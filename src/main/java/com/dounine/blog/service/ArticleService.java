package com.dounine.blog.service;

import com.dounine.blog.bean.Article;

import java.util.List;

public interface ArticleService {

    public int countArticles();

    public List<Article> listAllArticles(int page, int size);

    public Article findById(int id);

    public Article findByArticleTitle(String ArticleTitle);

    public int insert(Article article);

    public int delete(int id);

    public int update(Article newArticle);
}
