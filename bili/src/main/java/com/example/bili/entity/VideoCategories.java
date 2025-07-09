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
@TableName("video_categories")
public class VideoCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    private String name;

    /**
     * 父分类ID，0表示一级分类
     */
    private Integer parentId;

    private String icon;

    private Integer sortOrder;
}
