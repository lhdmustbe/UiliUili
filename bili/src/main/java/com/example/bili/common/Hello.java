package com.example.bili.common;

import com.example.bili.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Hello {
    @Autowired
    private IUserService iUserService;

    @GetMapping
    public String hello() {
        return "111";
    }

    @GetMapping("/list")
    public List<User> list() {
        return iUserService.list();
    }


}
