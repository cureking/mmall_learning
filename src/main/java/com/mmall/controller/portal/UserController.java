package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * create by jarry
 */

//Spring的Controller注解，声明是Controller，控制器
@Controller
//RequestMapping注解，将其下（class UserController）的请求全部统一到/user/这一命名空间中。
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;


    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    //声明该方法通过login.do访问(进行了类似原DD中servlet的处理），访问方法设置为POST
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    //返回时，自动通过SpringMVC下的MappingJacksonHttpMessageConverter(springMVC的dispatcher-servlet.xml中有相关配置），将返回值自动序列化为json
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        //service --> mybatis --> dao
        ServerResponse<User> response = iUserService.login(username,password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
}
