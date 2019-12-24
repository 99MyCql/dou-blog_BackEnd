package com.dounine.blog.service;

import com.dounine.blog.bean.User;

import java.util.List;

public interface UserService {

    public int countUsers();

    public List<User> listAllUsers(int page, int size);

    public User findById(int userId);

    public User findByName(String userName);

    public int insert(User user);

    public int delete(int userId);

    public int update(User newUser);
}
