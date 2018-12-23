package com.mmall.common;

/**
 * @Description： 常量类
 * @Author: jarry
 * @Date: 12/15/2018 16:27
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";
    public static final String EMALL = "email";
    public static final String USERNAME = "username";


    //    分组，但是用枚举又显得过于繁重，故采用内部接口类来进行分组
    public interface Role {
        int ROLE_CUSTOMER = 0;    //普通用户
        int ROLE_ADMIN = 1;   //管理员
    }
}
