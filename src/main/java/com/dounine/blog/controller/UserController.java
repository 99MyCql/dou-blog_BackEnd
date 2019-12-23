package com.dounine.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.Comment;
import com.dounine.blog.bean.User;
import com.dounine.blog.service.CommentService;
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
    UserService userService;

    @Autowired
    CommentService commentService;


    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public JSONObject listAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        List<User> userList = userService.listAllUsers(page, size); // 根据 page, size 查询所有用户

        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "list users successfully",
                JSONObject.toJSONString(userList));
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public JSONObject findById(@RequestParam int id) {

        JSONObject retMsg; // 返回信息

        User user = userService.findById(id); // 根据 id 查询 user

        // 如果该 user 不存在
        if (user == null) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this user");
        }
        else {
            user.setPassword("");   // 防止信息暴露给前端，设置 password 为空
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "find this comment successfully", JSONObject.toJSONString(user));
        }
        return retMsg;
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    public JSONObject findByName(@RequestParam String name) {

        JSONObject retMsg; // 返回信息

        User user = userService.findByName(name); // 根据 name 查询 user

        // 如果该 user 不存在
        if (user == null) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this user");
        }
        else {
            user.setPassword("");   // 防止信息暴露给前端，设置 password 为空
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "find this comment successfully", JSONObject.toJSONString(user));
        }
        return retMsg;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject delete(@RequestParam int id) {

        JSONObject retMsg; // 返回信息

        // 删除用户，需要删除与该用户发表的所有评论
        List<Comment> commentList = commentService.listByCommenterId(id); // 查询该用户发表过的所有评论
        for (Comment comment : commentList) {
            commentService.delete(comment.getId());
        }

        // 删除成功
        if (userService.delete(id) > 0) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "delete this user successfully");
        }
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "delete this user error");
        }
        return retMsg;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject update(@RequestBody User user) {

        JSONObject retMsg; // 返回信息

        // 更新用户成功
        if (userService.update(user) > 0) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "update this user successfully");
        }
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "update this user error");
        }
        return retMsg;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JSONObject login(@RequestParam String name, @RequestParam String password, HttpServletRequest request) {

        JSONObject retMsg; // 返回信息

        User user = userService.findByName(name); // 根据用户名查找该用户
        // 如果存在该用户
        if (user != null) {
            // 密码正确
            if (user.getPassword().equals(password)) {
                HttpSession session = request.getSession();             // 获取 session
                session.setAttribute("userId", user.getId());       // 在 session 中保存用户 id
                session.setAttribute("username", name);             // 在 session 中保存用户姓名
                session.setAttribute("userRole", user.getRole());   // 在 session 中保存用户身份
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "login successfully");
            }
            // 密码错误
            else {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "invalid password");
            }
        }
        // 该用户不存在
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this user");
        }
        return retMsg;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JSONObject logout(@RequestParam String name, HttpServletRequest request) {

        JSONObject retMsg; // 返回信息

        HttpSession session = request.getSession(); // 获取session
        // 判断该用户名与 session 中的用户名是否相同
        if (((String)session.getAttribute("username")).equals(name)) {
            session.removeAttribute("username");    // 移除 session 中的 username
            session.removeAttribute("userId");      // 移除 userId
            session.removeAttribute("userRole");    // 移除 userRole
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "logout successfully");
        }
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "invalid username, logout fail");
        }
        return retMsg;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody User user) {

        JSONObject retMsg; // 返回信息

        // 如果用户名不存在
        if (userService.findByName(user.getName()) == null) {
            // 插入成功
            if (userService.insert(user) > 0) {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "login successfully");
            }
            // 插入失败
            else {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "register error");
            }
        }
        // 用户名已存在
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "this username is exist");
        }
        return retMsg;
    }
}
