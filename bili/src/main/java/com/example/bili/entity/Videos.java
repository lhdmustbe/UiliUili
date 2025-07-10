package com.example.bili.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.example.bili.entity.Users;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Baomidou
 * @since 2025-07-09
 */
@Getter
@Setter
@ToString
public class Videos implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "video_id", type = IdType.AUTO)
    private Long videoId;

    /**
     * 上传用户ID
     */
    private Long userId;


    private String title;

    private String description;

    /**
     * 封面图URL
     */
    private String coverUrl;

    /**
     * 视频时长(秒)
     */
    private Integer duration;

    /**
     * 视频分类
     */
    private Integer categoryId;

    /**
     * 标签，逗号分隔
     */
    private String tags;

    /**
     * 1-原创 2-转载
     */
    private Byte copyright;

    private LocalDateTime publishTime;

    /**
     * 0-审核中 1-已发布 2-审核不通过 3-用户删除
     */
    private Byte status;

    /**
     * 播放量
     */
    private Long viewCount;

    /**
     * 弹幕数
     */
    private Integer danmakuCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 投币数
     */
    private Integer coinCount;

    /**
     * 收藏数
     */
    private Integer favoriteCount;

    /**
     * 分享数
     */
    private Integer shareCount;

    private String videoUrl;
}
