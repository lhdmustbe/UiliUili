package com.example.bili.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bili.common.Result;
import com.example.bili.entity.Videos;
import com.example.bili.service.IVideosService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 视频控制器
 */
@RestController
@RequestMapping("/video")
public class VideosController {


    @GetMapping({"/{videoId}/basic", "/{videoId}"})
    public Result getVideoBasicInfo(@PathVariable Long videoId) {
        Videos video = videosService.getById(videoId);
        if (video == null || video.getStatus() != 1) {
            return Result.fail("视频不存在或未发布");
        }
        Map<String, String> data = new HashMap<>();
        data.put("videoId", video.getVideoId().toString());
        data.put("coverUrl", video.getCoverUrl());
        data.put("videoUrl", video.getVideoUrl());
        data.put("title", video.getTitle());
        data.put("description", video.getDescription());
        data.put("tags", video.getTags());
        data.put("publishTime", video.getPublishTime().toString());
        data.put("viewCount", String.valueOf(video.getViewCount()));
        data.put("likeCount", String.valueOf(video.getLikeCount()));
        data.put("favoriteCount", String.valueOf(video.getFavoriteCount()));
        return Result.success(data);
    }

    /**
     * 批量获取视频基础信息（用于推荐流等场景）
     *
     * @param videoIds 视频ID列表，用逗号分隔
     * @return 视频信息列表
     */
    @GetMapping("/batch")
    public Result getBatchVideoBasicInfo(@RequestParam String videoIds) {
        List<Long> ids = Arrays.stream(videoIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Videos> videos = videosService.list(new QueryWrapper<Videos>()
                .in("video_id", ids)
                .eq("status", 1)); // 只查询已发布的视频

        List<Map<String, Object>> result = videos.stream().map(video -> {
            Map<String, Object> item = new HashMap<>();
            item.put("videoId", video.getVideoId());
            item.put("coverUrl", video.getCoverUrl());
            item.put("videoUrl", video.getVideoUrl());
            item.put("title", video.getTitle());
            item.put("duration", video.getDuration());
            return item;
        }).collect(Collectors.toList());

        return Result.success(result);
    }


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

}
