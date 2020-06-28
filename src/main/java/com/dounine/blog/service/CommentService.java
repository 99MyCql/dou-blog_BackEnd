package com.dounine.blog.service;

import com.dounine.blog.bean.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    public int countComments();

    /**
     * Comment 表、 User 表、Article 表做连接后，返回分页查询的数据。包括：
     * Comment.id, User.username, Article.title, Comment.content, Comment.likes, Comment.publishDate
     * @param page
     * @param size
     * @return 键值对序列
     */
    public List<Map<String,Object>> listExtendByPage(int page, int size);

    public List<Map<String,Object>> listExtendByArticleId(int articleId, int page, int size);

    public List<Map<String,Object>> listExtendByArticleId(int articleId);

    public List<Map<String,Object>> listExtendByCommenterId(int commenterId);

    public Comment findById(int id);

    /**
     * 先设置发布时间，再添加；添加成功后，将对应文章的评论数加一。
     * @param comment
     * @return true and false
     */
    public boolean insert(Comment comment);

    /**
     * 删除成功后，将对应文章的评论数减一。
     * @param id
     * @return true and false
     */
    public boolean delete(int id);
}
