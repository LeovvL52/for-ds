package com.gjq.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 系统概览统计数据 VO
 */
@Data
@Schema(description = "系统概览统计数据")
public class SystemOverviewVO {

    @Schema(description = "今日火灾事件数")
    private Integer todayFireEvents;

    @Schema(description = "今日检查次数")
    private Integer todayInspections;

    @Schema(description = "今日新增反馈")
    private Integer todayFeedbacks;

    @Schema(description = "在线设备数")
    private Integer onlineDevices;

    @Schema(description = "系统运行天数")
    private Integer runningDays;

    @Schema(description = "本周事件趋势")
    private List<Map<String, Object>> weeklyTrend;

    @Schema(description = "设备状态分布")
    private List<Map<String, Object>> deviceStatus;

    @Schema(description = "近期告警TOP5")
    private List<Map<String, Object>> recentAlarms;

    @Schema(description = "待处理事项")
    private Map<String, Integer> pendingItems;

    /**
     * 消防检查状态分布
     */
    private Map<String, Integer> inspectionStatus;
} 