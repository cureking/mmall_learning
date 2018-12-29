package com.mmall.common;

import com.google.common.collect.Sets;
import com.mmall.pojo.Product;
import com.mmall.uitl.PropertiesUtil;

import java.util.Set;

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

    public interface ProductListOrderBy{
        //Set的Contain时间复杂度是O(1),List的Contain时间复杂度是O(n)。
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{
        int CHECKED = 1;    //即购物车中选中状态
        int UNCHECKED = 0;  //购物车中未选中装填

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public enum ProductStatusEnum{
        ON_SALE(1,"在线");

        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }


}
