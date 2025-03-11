package com.gjq.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消防检查记录实体类
 */
@Data
@TableName("inspection_record")
public class InspectionRecord {
    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 检查人ID
     */
    private Long inspectorId;

    /**
     * 检查时间
     */
    private LocalDateTime inspectionTime;

    /**
     * 检查位置
     */
    private String location;

    /**
     * 检查结果
     */
    private String result;

    /**
     * 状态(0:异常 1:正常)
     */
    private Integer status;

    /**
     * 检查图片URLs
     */
    private String imageUrls;

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