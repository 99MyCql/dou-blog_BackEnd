package com.dounine.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.Article;
import com.dounine.blog.bean.Comment;
import com.dounine.blog.bean.User;
import com.dounine.blog.dao.ArticleDao;
import com.dounine.blog.dao.CommentDao;
import com.dounine.blog.dao.UserDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentDao commentDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ArticleDao articleDao;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public String listAll(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size) {
        List<Comment> commentList = commentDao.listAllComments();
        for (int i = 0; i < commentList.size(); i++) {
            User user = userDao.findById(commentList.get(i).getCommenterId());
            commentList.get(i).setCommenterName(user.getName());
            Article article = articleDao.findById(commentList.get(i).getArticleId());
            commentList.get(i).setArticleTitle(article.getArticleTitle());
        }
        PageHelper.startPage(page, size);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        return JSONObject.toJSONString(pageInfo);
    }

    @RequestMapping(value = "/listByArticleId", method = RequestMethod.GET)
    public JSONObject listByArticleId(@RequestParam int articleId) {
        List<Comment> commentList = commentDao.listByArticleId(articleId);
        JSONObject retMsg = new JSONObject();
        if (commentList == null) {
            retMsg.put("code", 0);
            retMsg.put("msg", "no comment");
        }
        else {
            for (int i = 0; i < commentList.size(); i++) {
                User user = userDao.findById(commentList.get(i).getCommenterId());
                commentList.get(i).setCommenterName(user.getName());
            }
            retMsg.put("code", 1);
            retMsg.put("msg", "successful");
            retMsg.put("data", JSONObject.toJSONString(commentList));
        }
        return retMsg;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public JSONObject findById(@RequestParam int id) {
        Comment comment = commentDao.findById(id);
        JSONObject retMsg = new JSONObject();
        if (comment == null) {
            retMsg.put("code", 0);
            retMsg.put("msg", "find this comment fail");
        }
        else {
            retMsg.put("code", 1);
            retMsg.put("msg", "find this comment successfully");
            retMsg.put("data", JSONObject.toJSONString(comment));
        }
        return retMsg;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JSONObject insert(@RequestBody Comment comment) {
        Article article = articleDao.findById(comment.getArticleId());
        article.setComments(article.getComments()+1);   // 文章评论数加一
        articleDao.update(article);
        JSONObject retMsg = new JSONObject();
        comment.setCommentDate(dateFormat.format(new Date()));
        if (commentDao.insert(comment) > 0) {
            retMsg.put("code", 1);
            retMsg.put("msg", "insert success");
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
        Comment comment = commentDao.findById(id);
        Article article = articleDao.findById(comment.getArticleId());
        article.setComments(article.getComments()-1);   // 文章评论数减一
        articleDao.update(article);
        JSONObject retMsg = new JSONObject();
        if (commentDao.delete(id) > 0) {
            retMsg.put("code", 1);
            retMsg.put("msg", "delete success");
            return retMsg;
        }
        else {
            retMsg.put("code", 0);
            retMsg.put("msg", "delete error");
            return retMsg;
        }
    }

//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public JSONObject update(@RequestBody Comment comment) {
//        JSONObject retMsg = new JSONObject();
//        if (commentDao.update(comment) > 0) {
//            retMsg.put("code", 1);
//            retMsg.put("msg", "success");
//            return retMsg;
//        }
//        else {
//            retMsg.put("code", 0);
//            retMsg.put("msg", "update error");
//            return retMsg;
//        }
//    }
}
