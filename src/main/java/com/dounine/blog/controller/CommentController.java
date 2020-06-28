package com.dounine.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.dounine.blog.bean.Article;
import com.dounine.blog.bean.Comment;
import com.dounine.blog.service.CommentService;
import com.dounine.blog.util.RetMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public JSONObject count() {
        int count = commentService.countComments();

        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "get the count of comments successfully",
                JSONObject.toJSONString(count));
    }

    @RequestMapping(value = "/listByPage", method = RequestMethod.GET)
    public JSONObject listByPage(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        // 分页查找
        List<Map<String, Object>> commentList = commentService.listExtendByPage(page, size);

        return RetMsgHandler.getRetMsg(
                RetMsgHandler.SUCCESS_CODE,
                "list comments successfully",
                JSONObject.toJSONString(commentList));
    }

    @RequestMapping(value = "/listByArticleId", method = RequestMethod.GET)
    public JSONObject listByArticleId(@RequestParam int articleId,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        /* TODO: 等前端实现评论懒加载后，此处需要用分页查询 */
        List<Map<String, Object>> commentList = commentService.listExtendByArticleId(articleId);

        return RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE,
                "list comments by this article successfully",
                JSONObject.toJSONString(commentList));
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public JSONObject findById(@RequestParam int id) {
        JSONObject retMsg; // 返回信息

        Comment comment = commentService.findById(id);  // 通过 id 查询 comment
        // 如果 comment 不存在
        if (comment == null)
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.FAIL_CODE, "find this comment fail");
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE,
                    "find this comment successfully", JSONObject.toJSONString(comment));
        return retMsg;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JSONObject insert(@RequestBody Comment comment, HttpServletRequest request) {
        JSONObject retMsg;  // 返回信息

        // 根据 session 中的 userId 设置评论者ID
        HttpSession session = request.getSession();
        comment.setCommenterId((int)session.getAttribute("userId"));

        // 插入数据成功
        if (commentService.insert(comment))
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "insert comment successfully");
        // 插入失败
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "insert comment error");

        return retMsg;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject delete(@RequestParam int id) {
        JSONObject retMsg; // 返回信息

        // 删除数据成功
        if (commentService.delete(id))
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.SUCCESS_CODE, "delete comment successfully");
        // 删除失败
        else
            retMsg = RetMsgHandler.getRetMsg(RetMsgHandler.ERROR_CODE, "delete comment error");

        return retMsg;
    }
}
