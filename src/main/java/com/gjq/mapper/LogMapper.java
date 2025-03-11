package com.gjq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gjq.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统日志Mapper接口
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {
    /**
     * 分页查询系统日志列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param type 日志类型
     * @param status 操作状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    IPage<Log> selectLogPage(
            Page<Log> page,
            @Param("userId") Long userId,
            @Param("type") Integer type,
            @Param("status") Integer status,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );

    /**
     * 清空系统日志
     */
    void clearLog();
} 