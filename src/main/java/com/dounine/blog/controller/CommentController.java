package com.dounine.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.Article;
import com.dounine.blog.bean.Comment;
import com.dounine.blog.bean.User;
import com.dounine.blog.service.ArticleService;
import com.dounine.blog.service.CommentService;
import com.dounine.blog.service.UserService;
import com.dounine.blog.util.RetMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public JSONObject listAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        List<Comment> commentList = commentService.listAllComments(page, size); // 根据 page, size 查询所有评论

        // 根据每条评论中的 commenterId 和 articleId ，查询对应的作者名和文字标题。如此操作是为了方便前端
        for (Comment comment : commentList) {
            User user = userService.findById(comment.getCommenterId());         // 查询发表该评论的用户
            comment.setCommenterName(user.getName());                           // 设置 comment 中的用户名
            Article article = articleService.findById(comment.getArticleId());  // 查询评论对应的文章
            comment.setArticleTitle(article.getArticleTitle());                 // 设置 comment 中的文章标题
        }
        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "list comments successfully",
                JSONObject.toJSONString(commentList));
    }

    @RequestMapping(value = "/listByArticleId", method = RequestMethod.GET)
    public JSONObject listByArticleId(@RequestParam int articleId) {

        JSONObject retMsg; // 返回信息

        List<Comment> commentList = commentService.listByArticleId(articleId); // 根据 articleId 查询评论

        // 根据每条评论中的 commenterId ，查询对应的作者名。如此操作是为了方便前端
        for (Comment comment : commentList) {
            User user = userService.findById(comment.getCommenterId());  // 查询发表该评论的用户
            comment.setCommenterName(user.getName());                    // 在 Comment 对象中设置用户名
        }

        // 设置返回信息
        retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "list comments by this article successfully", JSONObject.toJSONString(commentList));
        return retMsg;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public JSONObject findById(@RequestParam int id) {

        JSONObject retMsg; // 返回信息

        Comment comment = commentService.findById(id);  // 通过 id 查询 comment
        // 如果 comment 不存在
        if (comment == null) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "find this comment fail");
        }
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "find this comment successfully", JSONObject.toJSONString(comment));
        }
        return retMsg;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JSONObject insert(@RequestBody Comment comment, HttpServletRequest request) {

        JSONObject retMsg; // 返回信息

        Article article = articleService.findById(comment.getArticleId());  // 找到该评论对应的文章
        article.setComments(article.getComments()+1);                       // 文章评论数加一
        articleService.update(article);                                     // 更新该文章

        comment.setCommentDate(dateFormat.format(new Date())); // 记录评论时间

        HttpSession session = request.getSession();                         // 获取 session
        comment.setCommenterId((int)session.getAttribute("userId"));    // 从session中获取当前用户 id

        // 插入数据成功
        if (commentService.insert(comment) > 0) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "insert comment successfully");
        }
        // 插入失败
        else {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "insert comment error");
        }

        return retMsg;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject delete(@RequestParam int id) {

        JSONObject retMsg; // 返回信息

        Comment comment = commentService.findById(id); // 根据 id 查询 comment
        // 如果 comment 不存在
        if (comment == null) {
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "find this comment fail");
        }
        else {
            Article article = articleService.findById(comment.getArticleId());  // 查询该评论对应的文章
            article.setComments(article.getComments()-1);                       // 文章评论数减一
            articleService.update(article);                                     // 更新文章

            // 删除数据成功
            if (commentService.delete(id) > 0) {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "delete comment successfully");

            }
            // 删除失败
            else {
                retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "delete comment error");
            }
        }

        return retMsg;
    }
}
