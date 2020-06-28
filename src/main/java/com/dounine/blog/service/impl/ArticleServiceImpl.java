package com.dounine.blog.service.impl;

import com.dounine.blog.bean.Article;
import com.dounine.blog.dao.ArticleDao;
import com.dounine.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格式

    @Autowired
    public int countArticles() {
        return articleDao.countArticles();
    }

    @Override
    public List<Article> listByPage(int page, int size) {
        return articleDao.listByPage(page, size);
    }

    @Override
    public Article findById(int id) {
        return articleDao.findById(id);
    }

    @Override
    public Article findByTitle(String title, boolean addReadings) {
        /* TODO: 阅读量增加更加细致。 */
        Article article = articleDao.findByTitle(title);
        // 如果 addReadings 为 true，文章阅读量加一
        if (addReadings) {
            article.setReadings(article.getReadings()+1);
            articleDao.update(article);
        }
        return article;
    }

    @Override
    public int insert(Article article) {
        // 文章标题已存在
        if (articleDao.findByTitle(article.getTitle()) != null) {
            return 0;
        } else {
            // 设置文章发布时间。new Date()为获取当前系统时间
            article.setPublishDate(dateFormat.format(new Date()));
            if (articleDao.insert(article) > 0)
                return 1;
            else
                return -1;
        }

    }

    @Override
    public boolean delete(int id) {
        if (articleDao.delete(id) > 0)
            return true;
        else
            return false;
    }

    @Override
    public int update(Article article) {
        // 文章标题已存在
        if (articleDao.findByTitle(article.getTitle()) != null) {
            return 0;
        } else {
            // 设置文章更新时间。new Date()为获取当前系统时间
            article.setUpdateDate(dateFormat.format(new Date()));
            if (articleDao.update(article) > 0)
                return 1;
            else
                return -1;
        }
    }
}
