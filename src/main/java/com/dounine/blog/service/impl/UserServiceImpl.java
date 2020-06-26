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
        // 由于用户名唯一，所以返回的列表中至多只有一个用户
        List<User> userList = userDao.findByUsername(name);
        if (userList.size() == 1) userList.get(0).setPassword("");    // 防止将密码泄露
        return userList.get(0);
    }

    @Override
    public int insert(User user) {
        /* TODO: 密码加密存储。 */
        // 由于用户名唯一，所以返回的列表中至多只有一个用户
        List<User> userList = userDao.findByUsername(user.getUsername());

        // 用户名已存在
        if (userList != null && userList.size() == 1) {
            return 0;
        } else {
            user.setRole("normal"); // 一般注册的用户都是普通用户
            if (userDao.insert(user) > 0) return 1;
            else return -1;
        }
    }

    @Override
    public boolean delete(int userId) {
        int rows = userDao.delete(userId);
        if (rows > 0) return true;
        else return false;
    }

    @Override
    public boolean update(User user) {
        /* TODO: 密码加密存储。 */
        int rows = userDao.update(user);
        if (rows > 0) return true;
        else return false;
    }

    @Override
    public int checkUser(String username, String password) {
        // 由于用户名唯一，所以返回的列表中至多只有一个用户
        List<User> userList = userDao.findByUsername(username);

        // 用户存在
        if (userList != null && userList.size() == 1) {
            /* TODO: password加密后比较。 */
            if (userList.get(0).getPassword().equals(password)) {
                return 1;   // 用户名和密码正确
            } else {
                return -1;  // 密码错误
            }
        } else {
            return 0;       // 用户不存在
        }
    }
}
