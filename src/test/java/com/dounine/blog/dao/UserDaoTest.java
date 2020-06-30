package com.dounine.blog.dao;

import com.dounine.blog.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/***
 * @project dou-blog
 * @class UserDaoTest
 * @author douNine
 * @date 2020/6/27 12:20
 * @description UserDao 类测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Test
    public void countUsers() {
        log.info(String.valueOf(userDao.countUsers()));
    }

    @Test
    public void listByPage() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByUsername() {
    }

    @Test(expected = Exception.class)
    public void insert() {
        User user = new User();
        user.setUsername("dounine");
        user.setPassword("123456");
        assertEquals(0, userDao.insert(user));
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }
}