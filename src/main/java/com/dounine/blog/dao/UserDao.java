package com.dounine.blog.dao;

import com.dounine.blog.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @project dou-blog
 * @class UserDao
 * @author douNine
 * @date 2020/6/26 15:48
 * @description UserDao
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int countUsers() {
        return jdbcTemplate.queryForObject("select count(*) from \"USER\"", Integer.class);
    }

    public List<User> listByPage(int page, int size) {
        return jdbcTemplate.query("select * from \"USER\" where rownum>? and rownum<=?",
                new BeanPropertyRowMapper(User.class), (page-1)*size, page*size);
    }

    public User findById(int id) {
        try {
            return (User) jdbcTemplate.queryForObject("select * from \"USER\" where id = ?",
                    new BeanPropertyRowMapper(User.class), id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            // 用户名上建立了唯一索引
            return (User) jdbcTemplate.queryForObject("select * from \"USER\" where username = ?",
                    new BeanPropertyRowMapper(User.class), username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insert(User user) {
        String sql = "insert into \"USER\"(username, password, sex, phone, email, qq, profile, birthday, role) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getUsername(), user.getPassword(), user.getSex(), user.getPhone(),
                user.getEmail(), user.getQq(), user.getProfile(), user.getBirthday(), user.getRole());
    }

    public int delete(int userId) {
        return jdbcTemplate.update("delete from \"USER\" where id = ?", userId);
    }

    public int update(User user) {
        String sql = "update \"USER\" set username=?, sex=?, phone=?, " +
                "email=?, qq=?, profile=?, birthday=? " +
                "where id=?";
        return jdbcTemplate.update(sql,
                user.getUsername(), user.getSex(), user.getPhone(),
                user.getEmail(), user.getQq(), user.getProfile(), user.getBirthday(),
                user.getId());
    }
}
