package com.dounine.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/***
 * @project dou-blog
 * @class UserServiceImplTest
 * @author douNine
 * @date 2020/6/27 21:33
 * @description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void countUsers() {
    }

    @Test
    public void listByPage() {
        List<User> userList = userServiceImpl.listByPage(1, 10);
        for (User user : userList) {
            logger.info(JSONObject.toJSONString(user));
        }
    }

    @Test
    public void findById() {
        User user = userServiceImpl.findById(1);
        logger.info(JSONObject.toJSONString(user));
        assertEquals("", user.getPassword());
    }

    @Test
    public void findByName() {
        User user = userServiceImpl.findByName("dounine");
        logger.info(JSONObject.toJSONString(user));
        assertEquals("", user.getPassword());
    }

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("dounine");
        user.setPassword("123456");
        assertEquals(0, userServiceImpl.insert(user));
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(1);
        user.setUsername("tom");
        assertEquals(0, userServiceImpl.update(user));
    }

    @Test
    public void checkUser() {
        assertEquals(0, userServiceImpl.checkUser("douni", "123"));
        assertEquals(-1, userServiceImpl.checkUser("dounine", "123"));
    }
}