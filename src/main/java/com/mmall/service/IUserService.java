package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * @Author: jarry
 * @Date: 12/15/2018 14:28
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);
}
