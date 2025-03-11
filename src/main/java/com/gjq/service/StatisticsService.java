package com.gjq.service;

import com.gjq.dto.statistics.*;

/**
 * 统计分析服务接口
 */
public interface StatisticsService {

    /**
     * 获取火灾事件统计数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 火灾事件统计数据
     */
    FireEventStatisticsVO getFireEventStatistics(String startTime, String endTime);

    /**
     * 获取设备状态统计数据
     *
     * @return 设备状态统计数据
     */
    DeviceStatusStatisticsVO getDeviceStatusStatistics();

    /**
     * 获取消防检查统计数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 消防检查统计数据
     */
    InspectionStatisticsVO getInspectionStatistics(String startTime, String endTime);

    /**
     * 获取用户反馈统计数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 用户反馈统计数据
     */
    FeedbackStatisticsVO getFeedbackStatistics(String startTime, String endTime);

    /**
     * 获取系统概览统计数据
     *
     * @return 系统概览统计数据
     */
    SystemOverviewVO getSystemOverview();
} 