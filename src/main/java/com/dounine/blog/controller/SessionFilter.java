package com.dounine.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.util.RetMsgHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "sessionFilter",urlPatterns = {"/api/*"})
public class SessionFilter implements javax.servlet.Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 管理员才能访问的路径
    private String[] adminUris = {
            "/api/user/count", "/api/user/listByPage", "/api/user/findById", "/api/user/findByName", "/api/user/delete",
            "/api/article/insert", "/api/article/update", "/api/article/delete",
            "/api/comment/listByPage", "/api/comment/delete"
    };

    // 未登录也可访问的路径
    private String[] openUris = {
            "/api/user/login", "/api/user/register",
            "/api/article/count", "/api/article/listByPage", "/api/article/findById", "/api/article/findByTitle",
            "/api/comment/listByArticleId"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;       // HTTP 请求
        HttpServletResponse response = (HttpServletResponse) servletResponse;   // HTTP 响应
        HttpSession session = request.getSession();                             // session

        String uri = request.getRequestURI();
        logger.info("filter url: " + uri);

        JSONObject retMsg = new JSONObject();

        // 如果uri不在公开路由中
        if (!isExist(uri, openUris)) {
            // 如果已经登录过，即留下了session
            if (session != null && session.getAttribute("username") != null) {
                logger.info("该用户已经登录");
                // 如果不是管理员，却访问管理员路径，则返回莫得权限
                if (!((String)session.getAttribute("userRole")).equals("admin") && isExist(uri, adminUris)) {
                    logger.info("该用户不是管理员");
                    retMsg.put("code", RetMsgHandler.FAIL_CODE);
                    retMsg.put("msg", "permission deny");
                    try {
                        // 直接返回响应，不传递到controller
                        response.getWriter().write(JSONObject.toJSONString(retMsg));
                        return;
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 如果还没登录
            else {
                logger.info("该用户未登录");
                retMsg.put("code", RetMsgHandler.FAIL_CODE);
                retMsg.put("msg", "you haven't login");
                try {
                    // 直接返回响应，不传递到controller
                    response.getWriter().write(JSONObject.toJSONString(retMsg));
                    return;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 访问公开路由或管理员用户，传递到controller
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isExist(String uri, String[] uris) {
        for (String i : uris) {
            if (uri.equals(i))
                return true;
        }
        return false;
    }
}
