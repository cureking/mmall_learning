package com.mmall.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.uitl.CookieUtil;
import com.mmall.uitl.JsonUtil;
import com.mmall.uitl.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description：
 * @Author: jarry
 * @Date: 1/19/2019 1:35
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    //进入Controller之前的拦截器
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        //一，请求中Controller中的方法名
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        //解析HandlerMethod，获取用户相关参数
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();  //getName()获取的是完全限定名

        //解析参数,具体的参数key以及value是什么，打印日志
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = request.getParameterMap();
        //利用迭代器，迭代出paramMap中的元素
        //Map.Entry是Map声明的一个内部接口，此接口为泛型，定义为Entry<K,V>。它表示Map中的一个实体（一个key-value对）。接口中有getKey(),getValue方法。
        //因为Map这个类没有继承Iterable接口所以不能直接通过map.iterator来遍历(list，
        // set就是实现了这个接口，所以可以直接这样遍历),所以就只能先转化为set类型，用entrySet()方法，其中set中的每一个元素值就是map中的一个键值对，也就是Map.Entry<K,V>了，然后就可以遍历了。
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()){
            //迭代器的元素从it.next()开始
            //entrySet()的返回值也是返回一个Set集合，此集合的类型为Map.Entry
            Map.Entry entry = (Map.Entry)it.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;

            //Map.Entry是Map声明的一个内部接口，此接口为泛型，定义为Entry<K,V>。它表示Map中的一个实体（一个key-value对）。接口中有getKey(),getValue方法。
            //request这个参数的map，里面的value返回的是一个String[]
            Object obj = entry.getValue();

            //为了代码的健壮性，先进行类型判断，再进一步操作
            if(obj instanceof String[]){
                String[] strs = (String[])obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }
        //补充：拦截器解决类似登录循环问题的解决方案二：拦截器内部逻辑跳过
        if(StringUtils.equals(className,"UserManageController") && StringUtils.equals(methodName,"login")){
            log.info("权限拦截器拦截到请求,className:{},methodName:{}",className,methodName);
            //如果是拦截到登录请求，不打印参数，因为参数里面有密码，全部会打印到日志中，防止日志泄露
            return true;
        }
        //日志打印
        log.info("权限拦截器拦截到请求,className:{},methodName:{},param:{}",className,methodName,requestParamBuffer.toString());

        //二，通过获取的用户相关参数（账号，密码），利用之前代码的权限模块逻辑，对用户进行登录状态，及用户权限的判断。
        User user = null;
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            user = JsonUtil.string2Obj(userJsonStr, User.class);
        }
        //user==null将用户未登录与用户信息无两个状态合并，后者判断用户权限
        if (user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            //返回false.即不会调用controller里的方法
            //为了解决拦截器返回类型（Boolean）与Controller返回类型（ServerResponse）不一致问题
            //（当然，究其本质，和拦截器返回类型也毫无关系。毕竟拦截器也只是根据返回的boolean类型，决定是否拦截。）
            //（但是，我们需要给前端一个有效且统一（与正确返回对比）的返回。这样才有利于人机交互）
            //解决方案：直接重写Response
            response.reset();   // 这里要添加reset，否则报异常 getWriter() has already been called for this response.
            response.setCharacterEncoding("UTF-8"); // 这里要设置编码，否则会乱码。（因为已经将response reset了）
            response.setContentType("application/json;charset=UTF-8");  // 这里要设置返回值的类型，因为全部是json接口。

            //获取重写对象
            PrintWriter out = response.getWriter();
            if (user == null){
                out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录")));
            }else {
                out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户无权限操作")));
            }

            //上传由于富文本的控件要求，要特殊处理返回值，这里面区分是否登录以及是否有权限
            if(user == null){
                if(StringUtils.equals(className,"ProductManageController") && StringUtils.equals(methodName,"richtextImgUpload")){
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success",false);
                    resultMap.put("msg","请登录管理员");
                    out.print(JsonUtil.obj2String(resultMap));
                }else{
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户未登录")));
                }
            }else{
                if(StringUtils.equals(className,"ProductManageController") && StringUtils.equals(methodName,"richtextImgUpload")){
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success",false);
                    resultMap.put("msg","无权限操作");
                    out.print(JsonUtil.obj2String(resultMap));
                }else{
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户无权限操作")));
                }
            }

            out.flush();    //数据清空传输
            out.close();    //关闭缓存区

            return false;
        }
        return true;
    }

    @Override
    //进入Controller之后的拦截器
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    //全部操作之后的拦截器
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterCompletion");
    }
}
