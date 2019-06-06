package com.dounine.myblog.controller;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "sessionFilter",urlPatterns = {"/api/*"})
public class SessionFilter implements javax.servlet.Filter {

    // 管理员才能访问的路径
    private String[] adminUris = {"/api/user/listAll", "/api/user/delete",
                                "/api/article/submit", "/api/article/delete",
                                "/api/comment/update", "/api/comment/delete"};

    // 未登录也可访问的路径
    private String[] openUris = {"/api/user/login", "/api/user/register"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        System.out.println("filter url: " + uri);

        JSONObject retMsg = new JSONObject();

        // 如果uri不在公开路由中
        if (!isExist(uri, openUris)) {
            // 如果已经登录过(即留下了session)
            if (session != null && session.getAttribute("username") != null) {
                System.out.println("该用户已经登录");
                // 如果不是管理员，却访问管理员路径，则返回状态码3(莫得权限)
                if (!((String)session.getAttribute("role")).equals("admin") && isExist(uri, adminUris)) {
                    System.out.println("该用户不是管理员");
                    retMsg.put("code", 3);
                    retMsg.put("msg", "you don't have authority");
                    try {
                        response.getWriter().write(JSONObject.toJSONString(retMsg));
                        return; // 直接返回响应，不传递到controller
                    }
                    catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
            // 如果还没登录
            else {
                System.out.println("该用户未登录");
                retMsg.put("code", 2);
                retMsg.put("msg", "you haven't login");
                try {
                    response.getWriter().write(JSONObject.toJSONString(retMsg));
                    return; // 直接返回响应，不传递到controller
                }
                catch (IOException e) {
                    System.out.println(e);
                }
            }
        }

        // 访问公开路由或管理员用户，传递到controller
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        catch (ServletException e) {
            System.out.println(e);
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
