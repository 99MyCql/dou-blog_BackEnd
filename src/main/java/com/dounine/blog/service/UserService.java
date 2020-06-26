package com.dounine.blog.service;

import com.dounine.blog.bean.User;

import java.util.List;

public interface UserService {

    public int countUsers();

    public List<User> listByPage(int page, int size);

    public User findById(int userId);

    public User findByName(String name);

    /**
     * 添加用户
     * @param user
     * @return 0 表示用户名已存在；1 表示插入成功；-1 表示插入失败。
     */
    public int insert(User user);

    public boolean delete(int userId);

    public boolean update(User user);

    /**
     * 检查用户名和密码是否正确
     * @param username
     * @param password
     * @return 0 表示用户不存在；1 表示正确；-1 表示密码错误。
     */
    public int checkUser(String username, String password);
}
