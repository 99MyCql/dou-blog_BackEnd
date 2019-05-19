package com.dounine.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.myblog.bean.User;
import com.dounine.myblog.dao.UserDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public String findById(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size) {
        List<User> userList = userDao.listAllUsers();
        PageHelper.startPage(page, size);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return JSONObject.toJSONString(pageInfo);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public String findById(@RequestParam int id) {
        User user = userDao.findById(id);
        return JSONObject.toJSONString(user);
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    public String findByName(@RequestParam String name) {
        User user = userDao.findByName(name);
        return JSONObject.toJSONString(user);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JSONObject add(@RequestBody User user) {
        JSONObject retMsg = new JSONObject();
        if (userDao.insert(user) > 0) {
            retMsg.put("code", 1);
            retMsg.put("msg", "success");
            return retMsg;
        }
        else {
            retMsg.put("code", 0);
            retMsg.put("msg", "insert error");
            return retMsg;
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject delete(@RequestParam int id) {
        JSONObject retMsg = new JSONObject();
        if (userDao.delete(id) > 0) {
            retMsg.put("code", 1);
            retMsg.put("msg", "success");
            return retMsg;
        }
        else {
            retMsg.put("code", 0);
            retMsg.put("msg", "delete error");
            return retMsg;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject update(@RequestBody User user) {
        JSONObject retMsg = new JSONObject();
        if (userDao.update(user) > 0) {
            retMsg.put("code", 1);
            retMsg.put("msg", "success");
            return retMsg;
        }
        else {
            retMsg.put("code", 0);
            retMsg.put("msg", "update error");
            return retMsg;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody User user) {
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody User user) {
    }
}
