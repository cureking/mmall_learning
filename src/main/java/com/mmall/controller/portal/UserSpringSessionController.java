package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.uitl.CookieUtil;
import com.mmall.uitl.JsonUtil;
import com.mmall.uitl.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * create by jarry
 */

//Spring的Controller注解，声明是Controller，控制器
@Controller
//RequestMapping注解，将其下（class UserController）的请求全部统一到/user/这一命名空间中。
@RequestMapping("/user/springsession/")
public class UserSpringSessionController {

    @Autowired
    private IUserService iUserService;


    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    //声明该方法通过login.do访问(进行了类似原DD中servlet的处理），访问方法设置为POST
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    //返回时，自动通过SpringMVC下的MappingJacksonHttpMessageConverter(springMVC的dispatcher-servlet.xml中有相关配置）,将返回结果自动序列化为JSON
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {

        //SpringMVC全局异常的测试
        int i = 0;
        int j = 100/i;  //运行时异常

        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
            //不再保存在session中，而是保存在Redis构建的session服务器中
//            CookieUtil.writeLoginToken(httpServletResponse, session.getId());
//            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 登出功能
     *
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        CookieUtil.delLoginToken(request, response);
//        RedisShardedPoolUtil.del(loginToken);
        return ServerResponse.createBySuccess();
    }

    /**
     * 获取用户信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

//        String loginToken = CookieUtil.readLoginToken(request);
//        if (loginToken == null) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);

        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

}
