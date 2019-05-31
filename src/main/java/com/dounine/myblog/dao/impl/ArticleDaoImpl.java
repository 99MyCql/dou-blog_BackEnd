package com.dounine.myblog.dao.impl;

import com.dounine.myblog.bean.Article;
import com.dounine.myblog.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDaoImpl implements ArticleDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Article> listAllArticles() {
        return jdbcTemplate.query("select * from article", new BeanPropertyRowMapper(Article.class));
    }

    @Override
    public Article findById(int id) {
        String sql = "select * from article where id = ?";
        try {
            return (Article) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Article.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Article findByArticleTitle(String articleTitle) {
        String sql = "select * from article where articleTitle = ?";
        try {
            return (Article) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(Article.class), articleTitle);
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int insert(Article article) {
        String sql = "insert into article(authorId," +
                "articleTitle, articleContent, articleTags," +
                "articleCategories,publishDate, updateDate, " +
                "articleTabloid, likes, readers) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                article.getAuthorId(),
                article.getArticleTitle(),article.getArticleContent(), article.getArticleTags(),
                article.getArticleCategories(),article.getPublishDate(), article.getUpdateDate(),
                article.getArticleTabloid(), article.getLikes(), article.getReaders());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from article where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int update(Article newArticle) {
        String sql = "update article set authorId = ?, " +
                "articleTitle = ?, articleContent = ?, articleTags = ?, " +
                "articleCategories = ?, publishDate = ?, updateDate = ?, " +
                "articleTabloid = ?, likes = ?, readers = ? " +
                "where id = ?";
        return jdbcTemplate.update(sql,
                newArticle.getAuthorId(), newArticle.getArticleTitle(),
                newArticle.getArticleContent(), newArticle.getArticleTags(), newArticle.getArticleCategories(),
                newArticle.getPublishDate(), newArticle.getUpdateDate(), newArticle.getArticleTabloid(),
                newArticle.getLikes(), newArticle.getReaders(), newArticle.getId());
    }
}
