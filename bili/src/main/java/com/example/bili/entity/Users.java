package com.example.bili.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
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
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    private String username;

    private String passwordHash;

    private String email;

    private String phone;

    private String avatarUrl;

    /**
     * 0-未知 1-男 2-女
     */
    private Byte gender;

    private LocalDate birthday;

    /**
     * 个人签名
     */
    private String sign;

    /**
     * 用户等级
     */
    private Byte level;

    /**
     * 经验值
     */
    private Integer exp;

    /**
     * 硬币余额
     */
    private Integer coinBalance;

    /**
     * B币余额
     */
    private Integer bCoinBalance;

    /**
     * 会员状态
     */
    private Byte vipStatus;

    private LocalDateTime vipExpireDate;

    /**
     * 0-禁用 1-正常
     */
    private Byte status;

    private LocalDateTime registerTime;

    private LocalDateTime lastLoginTime;

    private String lastLoginIp;
}
