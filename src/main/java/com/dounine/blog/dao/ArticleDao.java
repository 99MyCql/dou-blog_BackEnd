package com.dounine.blog.dao;

import com.dounine.blog.bean.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @project dou-blog
 * @class ArticleDao
 * @author douNine
 * @date 2020/6/26 20:58
 * @description TODO
 */
@Repository
public class ArticleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int countArticles() {
        return jdbcTemplate.queryForObject("select count(*) from article", Integer.class);
    }

    public List<Article> listByPage(int page, int size) {
        return jdbcTemplate.query("select * from article where rownum>? and rownum<=?",
                new BeanPropertyRowMapper(Article.class), (page-1)*size, page*size);
    }

    public Article findById(int id) {
        try {
            return (Article) jdbcTemplate.queryForObject("select * from article where id = ?",
                    new BeanPropertyRowMapper(Article.class), id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Article findByTitle(String title) {
        try {
            // title 字段上建立了唯一索引
            return (Article) jdbcTemplate.queryForObject("select * from article where title = ?",
                    new BeanPropertyRowMapper(Article.class), title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insert(Article article) {
        String sql = "insert into article(authorId, title, summary, content, categories, tags, readings, " +
                "comments, likes, publishDate, updateDate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                article.getAuthorId(), article.getTitle(), article.getSummary(), article.getContent(),
                article.getCategories(), article.getTags(), article.getReadings(), article.getComments(),
                article.getLikes(), article.getPublishDate(), article.getUpdateDate());
    }

    public int delete(int id) {
        return jdbcTemplate.update("delete from article where id = ?", id);
    }

    public int update(Article article) {
        String sql = "update article set authorId=?, title=?, summary=?, content=?, categories=?, tags=?, " +
                "readings=?, comments=?, likes=?, publishDate=?, updateDate=? " +
                "where id = ?";
        return jdbcTemplate.update(sql,
                article.getAuthorId(), article.getTitle(), article.getSummary(), article.getContent(),
                article.getCategories(), article.getTags(), article.getReadings(), article.getComments(),
                article.getLikes(), article.getPublishDate(), article.getUpdateDate(),
                article.getId());
    }
}
