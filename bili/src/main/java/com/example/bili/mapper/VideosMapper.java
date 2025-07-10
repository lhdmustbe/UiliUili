package com.example.bili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bili.entity.Videos;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2025-07-09
 */
public interface VideosMapper extends BaseMapper<Videos> {
    @Update("UPDATE videos SET view_count = view_count + 1 WHERE video_id = #{videoId}")
    void increaseViewCount(Long videoId);
}
