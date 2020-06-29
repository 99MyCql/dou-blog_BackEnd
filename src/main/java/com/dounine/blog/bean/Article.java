package com.dounine.blog.bean;

public class Article {

    private int id;                 // 主键

    private int authorId;           // 作者ID

    private String title;           // 文章标题

    private String summary; // 文章摘要

    private String content;         // 文章内容

    private String categories;      // 文章分类

    private String tags;            // 文章标签

    private int readings;           // 文章阅读数

    private int comments;           // 文章评论数

    private int likes;              // 文章喜欢人数

    private String publishDate;     // 发布时间

    private String updateDate;      // 最后一次更新时间

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategories() {
        return categories;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }

    public void setReadings(int readings) {
        this.readings = readings;
    }

    public int getReadings() {
        return readings;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getComments() {
        return comments;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
