package com.dounine.blog.service.impl;

import com.dounine.blog.bean.Comment;
import com.dounine.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentServiceImpl implements CommentService {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public CommentServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> listAllComments(int page, int size) {
        return jdbcTemplate.query("select * from comment limit ?, ?", new BeanPropertyRowMapper(Comment.class), (page-1)*size, size);
    }

    @Override
    public List<Comment> listByArticleId(int articleId, int page, int size) {
        return jdbcTemplate.query("select * from comment where articleId = ? limit ?, ?", new BeanPropertyRowMapper(Comment.class), articleId, (page-1)*size, size);
    }

    @Override
    public List<Comment> listByArticleId(int articleId) {
        return jdbcTemplate.query("select * from comment where articleId = ?", new BeanPropertyRowMapper(Comment.class), articleId);
    }

    @Override
    public List<Comment> listByCommenterId(int commenterId) {
        return jdbcTemplate.query("select * from comment where commenterId = ?", new BeanPropertyRowMapper(Comment.class), commenterId);
    }

    @Override
    public Comment findById(int id) {
        String sql = "select * from comment where id = ?";
        try {
            return (Comment) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Comment.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int insert(Comment comment) {
        String sql = "insert into comment(parentId, articleId, commenterId, commentDate, commentContent, likes) " +
                "values(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                comment.getParentId(), comment.getArticleId(), comment.getCommenterId(),
                comment.getCommentDate(), comment.getCommentContent(), comment.getLikes());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from comment where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int update(Comment newComment) {
        String sql = "update comment set (parentId, articleId, commenterId, commentDate, commentContent, likes) " +
                "values(?, ?, ?, ?, ?, ?) where id = ?";
        return jdbcTemplate.update(sql,
                newComment.getParentId(), newComment.getArticleId(), newComment.getCommenterId(),
                newComment.getCommentDate(), newComment.getCommentContent(), newComment.getLikes(),
                newComment.getId());
    }
}
