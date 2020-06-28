package com.dounine.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.Article;
import com.dounine.blog.service.ArticleService;
import com.dounine.blog.util.RetMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public JSONObject count() {
        int count = articleService.countArticles();

        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "get the count of articles successfully",
                JSONObject.toJSONString(count));
    }

    @RequestMapping(value = "/listByPage", method = RequestMethod.GET)
    public JSONObject listByPage(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        // 根据 page, size 查询所有文章
        List<Article> articleList = articleService.listByPage(page, size);

        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "list articles successfully",
                JSONObject.toJSONString(articleList));
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public JSONObject findById(@RequestParam int id) {
        JSONObject retMsg; // 返回信息

        Article article = articleService.findById(id); // 根据 id 查询文章

        if (article == null)
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this article");
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE,
                    "find this article successfully", JSONObject.toJSONString(article));
        return retMsg;
    }

    @RequestMapping(value = "/findByTitle", method = RequestMethod.GET)
    public JSONObject findByTitle(@RequestParam String articleTitle, HttpServletRequest request) {
        JSONObject retMsg; // 返回信息
        Article article = null;
        HttpSession session = request.getSession();

        // 如果查看人是管理员，则不增加文章阅读量
        if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin"))
            article = articleService.findByTitle(articleTitle, false);
        else
            article = articleService.findByTitle(articleTitle, true);

        // 如果没有这篇文章
        if (article == null)
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "no this article");
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE,
                    "find this article successfully", JSONObject.toJSONString(article));

        return retMsg;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public JSONObject submit(@RequestBody Article article) {
        return RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "this api is abandoned");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject delete(@RequestParam int id) {
        JSONObject retMsg; // 返回信息

        // 删除成功
        if (articleService.delete(id))
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "delete this article successfully");
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "delete this article error");

        return retMsg;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JSONObject insert(@RequestBody Article article) {
        JSONObject retMsg = null;

        switch (articleService.insert(article)) {
            case 0:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "this article title is exist");
                break;
            case 1:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "insert successfully");
                break;
            case -1:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "insert error");
                break;
        }

        return retMsg;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject update(@RequestBody Article article) {
        JSONObject retMsg = null;

        switch (articleService.update(article)) {
            case 0:
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "this article title is exist");
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
}
