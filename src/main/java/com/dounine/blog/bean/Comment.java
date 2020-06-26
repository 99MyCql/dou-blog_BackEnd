package com.dounine.blog.bean;

public class Comment {

    private int id;

    private int articleId;      // 被评论的文章ID

    private int commenterId;    // 评论者ID

    private String content;     // 评论内容

    private int likes=0;        // 点赞数

    private String publishDate; // 发布日期

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setCommenterId(int commenterId) {
        this.commenterId = commenterId;
    }

    public int getCommenterId() {
        return commenterId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
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
}
