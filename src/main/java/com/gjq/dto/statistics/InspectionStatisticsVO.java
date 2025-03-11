package com.gjq.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 消防检查统计数据 VO
 */
@Data
@Schema(description = "消防检查统计数据")
public class InspectionStatisticsVO {

    @Schema(description = "检查总数")
    private Integer totalCount;

    @Schema(description = "正常检查数")
    private Integer normalCount;

    @Schema(description = "异常检查数")
    private Integer abnormalCount;

    @Schema(description = "待整改数")
    private Integer pendingCount;

    @Schema(description = "检查趋势数据")
    private List<Map<String, Object>> trendData;

    @Schema(description = "异常类型分布")
    private List<Map<String, Object>> abnormalTypeDistribution;

    @Schema(description = "检查区域分布")
    private List<Map<String, Object>> areaDistribution;

    @Schema(description = "整改完成率")
    private Double completionRate;
} 