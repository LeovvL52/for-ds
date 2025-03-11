package com.gjq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gjq.dto.LogQueryDTO;
import com.gjq.entity.Log;

/**
 * 系统日志Service接口
 */
public interface LogService extends IService<Log> {
    /**
     * 分页查询系统日志列表
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    IPage<Log> getLogPage(LogQueryDTO queryDTO);

    /**
     * 获取系统日志详情
     *
     * @param id 日志ID
     * @return 日志详情
     */
    Log getLogById(Long id);

    /**
     * 删除系统日志
     *
     * @param id 日志ID
     */
    void deleteLog(Long id);

    /**
     * 清空系统日志
     */
    void clearLog();

    /**
     * 记录登录日志
     *
     * @param userId 用户ID
     * @param status 登录状态
     * @param ip IP地址
     * @param error 错误信息
     */
    void recordLoginLog(Long userId, Integer status, String ip, String error);

    /**
     * 记录操作日志
     *
     * @param userId 用户ID
     * @param module 操作模块
     * @param description 操作描述
     * @param method 请求方法
     * @param params 请求参数
     * @param ip IP地址
     */
    void recordOperationLog(Long userId, String module, String description, String method, String params, String ip);

    /**
     * 记录异常日志
     *
     * @param userId 用户ID
     * @param module 操作模块
     * @param description 操作描述
     * @param method 请求方法
     * @param params 请求参数
     * @param ip IP地址
     * @param error 错误信息
     */
    void recordErrorLog(Long userId, String module, String description, String method, String params, String ip, String error);
} 