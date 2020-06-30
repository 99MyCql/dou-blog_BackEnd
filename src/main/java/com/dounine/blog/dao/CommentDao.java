package com.dounine.blog.dao;

import com.dounine.blog.bean.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.rmi.server.ExportException;
import java.util.List;
import java.util.Map;

/***
 * @project dou-blog
 * @class CommentDao
 * @author douNine
 * @date 2020/6/27 13:27
 * @description CommentDao
 */
@Repository
public class CommentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int countComments() {
        return jdbcTemplate.queryForObject("select count(*) from comment", Integer.class);
    }

    public List<Map<String,Object>> listExtendByPage(int page, int size) {
        String sql = "select c.id, u.username, a.title, c.content, c.likes, c.publishDate " +
                "from comment c join user u join article a " +
                "on c.commenterId=u.id and c.articleId=a.id limit ?, ?";
        return jdbcTemplate.queryForList(sql, (page-1)*size, size);
    }

    public List<Map<String,Object>> listExtendByArticleId(int articleId, int page, int size) {
        String sql = "select c.id, u.username, a.title, c.content, c.likes, c.publishDate " +
                "from comment c join user u join article a " +
                "on c.commenterId=u.id and c.articleId=a.id " +
                "where c.articleId=? limit ?, ?";
        return jdbcTemplate.queryForList(sql, articleId, (page-1)*size, size);
    }

    public List<Map<String,Object>> listExtendByArticleId(int articleId) {
        String sql = "select c.id, u.username, a.title, c.content, c.likes, c.publishDate " +
                "from comment c join user u join article a " +
                "on c.commenterId=u.id and c.articleId=a.id " +
                "where c.articleId=?";
        return jdbcTemplate.queryForList(sql, articleId);
    }

    public List<Map<String,Object>> listExtendByCommenterId(int commenterId) {
        String sql = "select c.id, u.username, a.title, c.content, c.likes, c.publishDate " +
                "from comment c join user u join article a " +
                "on c.commenterId=u.id and c.articleId=a.id " +
                "where c.commenterId=?";
        return jdbcTemplate.queryForList(sql, commenterId);
    }

    public Comment findById(int id) {
        try {
            return (Comment) jdbcTemplate.queryForObject("select * from comment where id = ?",
                    new BeanPropertyRowMapper(Comment.class), id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insert(Comment comment) {
        String sql = "insert into comment(articleId, commenterId, content, likes, publishDate) " +
                "values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                comment.getArticleId(), comment.getCommenterId(), comment.getContent(),
                comment.getLikes(), comment.getPublishDate());
    }

    public int delete(int id) {
        return jdbcTemplate.update("delete from comment where id = ?", id);
    }

    public int update(Comment comment) {
        String sql = "update comment set articleId=?, commenterId=?, content=?, likes=?, publishDate=? " +
                "where id = ?";
        return jdbcTemplate.update(sql,
                comment.getArticleId(), comment.getCommenterId(), comment.getContent(),
                comment.getLikes(), comment.getPublishDate(),
                comment.getId());
    }
}
