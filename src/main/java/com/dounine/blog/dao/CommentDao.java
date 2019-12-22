package com.dounine.blog.dao;

import com.dounine.blog.bean.Comment;

import java.util.List;

public interface CommentDao {
    public List<Comment> listAllComments();

    public List<Comment> listByArticleId(int articleId);

    public List<Comment> listByCommenterId(int commenterId);

    public Comment findById(int id);

    public int insert(Comment comment);

    public int delete(int id);

    public int update(Comment newComment);
}
