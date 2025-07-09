package com.example.bili.common;

import lombok.Data;

@Data
public class Result {
    private int code;   // 状态码：200成功，400失败
    private String msg; // 消息提示
    private long total; // 总记录数（用于分页）
    private Object data; // 返回的数据

    private Result() {
    }

    // ------------------- 成功静态方法 -------------------
    public static Result success() {
        return result(200, "成功", 0L, null);
    }

    public static Result success(Object data) {
        return result(200, "成功", 0L, data);
    }

    public static Result success(Object data, long total) {
        return result(200, "成功", total, data);
    }

    // ------------------- 失败静态方法 -------------------
    public static Result fail() {
        return result(400, "失败", 0L, null);
    }

    public static Result fail(String msg) {
        return result(400, msg, 0L, null);
    }

    // ------------------- 通用构建方法 -------------------
    public static Result result(int code, String msg, long total, Object data) {
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }

    // 添加链式调用支持
    public Result code(int code) {
        this.code = code;
        return this;
    }

    public Result msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result data(Object data) {
        this.data = data;
        return this;
    }

}
