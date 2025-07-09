package com.example.bili.controller;

import com.example.bili.common.Result;
import com.example.bili.entity.Users;
import com.example.bili.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

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

        // 5. 生成 token (示例用固定值，实际应使用JWT)
        String token = "fake-jwt-token-" + dbUser.getUserId();

        // 6. 返回成功响应
        return Result.success(token);
    }

}