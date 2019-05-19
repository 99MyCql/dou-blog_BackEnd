package com.dounine.myblog.dao.impl;

import com.dounine.myblog.bean.Article;
import com.dounine.myblog.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDaoImpl implements ArticleDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

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
        return jdbcTemplate.queryForObject(sql, Article.class, id);
    }

    @Override
    public Article findByArticleId(int ArticleId) {
        String sql = "select * from article where articleId = ?";
        return jdbcTemplate.queryForObject(sql, Article.class, ArticleId);
    }

    @Override
    public int insert(Article article) {
        String sql = "insert into article(authorId, articleId, " +
                "articleTitle, articleContent, articleTags, articleCategories, " +
                "publishDate, updateDate, articleTabloid, likes, reads) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                article.getAuthorId(), article.getArticleId(), article.getArticleTitle(),
                article.getArticleContent(), article.getArticleTags(), article.getArticleCategories(),
                article.getPublishDate(), article.getUpdateDate(), article.getArticleTabloid(),
                article.getLikes(), article.getReads());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from article where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int update(Article newArticle) {
        String sql = "update article set (authorId, articleId, " +
                "articleTitle, articleContent, articleTags, articleCategories, " +
                "publishDate, updateDate, articleTabloid, likes, reads) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) where id = ?";
        return jdbcTemplate.update(sql,
                newArticle.getAuthorId(), newArticle.getArticleId(), newArticle.getArticleTitle(),
                newArticle.getArticleContent(), newArticle.getArticleTags(), newArticle.getArticleCategories(),
                newArticle.getPublishDate(), newArticle.getUpdateDate(), newArticle.getArticleTabloid(),
                newArticle.getLikes(), newArticle.getReads(), newArticle.getId());
    }
}
