package com.dashwood.myspring.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * @author 陈喜骋
 * @date 20170828
 */
public class ResultJSON extends JSONObject{
    private static final long serialVersionUID = 1L;
    /**
     * 构造器
     */
    public ResultJSON() {

    }

    /**
     * 传参构造器
     * @param status 状态值 true 正确 false 错误
     */
    public ResultJSON(boolean status) {
        put("status", status);
    }

    /**
     * 执行错误
     */
    public static ResultJSON error() {
        return new ResultJSON(false);
    }

    /**
     * 执行错误
     * @param message 错误信息
     * @return ResultJSON
     */
    public static ResultJSON error(String message) {

        return error().put("message", message);
    }

    /**
     * 执行正确
     */
    public static ResultJSON ok() {
        return new ResultJSON(true);
    }

    /**
     * 执行正确
     * @param message 正确信息
     * @return
     */
    public static ResultJSON ok(String message) {
        return ok().put("message", message);
    }

    /**
     * 设置参数
     * @param key 键
     * @param object 值
     * @return
     */
    public ResultJSON put(String key, Object object) {
        super.put(key, object);
        return this;
    }


    public static void main(String[] args) {
        ResultJSON resultJSON = ResultJSON.ok();
        System.out.println(ResultJSON.ok("正确").put("name", "cxc").put("age",26));
        System.out.println(ResultJSON.error("错误"));
    }

}
