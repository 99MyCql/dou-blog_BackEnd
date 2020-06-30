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
        return jdbcTemplate.queryForObject("select count(*) from \"COMMENT\"", Integer.class);
    }

    public List<Map<String,Object>> listExtendByPage(int page, int size) {
        String sql = "select c.id, u.username, a.title, c.content, c.likes, c.publishDate " +
                "from \"COMMENT\" c, \"USER\" u, article a " +
                "where c.commenterId=u.id and c.articleId=a.id and rownum>? and rownum<=?";
        return jdbcTemplate.queryForList(sql, (page-1)*size, page*size);
    }

    public List<Map<String,Object>> listExtendByArticleId(int articleId, int page, int size) {
        String sql = "select c.id, u.username, a.title, c.content, c.likes, c.publishDate " +
                "from \"COMMENT\" c, \"USER\" u, article a " +
                "where c.commenterId=u.id and c.articleId=a.id and c.articleId=? and rownum>? and rownum<=?";
        return jdbcTemplate.queryForList(sql, articleId, (page-1)*size, page*size);
    }

    public List<Map<String,Object>> listExtendByArticleId(int articleId) {
        String sql = "select c.id, u.username, a.title, c.content, c.likes, c.publishDate " +
                "from \"COMMENT\" c, \"USER\" u, article a " +
                "where c.commenterId=u.id and c.articleId=a.id and c.articleId=?";
        return jdbcTemplate.queryForList(sql, articleId);
    }

    public List<Map<String,Object>> listExtendByCommenterId(int commenterId) {
        String sql = "select c.id, u.username, a.title, c.content, c.likes, c.publishDate " +
                "from \"COMMENT\" c, \"USER\" u, article a " +
                "where c.commenterId=u.id and c.articleId=a.id and c.commenterId=?";
        return jdbcTemplate.queryForList(sql, commenterId);
    }

    public Comment findById(int id) {
        try {
            return (Comment) jdbcTemplate.queryForObject("select * from \"COMMENT\" where id = ?",
                    new BeanPropertyRowMapper(Comment.class), id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insert(Comment comment) {
        String sql = "insert into \"COMMENT\"(articleId, commenterId, content, likes, publishDate) " +
                "values (?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql,
                    comment.getArticleId(), comment.getCommenterId(), comment.getContent(),
                    comment.getLikes(), comment.getPublishDate());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(int id) {
        return jdbcTemplate.update("delete from \"COMMENT\" where id = ?", id);
    }

    public int update(Comment comment) {
        String sql = "update \"COMMENT\" set articleId=?, commenterId=?, content=?, likes=?, publishDate=? " +
                "where id = ?";
        try {
            return jdbcTemplate.update(sql,
                    comment.getArticleId(), comment.getCommenterId(), comment.getContent(),
                    comment.getLikes(), comment.getPublishDate(),
                    comment.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
