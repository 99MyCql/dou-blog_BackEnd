package com.dounine.blog.service.impl;

import com.dounine.blog.bean.User;
import com.dounine.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> listAllUsers(int page, int size) {
        return jdbcTemplate.query("select * from user limit ?, ?", new BeanPropertyRowMapper(User.class), (page-1)*size, size);
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id = ?";
        try {
            User user = (User) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), id);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public User findByName(String userName) {
        String sql = "select * from user where name = ?";
        try {
            User user = (User) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), userName);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int insert(User user) {
        String sql = "insert into user(name, phone, password, gender, personalBrief, email, qq, birthday, role) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getName(), user.getPhone(), user.getPassword(), user.getGender(),
                user.getPersonalBrief(), user.getEmail(), user.getQq(), user.getBirthday(), "normal");
    }

    @Override
    public int delete(int userId) {
        String sql = "delete from user where id = ?";
        return jdbcTemplate.update(sql, userId);
    }

    @Override
    public int update(User newUser) {
        String sql = "update user set name=?, phone=?, password=?, gender=?, " +
                    "personalBrief=?, email=?, qq=?, birthday=?" +
                    "where id=?";
        return jdbcTemplate.update(sql, newUser.getName(), newUser.getPhone(), newUser.getPassword(), newUser.getGender(),
                newUser.getPersonalBrief(), newUser.getEmail(), newUser.getQq(), newUser.getBirthday(),
                newUser.getId());
    }
}
