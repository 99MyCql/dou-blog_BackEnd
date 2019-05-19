package com.dounine.myblog.controller;

import com.dounine.myblog.bean.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public void findById(@RequestParam int id) {
    }

    @RequestMapping(value = "/findByArticleId", method = RequestMethod.GET)
    public void findByArticleId(@RequestParam int articleId) {
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(@RequestBody User user) {
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void delete(@RequestParam int id) {
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody User user) {
    }
}
