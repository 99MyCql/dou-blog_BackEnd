package com.dounine.myblog.dao;

import com.dounine.myblog.bean.Article;

import java.util.List;

public interface ArticleDao {
    public List<Article> listAllArticles();

    public Article findById(int id);

    public Article findByArticleId(int ArticleId);

    public int insert(Article article);

    public int delete(int id);

    public int update(Article newArticle);
}
