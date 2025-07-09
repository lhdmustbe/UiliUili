package com.example.bili.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
@TableName("video_parts")
public class VideoParts implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "part_id", type = IdType.AUTO)
    private Long partId;

    private Long videoId;

    /**
     * 分集序号
     */
    private Integer partNo;

    private String title;

    /**
     * 分集时长(秒)
     */
    private Integer duration;

    private String videoUrl;

    /**
     * 0-不可用 1-正常
     */
    private Byte status;
}
