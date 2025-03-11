package com.gjq.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 火灾事件统计数据 VO
 */
@Data
@Schema(description = "火灾事件统计数据")
public class FireEventStatisticsVO {

    @Schema(description = "总事件数")
    private Integer totalCount;

    @Schema(description = "已处理事件数")
    private Integer handledCount;

    @Schema(description = "处理中事件数")
    private Integer processingCount;

    @Schema(description = "未处理事件数")
    private Integer pendingCount;

    @Schema(description = "事件趋势数据")
    private List<Map<String, Object>> trendData;

    @Schema(description = "事件类型分布")
    private List<Map<String, Object>> typeDistribution;

    @Schema(description = "事件位置热力图数据")
    private List<Map<String, Object>> locationHeatmap;
}