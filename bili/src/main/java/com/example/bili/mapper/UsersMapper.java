package com.example.bili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bili.entity.Users;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2025-07-09
 */

public interface UsersMapper extends BaseMapper<Users> {
    // 注意：这个方法是为了演示自定义查询，
    // 实际上可以通过MyBatis-Plus的lambdaQuery方法实现相同的功能：
    // usersService.lambdaQuery().eq(Users::getUsername, username).one();
    @Select("SELECT * FROM users WHERE username = #{username}")
    Users selectByUsername(String username);

    @Update("UPDATE users SET username = #{username} WHERE id = #{id}")
    int updateUsernameById(String username, Integer id);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Integer id);
}