package com.dounine.blog.service.impl;

import com.dounine.blog.bean.User;
import com.dounine.blog.dao.UserDao;
import com.dounine.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    public int countUsers() {
        return userDao.countUsers();
    }

    @Override
    public List<User> listByPage(int page, int size) {
        return userDao.listByPage(page, size);
    }

    @Override
    public User findById(int id) {
        User user = userDao.findById(id);
        user.setPassword("");   // 防止密码泄露
        return user;
    }

    @Override
    public User findByName(String name) {
        User user = userDao.findByUsername(name);
        user.setPassword("");   // 防止密码泄露
        return user;
    }

    @Override
    public int insert(User newUser) {
        /* TODO: 密码加密存储。 */
        User user = userDao.findByUsername(newUser.getUsername());

        // 用户名已存在
        if (user != null) {
            return 0;
        } else {
            newUser.setRole("normal"); // 一般注册的用户都是普通用户
            if (userDao.insert(newUser) > 0)
                return 1;
            else
                return -1;
        }
    }

    @Override
    public boolean delete(int userId) {
        if (userDao.delete(userId) > 0)
            return true;
        else
            return false;
    }

    @Override
    public int update(User user) {
        /* TODO: 密码加密存储。 */
        if (!userDao.findById(user.getId()).getUsername().equals(user.getUsername())
                && userDao.findByUsername(user.getUsername()) != null) {
            return 0;
        } else {
            if (userDao.update(user) > 0)
                return 1;
            else
                return -1;
        }
    }

    @Override
    public int checkUser(String username, String password) {
        User user = userDao.findByUsername(username);

        // 用户存在
        if (user != null) {
            /* TODO: password加密后比较。 */
            if (user.getPassword().equals(password)) {
                return 1;   // 用户名和密码正确
            } else {
                return -1;  // 密码错误
            }
        } else {
            return 0;       // 用户不存在
        }
    }
}
