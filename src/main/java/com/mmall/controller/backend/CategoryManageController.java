package com.mmall.controller.backend;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.uitl.CookieUtil;
import com.mmall.uitl.JsonUtil;
import com.mmall.uitl.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：
 * @Author: jarry
 * @Date: 12/24/2018 14:19
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;


    @RequestMapping(value = "/add_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryName,
                                      //设置默认值 0 ，即默认未根节点
                                      @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
//        //校验一下，用户是否在登录状态
////        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (loginToken == null) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        //校验一下，用户是否为管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            //增加处理分类的逻辑
//            return iCategoryService.addCategory(categoryName, parentId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //上述代码全部通过拦截器验证用户登录状态已经管理员权限
        return iCategoryService.addCategory(categoryName, parentId);
    }

    @RequestMapping(value = "set_category_name.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
////        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (loginToken == null) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //更新categoryName
//            return iCategoryService.updateCategoryName(categoryId, categoryName);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //上述代码全部通过拦截器验证用户登录状态已经管理员权限
        return iCategoryService.updateCategoryName(categoryId, categoryName);
    }

    @RequestMapping(value = "get_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
////        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (loginToken == null) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //查询子节点的category信息，并且不递归，保持平级
//            return iCategoryService.getChildrenParallelCategory(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //上述代码全部通过拦截器验证用户登录状态已经管理员权限
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping(value = "get_deep_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest request,
                                                             @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
////        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (loginToken == null) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //查询当前节点的id和递归子节点的id
//            return iCategoryService.selectCategoryAndChildrenById(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //上述代码全部通过拦截器验证用户登录状态已经管理员权限
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}
