package com.gjq.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 设备状态统计数据 VO
 */
@Data
@Schema(description = "设备状态统计数据")
public class DeviceStatusStatisticsVO {

    @Schema(description = "设备总数")
    private Integer totalCount;

    @Schema(description = "在线设备数")
    private Integer onlineCount;

    @Schema(description = "离线设备数")
    private Integer offlineCount;

    @Schema(description = "故障设备数")
    private Integer faultCount;

    @Schema(description = "设备状态分布")
    private List<Map<String, Object>> statusDistribution;

    @Schema(description = "设备在线率趋势")
    private List<Map<String, Object>> onlineRateTrend;

    @Schema(description = "设备故障类型分布")
    private List<Map<String, Object>> faultTypeDistribution;
} 