package com.dounine.myblog.bean;

public class Article {

    private int id; // 主键

    private int authorId;    // 作者编号

    private String articleTitle;    // 文章标题

    private String publishDate;     // 发布时间

    private String updateDate;      // 最后一次更新时间

    private String articleContent;  // 文章内容

    private String articleTags;     // 文章标签

    private String articleCategories;   // 文章分类

    private String articleTabloid;      // 文章摘要

    private int likes;      // 文章喜欢人数

    private int readers;      // 文章阅读数

    private int comments;      // 文章评论数

    private long lastArticleId; // 上一篇文章id

    private long nextArticleId; // 下一篇文章id

    public Article() {}

    public Article(int authorId, String articleTitle, String publishDate,
                   String updateDate, String articleContent, String articleTags, String articleCategories,
                   String articleTabloid, long lastArticleId, long nextArticleId,int readers,int likes, int comments) {
        this.authorId = authorId;
        this.publishDate = publishDate;
        this.updateDate = updateDate;
        this.articleContent = articleContent;
        this.articleTags = articleTags;
        this.articleCategories = articleCategories;
        this.articleTabloid = articleTabloid;
        this.lastArticleId = lastArticleId;
        this.readers=readers;
        this.likes=likes;
        this.comments=comments;
        this.nextArticleId = nextArticleId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public void setArticleCategories(String articleCategories) {
        this.articleCategories = articleCategories;
    }

    public void setArticleTabloid(String articleTabloid) {
        this.articleTabloid = articleTabloid;
    }

    public void setArticleTags(String articleTags) {
        this.articleTags = articleTags;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setLastArticleId(long lastArticleId) {
        this.lastArticleId = lastArticleId;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setNextArticleId(long nextArticleId) {
        this.nextArticleId = nextArticleId;
    }

    public void setReaders(int readers) {
        this.readers = readers;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getId() {
        return id;
    }

    public int getLikes() {
        return likes;
    }

    public String getArticleCategories() {
        return articleCategories;
    }

    public int getReaders() {
        return readers;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public long getLastArticleId() {
        return lastArticleId;
    }

    public long getNextArticleId() {
        return nextArticleId;
    }

    public String getArticleTabloid() {
        return articleTabloid;
    }

    public String getArticleTags() {
        return articleTags;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public int getComments() {
        return comments;
    }
}
