package com.example.bili.service.impl;

import com.example.bili.entity.User;
import com.example.bili.mapper.UserMapper;
import com.example.bili.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2025-07-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
