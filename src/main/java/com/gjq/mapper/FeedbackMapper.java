package com.gjq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gjq.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户反馈Mapper接口
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
    /**
     * 分页查询用户反馈列表
     *
     * @param page 分页参数
     * @param id 反馈ID
     * @param userId 用户ID
     * @param type 反馈类型
     * @param status 处理状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    IPage<Feedback> selectFeedbackPage(
            Page<Feedback> page,
            @Param("id") Long id,
            @Param("userId") Long userId,
            @Param("type") Integer type,
            @Param("status") Integer status,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
} 