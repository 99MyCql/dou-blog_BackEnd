package com.dounine.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/***
 * @project dou-blog
 * @class CommentServiceImplTest
 * @author douNine
 * @date 2020/6/28 8:49
 * @description CommentServiceImpl 类测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @Test
    public void countComments() {
    }

    @Test
    public void listExtendByPage() {
        List<Map<String, Object>> data = commentServiceImpl.listExtendByPage(1, 10);
        System.out.println(JSONObject.toJSONString(data));
    }

    @Test
    public void listExtendByArticleId() {
    }

    @Test
    public void listExtendByArticleId1() {
    }

    @Test
    public void listExtendByCommenterId() {
    }

    @Test
    public void findById() {
        assertEquals(null, commentServiceImpl.findById(0));
    }

    @Test
    public void insert() {
        int articleId = 26;
        int commenterId = 1;
        int oldComments = articleServiceImpl.findById(articleId).getComments();

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setCommenterId(commenterId);
        comment.setContent("junit test");
        commentServiceImpl.insert(comment);

        assertEquals(oldComments+1, articleServiceImpl.findById(articleId).getComments());
    }

    @Test
    public void delete() {
        // int articleId = 26;
        // int commenterId = 1740; // 不能使用第二次，删完就没了
        // int oldComments = articleServiceImpl.findById(articleId).getComments();
        //
        // commentServiceImpl.delete(commenterId);
        //
        // assertEquals(oldComments-1, articleServiceImpl.findById(articleId).getComments());
    }
}