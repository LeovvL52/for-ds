package com.gjq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gjq.dto.log.SystemLogCreateDTO;
import com.gjq.dto.log.SystemLogQueryDTO;
import com.gjq.dto.log.SystemLogVO;
import com.gjq.entity.SystemLog;

/**
 * 系统日志服务接口
 */
public interface SystemLogService extends IService<SystemLog> {
    /**
     * 创建系统日志
     *
     * @param dto 创建参数
     * @return 日志ID
     */
    Long createSystemLog(SystemLogCreateDTO dto);

    /**
     * 删除系统日志
     *
     * @param id 日志ID
     */
    void deleteSystemLog(Long id);

    /**
     * 分页查询系统日志列表
     *
     * @param dto 查询参数
     * @return 日志列表
     */
    IPage<SystemLogVO> getSystemLogPage(SystemLogQueryDTO dto);

    /**
     * 获取系统日志详情
     *
     * @param id 日志ID
     * @return 日志详情
     */
    SystemLogVO getSystemLogById(Long id);

    /**
     * 清空系统日志
     */
    void clearSystemLog();
} 