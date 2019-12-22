package com.dounine.blog.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/***
 * @project dou-blog
 * @class RetMsgHandler
 * @author douNine
 * @date 2019/12/22 17:20
 * @description 对 Http Response 返回信息数据的规范处理类
 */
public class RetMsgHandler {
    /***
     * 状态码定义
     */
    public static final int SUCCESS_CODE = 0;   // 成功
    public static final int ERROR_CODE = 1;     // 出现错误
    public static final int FAIL_CODE = 2;      // 失败

    /***
     * 组合成map类型的返回信息
     * @param code 状态码
     * @param msg 信息
     * @param data 数据
     * @return Map{
     *     code 状态码
     *     msg 信息
     *     data 数据
     * }
     */
    static public JSONObject getRetMsg(int code, String msg, Object data) {
        JSONObject retMsg = new JSONObject();   // 创建 Json 对象
        retMsg.put("code", code);               // 状态码
        retMsg.put("msg", msg);                 // 提示信息
        retMsg.put("data", data);               // 数据
        return retMsg;
    }

    /***
     * 没有数据，根据bool值返回正确或错误信息
     * @param code 状态码
     * @param msg 信息
     * @return Map{
     *     code 状态码
     *     msg 信息
     * }
     */
    static public JSONObject getRetMsg(int code, String msg) {
        JSONObject retMsg = new JSONObject();   // 创建 Json 对象
        retMsg.put("code", code);               // 状态码
        retMsg.put("msg", msg);                 // 提示信息
        return retMsg;
    }

}
