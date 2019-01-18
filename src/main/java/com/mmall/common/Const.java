package com.mmall.common;

import com.google.common.collect.Sets;

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


    //TokenCache的配置
    //token 作为前缀的常量，由于与TokenCache太紧密，所以放在这里
    public static final String TOKEN_PREFIX = "token_";
    //self  有效期限
    public static final int TOKEN_DURATION = 60 * 60 * 12;


    //    分组，但是用枚举又显得过于繁重，故采用内部接口类来进行分组
    public interface Role {
        int ROLE_CUSTOMER = 0;    //普通用户
        int ROLE_ADMIN = 1;   //管理员
    }

    //新增Redis的有效期，用于单点登陆的登录有效期
    public interface RedisCacheExtime {
        int REDIS_SESSION_EXTIME = 30 * 60; //30min
    }

    public interface ProductListOrderBy {
        //Set的Contain时间复杂度是O(1),List的Contain时间复杂度是O(n)。
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    public interface Cart {
        int CHECKED = 1;    //即购物车中选中状态
        int UNCHECKED = 0;  //购物车中未选中装填

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public enum ProductStatusEnum {
        ON_SALE(1, "在线");

        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
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

    public enum OrderStatusEnum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已支付"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum PayPlatformEnum {
        ALIPAY(1, "支付宝");

        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum PaymentTypeEnum {
        ONLINE_PAY(1, "在线支付");

        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }


        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }

    }

}
