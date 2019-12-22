package com.dounine.blog.dao;

import com.dounine.blog.bean.Article;

import java.util.List;

public interface ArticleDao {
    public List<Article> listAllArticles();

    public Article findById(int id);

    public Article findByArticleTitle(String ArticleTitle);

    public int insert(Article article);

    public int delete(int id);

    public int update(Article newArticle);
}
