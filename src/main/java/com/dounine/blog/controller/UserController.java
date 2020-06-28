package com.dounine.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.User;
import com.dounine.blog.service.UserService;
import com.dounine.blog.util.RetMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public JSONObject count() {
        int count = userService.countUsers();

        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "get the count of users successfully",
                JSONObject.toJSONString(count));
    }

    @RequestMapping(value = "/listByPage", method = RequestMethod.GET)
    public JSONObject listByPage(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        if (page <= 0)
            return RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "page is error");
        List<User> userList = userService.listByPage(page, size);   // 分页查询用户

        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "list users successfully",
                JSONObject.toJSONString(userList));
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public JSONObject getInfo(HttpServletRequest request) {
        // 从 session 中获取当前用户的 ID
        HttpSession session = request.getSession();
        int userId = (int)session.getAttribute("userId");

        return RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE,
                "get info successfully",
                JSONObject.toJSONString(userService.findById(userId)));
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public JSONObject findById(@RequestParam int id) {
        JSONObject retMsg; // 返回 json

        User user = userService.findById(id); // 根据 id 查询 user

        // 如果该 user 不存在
        if (user == null)
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this user");
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE,
                    "find this user successfully", JSONObject.toJSONString(user));
        return retMsg;
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    public JSONObject findByName(@RequestParam String name) {
        JSONObject retMsg; // 返回 json

        User user = userService.findByName(name); // 根据 name 查询 user

        // 如果该 user 不存在
        if (user == null)
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this user");
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE,
                    "find this comment successfully", JSONObject.toJSONString(user));
        return retMsg;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject delete(@RequestParam int id) {
        JSONObject retMsg; // 返回信息

        // 删除成功
        if (userService.delete(id))
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "delete this user successfully");
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "delete this user error");
        return retMsg;
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public JSONObject updateInfo(@RequestBody User user, HttpServletRequest request) {
        JSONObject retMsg = null;

        // 检测 user ID 是否有问题，只允许是当前用户 ID
        HttpSession session = request.getSession();
        if (user.getId() != (int)session.getAttribute("userId"))
            return RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "the user ID is incorrect");

        switch (userService.update(user)) {
            case 0:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "this username is exist");
                break;
            case 1:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "update successfully");
                break;
            case -1:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "update error");
                break;
        }

        return retMsg;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JSONObject login(@RequestParam String name, @RequestParam String password,
                            HttpServletRequest request) {
        /* TODO: 邮箱验证 */
        JSONObject retMsg = null; // 返回信息

        switch (userService.checkUser(name, password)) {
            // 用户不存在
            case 0:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this user");
                break;
            // 正确
            case 1:
                User user = userService.findByName(name);
                HttpSession session = request.getSession(); // 获取session
                session.setAttribute("username", name);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole());
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "login successfully");
                break;
            // 密码错误
            case -1:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "invalid password");
                break;
        }

        return retMsg;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JSONObject logout(HttpServletRequest request) {
        HttpSession session = request.getSession(); // 获取session
        session.removeAttribute("username");    // 移除 session 中的 username
        session.removeAttribute("userId");      // 移除 userId
        session.removeAttribute("userRole");    // 移除 userRole
        return RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "logout successfully");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody User user) {
        JSONObject retMsg = null; // 返回信息

        switch (userService.insert(user)) {
            case 0:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "this username is exist");
                break;
            case 1:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "register successfully");
                break;
            case -1:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "register error");
                break;
        }

        return retMsg;
    }
}
