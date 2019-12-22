package com.dounine.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.Article;
import com.dounine.blog.bean.Comment;
import com.dounine.blog.service.ArticleService;
import com.dounine.blog.service.CommentService;
import com.dounine.blog.util.RetMsgHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格式


    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public JSONObject listAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        List<Article> articleList = articleService.listAllArticles(page, size); // 根据 page, size 查询所有文章

        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "list articles successfully",
                JSONObject.toJSONString(articleList));
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public JSONObject findById(@RequestParam int id) {

        JSONObject retMsg; // 返回信息

        Article article = articleService.findById(id); // 根据 id 查询文章

        if (article == null) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this article");
        }
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "find this article successfully", JSONObject.toJSONString(article));
        }
        return retMsg;
    }

    @RequestMapping(value = "/findByArticleTitle", method = RequestMethod.GET)
    public JSONObject findByArticleTitle(@RequestParam String articleTitle, HttpServletRequest request) {

        JSONObject retMsg; // 返回信息

        Article article = articleService.findByArticleTitle(articleTitle); // 根据文章标题查询文章
        // 如果没有这篇文章
        if (article == null) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this article");
        }
        else {
            HttpSession session = request.getSession();
            String userRole = (String)session.getAttribute("userRole"); // 从 session 中获取访问用户的身份

            // 如果不是管理员，文章阅读量加一
            if (!userRole.equals("admin")) {
                article.setReaders(article.getReaders()+1);
                articleService.update(article);
            }
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "find this article successfully", JSONObject.toJSONString(article));
        }
        return retMsg;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public JSONObject submit(@RequestBody Article article) {

        JSONObject retMsg; // 返回信息

        Article oldArticle = articleService.findByArticleTitle(article.getArticleTitle()); // 根据文章标题查询文章
        // 如果文章不存在，则为发布文章操作
        if (oldArticle == null) {
            article.setPublishDate(dateFormat.format(new Date())); // 设置文章发布时间。new Date()为获取当前系统时间
            // 插入成功
            if (articleService.insert(article) > 0) {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "insert this article successfully");
            }
            // 插入失败
            else {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "insert this article error");
            }
        }
        // 如果文章存在，则为更新文章操作
        else {
            article.setUpdateDate(dateFormat.format(new Date()));   // 设置文章更新时间
            article.setId(oldArticle.getId());                      // 设置文章 id 为已存在文章 id
            // 更新成功
            if (articleService.update(article) > 0) {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "update this article successfully");
            }
            // 更新失败
            else {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "update this article error");
            }
        }
        return retMsg;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject delete(@RequestParam int id) {

        JSONObject retMsg; // 返回信息

        // 删除文章，需要删除该文章的评论
        List<Comment> commentList = commentService.listByArticleId(id); // 根据 articleId 查询所有评论
        for (Comment comment : commentList) {
            commentService.delete(comment.getId());
        }

        // 删除成功
        if (articleService.delete(id) > 0) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "delete this article successfully");
        }
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "delete this article error");
        }
        return retMsg;
    }
}
