package com.dounine.blog.dao;

import com.dounine.blog.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @project dou-blog
 * @class UserDao
 * @author douNine
 * @date 2020/6/26 15:48
 * @description TODO
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int countUsers() {
        return jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
    }

    public List<User> listByPage(int page, int size) {
        return jdbcTemplate.query("select * from user limit ?, ?",
                new BeanPropertyRowMapper(User.class), (page-1)*size, size);
    }

    public User findById(int id) {
        return (User) jdbcTemplate.queryForObject("select * from user where id = ?",
                new BeanPropertyRowMapper(User.class), id);
    }

    public List<User> findByUsername(String username) {
        return jdbcTemplate.query("select * from user where username = ?",
                new BeanPropertyRowMapper(User.class), username);
    }

    public int insert(User user) {
        String sql = "insert into user(username, password, sex, phone, email, qq, profile, birthday, role) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getUsername(), user.getPassword(), user.getSex(), user.getPhone(),
                user.getEmail(), user.getQq(), user.getProfile(), user.getBirthday(), user.getRole());
    }

    public int delete(int userId) {
        return jdbcTemplate.update("delete from user where id = ?", userId);
    }

    public int update(User user) {
        String sql = "update user set username=?, password=?, sex=?, phone=?, " +
                "email=?, qq=?, profile=?, birthday=?, role=?" +
                "where id=?";
        return jdbcTemplate.update(sql,
                user.getUsername(), user.getPassword(), user.getSex(), user.getPhone(),
                user.getEmail(), user.getQq(), user.getProfile(), user.getBirthday(), user.getRole(),
                user.getId());
    }
}
