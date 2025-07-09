package com.example.bili.service.impl;

import com.example.bili.entity.Comments;
import com.example.bili.mapper.CommentsMapper;
import com.example.bili.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2025-07-09
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

}
