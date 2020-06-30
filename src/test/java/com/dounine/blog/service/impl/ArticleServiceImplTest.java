package com.dounine.blog.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/***
 * @project dou-blog
 * @class ArticleServiceImplTest
 * @author douNine
 * @date 2020/6/28 8:26
 * @description ArticleServiceImpl 类测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {

    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @Test
    public void countArticles() {
    }

    @Test
    public void listByPage() {
    }

    @Test
    public void findById() {
        assertEquals(null, articleServiceImpl.findById(0));
    }

    @Test
    public void findByTitle() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }
}