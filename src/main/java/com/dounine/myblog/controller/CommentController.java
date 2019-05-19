package com.dounine.myblog.controller;

import com.dounine.myblog.bean.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public void findById(@RequestParam int id) {

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
