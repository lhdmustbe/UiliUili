package com.example.bili.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    private String content;

    private Long userId;

    private Long videoId;

    /**
     * 回复的评论ID，0表示一级评论
     */
    private Long parentId;

    /**
     * 回复的用户ID
     */
    private Long replyTo;

    private Integer likeCount;

    private LocalDateTime publishTime;

    /**
     * 0-删除 1-正常
     */
    private Byte status;
}
