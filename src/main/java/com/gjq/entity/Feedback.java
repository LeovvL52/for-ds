package com.gjq.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户反馈
 */
@Data
@TableName("feedback")
public class Feedback {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 反馈类型：1-故障报修，2-意见建议，3-其他
     */
    private Integer type;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 处理状态：0-未处理，1-处理中，2-已处理
     */
    private Integer status;

    /**
     * 回复内容
     */
    private String reply;

    /**
     * 满意度评分
     */
    private Double satisfactionScore;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 