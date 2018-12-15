package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //custom
    int checkUsername(String username);

    //多个输入参数时，需要采用Param注解。在对应的xml文件中，参数名取得是Param注解中的名字
    User selectLogin(@Param("username") String username,@Param("password") String password);
}