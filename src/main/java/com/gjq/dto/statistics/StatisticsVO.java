package com.gjq.dto.statistics;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 统计分析返回对象
 */
@Data
public class StatisticsVO {
    /**
     * 火灾事件等级分布
     * key: 火情等级(1-5)
     * value: 数量
     */
    private Map<Integer, Long> fireEventLevelCount;

    /**
     * 火灾事件处理状态分布
     * key: 处理状态(0:未处理 1:处理中 2:已处理)
     * value: 数量
     */
    private Map<Integer, Long> fireEventStatusCount;

    /**
     * 最近7天火灾事件趋势
     * key: 日期
     * value: 数量
     */
    private Map<String, Long> fireEventTrend;

    /**
     * 摄像头设备状态分布
     * key: 状态(0:离线 1:在线 2:故障)
     * value: 数量
     */
    private Map<Integer, Long> cameraStatusCount;

    /**
     * 消防检查结果分布
     * key: 状态(0:异常 1:正常)
     * value: 数量
     */
    private Map<Integer, Long> inspectionStatusCount;

    /**
     * 最近7天消防检查趋势
     * key: 日期
     * value: 数量
     */
    private Map<String, Long> inspectionTrend;

    /**
     * 用户反馈类型分布
     * key: 反馈类型(1:故障报修 2:意见建议 3:其他)
     * value: 数量
     */
    private Map<Integer, Long> feedbackTypeCount;

    /**
     * 用户反馈处理状态分布
     * key: 处理状态(0:未处理 1:处理中 2:已处理)
     * value: 数量
     */
    private Map<Integer, Long> feedbackStatusCount;

    /**
     * 最近7天用户反馈趋势
     * key: 日期
     * value: 数量
     */
    private Map<String, Long> feedbackTrend;

    /**
     * 高危区域Top5
     * key: 位置
     * value: 火灾事件数量
     */
    private List<Map<String, Object>> dangerousAreaTop5;
} 