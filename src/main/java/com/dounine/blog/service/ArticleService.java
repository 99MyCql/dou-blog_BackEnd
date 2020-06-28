package com.dounine.blog.service;

import com.dounine.blog.bean.Article;

import java.util.List;

public interface ArticleService {

    public int countArticles();

    public List<Article> listByPage(int page, int size);

    public Article findById(int id);

    /**
     * 通过文章标题查询文章，根据 addReadings 判定是否增加文章阅读量
     * @param title
     * @param addReadings
     * @return
     */
    public Article findByTitle(String title, boolean addReadings);

    /**
     * 添加文章
     * @param article
     * @return 0 表示文章标题已存在；1 表示添加成功；-1 表示添加失败。
     */
    public int insert(Article article);

    public boolean delete(int id);

    /**
     * 更新文章
     * @param article
     * @return 0 表示文章标题已存在；1 表示更新成功；-1 表示更新失败。
     */
    public int update(Article article);
}
