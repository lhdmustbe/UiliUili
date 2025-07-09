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
public class Danmakus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "danmaku_id", type = IdType.AUTO)
    private Long danmakuId;

    private String content;

    private Long userId;

    private Long videoId;

    private Long partId;

    private String color;

    private Byte fontSize;

    /**
     * 1-滚动 2-顶部 3-底部
     */
    private Byte position;

    /**
     * 弹幕出现时间(秒)
     */
    private Double sendTime;

    private LocalDateTime publishTime;
}
