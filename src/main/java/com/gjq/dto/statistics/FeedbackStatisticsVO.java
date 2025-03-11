package com.gjq.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用户反馈统计数据 VO
 */
@Data
@Schema(description = "用户反馈统计数据")
public class FeedbackStatisticsVO {

    @Schema(description = "反馈总数")
    private Integer totalCount;

    @Schema(description = "已处理数")
    private Integer handledCount;

    @Schema(description = "处理中数")
    private Integer processingCount;

    @Schema(description = "未处理数")
    private Integer pendingCount;

    @Schema(description = "反馈趋势数据")
    private List<Map<String, Object>> trendData;

    @Schema(description = "反馈类型分布")
    private List<Map<String, Object>> typeDistribution;

    @Schema(description = "反馈来源分布")
    private List<Map<String, Object>> sourceDistribution;

    @Schema(description = "平均处理时长(小时)")
    private Double avgHandleTime;

    @Schema(description = "满意度评分")
    private Double satisfactionScore;
} 