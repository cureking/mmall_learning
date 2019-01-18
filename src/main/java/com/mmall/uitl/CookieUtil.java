package com.mmall.uitl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description：
 * @Author: jarry
 * @Date: 1/17/2019 14:28
 */
@Slf4j
public class CookieUtil {

    //这里的配置，可以写入配置文件，进行读取，拒绝硬编码
    private final static String COOKIE_DOMAIN = "localhost";    //这里为了便于本地测试，采用了本地DOMAIN，线上需要改为jarry.top
    private final static String COOKIE_NAME = "jarry_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //X:domain=".happymmall.com"
    //a:A.happymmall.com            cookie:domain=A.happymmall.com;path="/"
    //b:B.happymmall.com            cookie:domain=B.happymmall.com;path="/"
    //c:A.happymmall.com/test/cc    cookie:domain=A.happymmall.com;path="/test/cc"
    //d:A.happymmall.com/test/dd    cookie:domain=A.happymmall.com;path="/test/dd"
    //e:A.happymmall.com/test       cookie:domain=A.happymmall.com;path="/test"
    //a,b,c,d,e域名均为二级域名，包含于X的一级域名。故可以访问在X中存储的Cookie.
    //c,d可以访问e的Cookie，也是同样的道理。
    //但是反过来，则不可以。类似于继承。子代可以获取父代（乃至父代的父代）的信息，但父代无法获取后代信息

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");     //表示在根目录
        ck.setHttpOnly(true);   //防止脚本攻击带来的信息泄露风险
        // 单位是秒。
        //如果是-1，表示永久。
        //如果MaxAag不进行设置，则cookie不会写入硬盘，而是写在内存，只会在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 30);
        log.info("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (org.apache.commons.lang.StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置成0，代表删除此cookie。
                    log.info("del cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
