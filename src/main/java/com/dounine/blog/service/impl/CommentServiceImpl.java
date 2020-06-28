package com.dounine.blog.service.impl;

import com.dounine.blog.bean.Article;
import com.dounine.blog.bean.Comment;
import com.dounine.blog.dao.ArticleDao;
import com.dounine.blog.dao.CommentDao;
import com.dounine.blog.service.ArticleService;
import com.dounine.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    public int countComments() {
        return commentDao.countComments();
    }

    @Override
    public List<Map<String,Object>> listExtendByPage(int page, int size) {
        return commentDao.listExtendByPage(page, size);
    }

    @Override
    public List<Map<String,Object>> listExtendByArticleId(int articleId) {
        return commentDao.listExtendByArticleId(articleId);
    }

    @Override
    public List<Map<String,Object>> listExtendByArticleId(int articleId, int page, int size) {
        return commentDao.listExtendByArticleId(articleId, page, size);
    }

    @Override
    public List<Map<String,Object>> listExtendByCommenterId(int commenterId) {
        return commentDao.listExtendByCommenterId(commenterId);
    }

    @Override
    public Comment findById(int id) {
        return commentDao.findById(id);
    }

    @Override
    public boolean insert(Comment comment) {
        comment.setPublishDate(dateFormat.format(new Date()));  // 设置发布时间
        if (commentDao.insert(comment) > 0) {
            Article article = articleDao.findById(comment.getArticleId());  // 找到该评论对应的文章
            article.setComments(article.getComments()+1);                   // 文章评论数加一
            // 更新文章
            if (articleDao.update(article) > 0)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    @Override
    public boolean delete(int id) {
        Comment comment = commentDao.findById(id);
        if (comment != null && commentDao.delete(id) > 0) {
            Article article = articleDao.findById(comment.getArticleId());  // 找到该评论对应的文章
            article.setComments(article.getComments()-1);                   // 文章评论数加一
            // 更新文章
            if (articleDao.update(article) > 0)
                return true;
            else
                return false;
        }
        else
            return false;
    }
}
