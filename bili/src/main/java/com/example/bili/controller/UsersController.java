package com.example.bili.controller;

import com.example.bili.common.Result;
import com.example.bili.entity.Users;
import com.example.bili.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Baomidou
 * @since 2025-07-09
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private IUsersService usersService;

    @GetMapping("/list")
    public List<Users> list() {
        return usersService.list();
    }

    @PostMapping("/login")
    public Result login(@RequestBody Users user) {
        // 1. 参数验证
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return Result.fail("用户名不能为空");
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty()) {
            return Result.fail("密码不能为空");
        }

        // 2. 查询用户
        Users dbUser = usersService.lambdaQuery()
                .eq(Users::getUsername, user.getUsername())
                .one();

        // 3. 验证用户
        if (dbUser == null) {
            return Result.fail("用户不存在");
        }

        // 4. 密码验证 (注意：实际项目应使用加密验证)
        if (!dbUser.getPasswordHash().equals(user.getPasswordHash())) {
            return Result.fail("用户名或密码错误"); // 模糊提示更安全
        }

        String token = "fake-jwt-token-" + dbUser.getUserId();
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("username", dbUser.getUsername()); // 使用数据库中的用户名
        data.put("email", dbUser.getEmail());
        data.put("phone", dbUser.getPhone());
        return Result.success(data);


    }

    @PostMapping("/register")
    public Result register(@RequestBody Users user) {
        // 1. 参数校验
        if (!StringUtils.hasText(user.getUsername())) {
            return Result.fail("用户名不能为空");
        }
        if (user.getUsername().length() < 3 || user.getUsername().length() > 16) {
            return Result.fail("用户名长度需在3-16个字符之间");
        }
        if (!StringUtils.hasText(user.getPasswordHash())) {
            return Result.fail("密码不能为空");
        }
        if (user.getPasswordHash().length() < 6 || user.getPasswordHash().length() > 20) {
            return Result.fail("密码长度需在6-20个字符之间");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            return Result.fail("邮箱不能为空");
        }
        if (!user.getEmail().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
            return Result.fail("邮箱格式不正确");
        }
        if (!StringUtils.hasText(user.getPhone())) {
            return Result.fail("手机号不能为空");
        }
        if (!user.getPhone().matches("^1[3-9]\\d{9}$")) {
            return Result.fail("手机号格式不正确");
        }

        // 2. 检查用户名是否已存在
        if (usersService.lambdaQuery()
                .eq(Users::getUsername, user.getUsername())
                .exists()) {
            return Result.fail("用户名已被注册");
        }

        // 3. 检查邮箱是否已注册
        if (usersService.lambdaQuery()
                .eq(Users::getEmail, user.getEmail())
                .exists()) {
            return Result.fail("邮箱已被注册");
        }

        // 4. 检查手机号是否已注册
        if (usersService.lambdaQuery()
                .eq(Users::getPhone, user.getPhone())
                .exists()) {
            return Result.fail("手机号已被注册");
        }

        // 5. 保存用户信息（实际项目应该对密码进行加密存储）
        boolean success = usersService.save(user);
        if (!success) {
            return Result.fail("注册失败，请稍后重试");
        }

        // 6. 返回成功响应
        return Result.success("注册成功");
    }
}