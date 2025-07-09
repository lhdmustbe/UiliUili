package com.example.bili.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("user_follows")
public class UserFollows implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关注者
     */
    private Long userId;

    /**
     * 被关注者
     */
    private Long followedUserId;

    private LocalDateTime followTime;
}
