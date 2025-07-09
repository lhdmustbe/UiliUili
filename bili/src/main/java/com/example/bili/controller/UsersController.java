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
        Users dbUser = usersService.lambdaQuery()
                .eq(Users::getUsername, user.getUsername())
                .one();
        if (dbUser == null) {
            return Result.fail("用户不存在");
        }
        if (!dbUser.getPasswordHash().equals(user.getPasswordHash())) {
            return Result.fail("密码错误");
        }
        String token = "fake-jwt-token-" + dbUser.getUserId();
        return Result.success(token);
    }
}