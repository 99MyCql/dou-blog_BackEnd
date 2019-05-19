package com.dounine.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.myblog.bean.Comment;
import com.dounine.myblog.dao.CommentDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentDao commentDao;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public String findById(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size) {
        List<Comment> commentList = commentDao.listAllComments();
        PageHelper.startPage(page, size);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        return JSONObject.toJSONString(pageInfo);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public String findById(@RequestParam int id) {
        Comment comment = commentDao.findById(id);
        return JSONObject.toJSONString(comment);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JSONObject add(@RequestBody Comment comment) {
        JSONObject retMsg = new JSONObject();
        if (commentDao.insert(comment) > 0) {
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
        if (commentDao.delete(id) > 0) {
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
    public JSONObject update(@RequestBody Comment comment) {
        JSONObject retMsg = new JSONObject();
        if (commentDao.update(comment) > 0) {
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
}
