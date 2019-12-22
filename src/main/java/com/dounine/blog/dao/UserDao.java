package com.dounine.blog.dao;

import com.dounine.blog.bean.User;

import java.util.List;

public interface UserDao {
    public List<User> listAllUsers();

    public User findById(int userId);

    public User findByName(String userName);

    public int insert(User user);

    public int delete(int userId);

    public int update(User newUser);
}
