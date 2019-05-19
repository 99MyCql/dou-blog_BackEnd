package com.dounine.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.myblog.bean.Article;
import com.dounine.myblog.dao.ArticleDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleDao articleDao;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public String findById(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size) {
        List<Article> articleList = articleDao.listAllArticles();
        PageHelper.startPage(page, size);
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        return JSONObject.toJSONString(pageInfo);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public String findById(@RequestParam int id) {
        Article article = articleDao.findById(id);
        return JSONObject.toJSONString(article);
    }

    @RequestMapping(value = "/findByArticleId", method = RequestMethod.GET)
    public String findByArticleId(@RequestParam int articleId) {
        Article article = articleDao.findByArticleId(articleId);
        return JSONObject.toJSONString(article);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JSONObject add(@RequestBody Article article) {
        JSONObject retMsg = new JSONObject();
        if (articleDao.insert(article) > 0) {
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
        if (articleDao.delete(id) > 0) {
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
    public JSONObject update(@RequestBody Article article) {
        JSONObject retMsg = new JSONObject();
        if (articleDao.update(article) > 0) {
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
