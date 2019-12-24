package com.dounine.blog.service;

import com.dounine.blog.bean.Comment;

import java.util.List;

public interface CommentService {

    public int countComments();

    public List<Comment> listAllComments(int page, int size);

    public List<Comment> listByArticleId(int articleId, int page, int size);

    public List<Comment> listByArticleId(int articleId);

    public List<Comment> listByCommenterId(int commenterId);

    public Comment findById(int id);

    public int insert(Comment comment);

    public int delete(int id);

    public int update(Comment newComment);
}
