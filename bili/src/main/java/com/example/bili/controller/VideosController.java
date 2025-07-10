package com.example.bili.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bili.common.Result;
import com.example.bili.entity.Videos;
import com.example.bili.service.IVideosService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频控制器
 */
@RestController
@RequestMapping("/videos")
public class VideosController {

    @Resource
    private IVideosService videosService;

    /**
     * 新增视频
     */
    @PostMapping
    public Result<Videos> save(@RequestBody Videos video) {
        videosService.save(video);
        return Result.success(video);
    }

    /**
     * 更新视频信息
     */
    @PutMapping
    public Result<Videos> update(@RequestBody Videos video) {
        videosService.updateById(video);
        return Result.success(video);
    }

    /**
     * 删除视频
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        videosService.removeById(id);
        return Result.success();
    }

    /**
     * 根据ID查询视频详情
     */
    @GetMapping("/{id}")
    public Result<Videos> findById(@PathVariable Long id) {
        Videos video = videosService.getById(id);
        return Result.success(video);
    }

    /**
     * 分页查询视频列表
     */
    @GetMapping("/page")
    public Result<Page<Videos>> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long userId) {

        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            queryWrapper.like("title", title);
        }
        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        }
        queryWrapper.orderByDesc("publish_time");

        Page<Videos> page = videosService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

    /**
     * 获取用户上传的视频列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<Videos>> findByUserId(@PathVariable Long userId) {
        QueryWrapper<Videos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .orderByDesc("publish_time");
        List<Videos> videos = videosService.list(queryWrapper);
        return Result.success(videos);
    }

    /**
     * 增加视频播放量
     */
    @PostMapping("/{id}/view")
    public Result<?> increaseViewCount(@PathVariable Long id) {
        videosService.increaseViewCount(id);
        return Result.success();
    }
}
