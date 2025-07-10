package com.example.bili.common;

import lombok.Data;

@Data
public class Result<V> {
    private int code;   // 状态码：200成功，400失败
    private String msg; // 消息提示
    private long total; // 总记录数（用于分页）
    private V data;     // 返回的数据(使用泛型)

    private Result() {
    }

    // ------------------- 成功静态方法 -------------------
    public static <V> Result<V> success() {
        return result(200, "成功", 0L, null);
    }

    public static <V> Result<V> success(V data) {
        return result(200, "成功", 0L, data);
    }

    public static <V> Result<V> success(V data, long total) {
        return result(200, "成功", total, data);
    }

    // ------------------- 失败静态方法 -------------------
    public static <V> Result<V> fail() {
        return result(400, "失败", 0L, null);
    }

    public static <V> Result<V> fail(String msg) {
        return result(400, msg, 0L, null);
    }

    // ------------------- 通用构建方法 -------------------
    public static <V> Result<V> result(int code, String msg, long total, V data) {
        Result<V> res = new Result<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }

    // ------------------- Builder模式 -------------------
    public static <V> Builder<V> builder() {
        return new Builder<>();
    }

    public static class Builder<V> {
        private final Result<V> result = new Result<>();

        public Builder<V> code(int code) {
            result.setCode(code);
            return this;
        }

        public Builder<V> msg(String msg) {
            result.setMsg(msg);
            return this;
        }

        public Builder<V> total(long total) {
            result.setTotal(total);
            return this;
        }

        public Builder<V> data(V data) {
            result.setData(data);
            return this;
        }

        public Result<V> build() {
            return result;
        }
    }

    // ------------------- 链式调用 -------------------
    public Result<V> code(int code) {
        this.code = code;
        return this;
    }

    public Result<V> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<V> total(long total) {
        this.total = total;
        return this;
    }

    public Result<V> data(V data) {
        this.data = data;
        return this;
    }
}
