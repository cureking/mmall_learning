package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jarry
 * @Date: 12/15/2018 14:33
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

//    通过Autowired注解，将UserMapper注入进来
//    @Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 通过 @Autowired的使用来消除 set ，get方法。
    @Autowired
    private UserMapper userMapper;


    @Override
    public ServerResponse<User> login(String username, String password) {

        int resultCount=userMapper.checkUsername(username);
        if (resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //todo 密码登录MD5

        User user =userMapper.selectLogin(username,password);
        if (username==null){
            //由于之前已经检测过用户不存在了，所以此处的不存在一定表示指定用户名和指定密码没有同时成立（AND)，而用户名已经确定存在，那一定是密码错误了。
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }
}

