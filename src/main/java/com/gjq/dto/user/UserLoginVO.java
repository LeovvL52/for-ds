package com.gjq.dto.user;

import lombok.Data;

/**
 * 用户登录响应DTO
 */
@Data
public class UserLoginVO {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色(0:普通用户 1:管理员)
     */
    private Integer role;

    /**
     * 状态(0:禁用 1:启用)
     */
    private Integer status;
} 