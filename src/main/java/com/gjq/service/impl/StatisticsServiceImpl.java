package com.gjq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gjq.dto.statistics.*;
import com.gjq.entity.Camera;
import com.gjq.entity.Feedback;
import com.gjq.entity.FireEvent;
import com.gjq.entity.InspectionRecord;
import com.gjq.entity.SystemLog;
import com.gjq.mapper.SystemLogMapper;
import com.gjq.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计分析服务实现类
 */
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final FireEventService fireEventService;
    private final CameraService cameraService;
    private final InspectionRecordService inspectionRecordService;
    private final FeedbackService feedbackService;
    private final SystemLogMapper systemLogMapper;

    @Override
    public FireEventStatisticsVO getFireEventStatistics(String startTime, String endTime) {
        FireEventStatisticsVO vo = new FireEventStatisticsVO();
        
        // 查询时间范围内的火灾事件
        LambdaQueryWrapper<FireEvent> wrapper = new LambdaQueryWrapper<>();
        if (startTime != null && endTime != null) {
            wrapper.between(FireEvent::getCreateTime, startTime, endTime);
        }
        List<FireEvent> fireEvents = fireEventService.list(wrapper);
        
        // 统计总数
        vo.setTotalCount(fireEvents.size());
        
        // 统计各状态数量
        Map<Integer, Long> statusCount = fireEvents.stream()
                .collect(Collectors.groupingBy(FireEvent::getStatus, Collectors.counting()));
        vo.setHandledCount(statusCount.getOrDefault(2, 0L).intValue());
        vo.setProcessingCount(statusCount.getOrDefault(1, 0L).intValue());
        vo.setPendingCount(statusCount.getOrDefault(0, 0L).intValue());
        
        // 事件趋势数据
        vo.setTrendData(getFireEventTrend(fireEvents));
        
        // 事件类型分布
        vo.setTypeDistribution(getFireEventTypeDistribution(fireEvents));
        
        // 事件位置热力图数据
        vo.setLocationHeatmap(getFireEventLocationHeatmap(fireEvents));
        
        return vo;
    }

    @Override
    public DeviceStatusStatisticsVO getDeviceStatusStatistics() {
        DeviceStatusStatisticsVO vo = new DeviceStatusStatisticsVO();
        
        // 查询所有摄像头设备
        List<Camera> cameras = cameraService.list();
        
        // 统计总数
        vo.setTotalCount(cameras.size());
        
        // 统计各状态数量
        Map<Integer, Long> statusCount = cameras.stream()
                .collect(Collectors.groupingBy(Camera::getStatus, Collectors.counting()));
        vo.setOnlineCount(statusCount.getOrDefault(1, 0L).intValue());
        vo.setOfflineCount(statusCount.getOrDefault(0, 0L).intValue());
        vo.setFaultCount(statusCount.getOrDefault(2, 0L).intValue());
        
        // 设备状态分布
        vo.setStatusDistribution(getDeviceStatusDistribution(cameras));
        
        // 设备在线率趋势
        vo.setOnlineRateTrend(getDeviceOnlineRateTrend());
        
        // 设备故障类型分布
        vo.setFaultTypeDistribution(getDeviceFaultTypeDistribution());
        
        return vo;
    }

    @Override
    public InspectionStatisticsVO getInspectionStatistics(String startTime, String endTime) {
        InspectionStatisticsVO vo = new InspectionStatisticsVO();
        
        // 查询时间范围内的检查记录
        LambdaQueryWrapper<InspectionRecord> wrapper = new LambdaQueryWrapper<>();
        if (startTime != null && endTime != null) {
            wrapper.between(InspectionRecord::getCreateTime, startTime, endTime);
        }
        List<InspectionRecord> records = inspectionRecordService.list(wrapper);
        
        // 统计总数
        vo.setTotalCount(records.size());
        
        // 统计各状态数量
        Map<Integer, Long> statusCount = records.stream()
                .collect(Collectors.groupingBy(InspectionRecord::getStatus, Collectors.counting()));
        vo.setNormalCount(statusCount.getOrDefault(1, 0L).intValue());
        vo.setAbnormalCount(statusCount.getOrDefault(0, 0L).intValue());
        
        // 待整改数量
        vo.setPendingCount(records.stream()
                .filter(r -> r.getStatus() == 0)
                .collect(Collectors.toList()).size());
        
        // 检查趋势数据
        vo.setTrendData(getInspectionTrend(records));
        
        // 异常类型分布
        vo.setAbnormalTypeDistribution(getInspectionAbnormalTypeDistribution(records));
        
        // 检查区域分布
        vo.setAreaDistribution(getInspectionAreaDistribution(records));
        
        // 整改完成率
        vo.setCompletionRate(calculateCompletionRate(records));
        
        return vo;
    }

    @Override
    public FeedbackStatisticsVO getFeedbackStatistics(String startTime, String endTime) {
        FeedbackStatisticsVO vo = new FeedbackStatisticsVO();
        
        // 查询时间范围内的用户反馈
        LambdaQueryWrapper<Feedback> wrapper = new LambdaQueryWrapper<>();
        if (startTime != null && endTime != null) {
            wrapper.between(Feedback::getCreateTime, startTime, endTime);
        }
        List<Feedback> feedbacks = feedbackService.list(wrapper);
        
        // 统计总数
        vo.setTotalCount(feedbacks.size());
        
        // 统计各状态数量
        Map<Integer, Long> statusCount = feedbacks.stream()
                .collect(Collectors.groupingBy(Feedback::getStatus, Collectors.counting()));
        vo.setHandledCount(statusCount.getOrDefault(2, 0L).intValue());
        vo.setProcessingCount(statusCount.getOrDefault(1, 0L).intValue());
        vo.setPendingCount(statusCount.getOrDefault(0, 0L).intValue());
        
        // 反馈趋势数据
        vo.setTrendData(getFeedbackTrend(feedbacks));
        
        // 反馈类型分布
        vo.setTypeDistribution(getFeedbackTypeDistribution(feedbacks));
        
        // 反馈来源分布
        vo.setSourceDistribution(getFeedbackSourceDistribution(feedbacks));
        
        // 平均处理时长
        vo.setAvgHandleTime(calculateAvgHandleTime(feedbacks));
        
        // 满意度评分
        vo.setSatisfactionScore(calculateSatisfactionScore(feedbacks));
        
        return vo;
    }

    @Override
    public SystemOverviewVO getSystemOverview() {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        
        SystemOverviewVO vo = new SystemOverviewVO();
        vo.setTodayFireEvents(countTodayFireEvents(today));
        vo.setTodayInspections(countTodayInspections(today));
        vo.setTodayFeedbacks(countTodayFeedbacks(today));
        vo.setOnlineDevices(countOnlineDevices());
        vo.setRunningDays(calculateRunningDays());
        vo.setWeeklyTrend(getWeeklyTrend());
        vo.setDeviceStatus(getDeviceStatusDistribution());
        vo.setRecentAlarms(getRecentAlarms());
        vo.setPendingItems(getPendingItems());
        vo.setInspectionStatus(getInspectionStatusDistribution());
        return vo;
    }

    // 辅助方法
    private List<Map<String, Object>> getFireEventTrend(List<FireEvent> events) {
        return events.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCreateTime().format(DateTimeFormatter.ISO_DATE),
                        Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getFireEventTypeDistribution(List<FireEvent> events) {
        return events.stream()
                .collect(Collectors.groupingBy(FireEvent::getLevel, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("level", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getFireEventLocationHeatmap(List<FireEvent> events) {
        return events.stream()
                .collect(Collectors.groupingBy(FireEvent::getLocation, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("location", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getDeviceStatusDistribution(List<Camera> cameras) {
        return cameras.stream()
                .collect(Collectors.groupingBy(Camera::getStatus, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getDeviceOnlineRateTrend() {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        // 获取最近7天的在线率数据
        for (int i = 6; i >= 0; i--) {
            LocalDateTime date = now.minusDays(i).withHour(0).withMinute(0).withSecond(0);
            String dateStr = date.format(DateTimeFormatter.ISO_DATE);
            
            // 统计当天的设备总数和在线数
            List<Camera> cameras = cameraService.list(new LambdaQueryWrapper<Camera>()
                    .le(Camera::getCreateTime, date));
            long total = cameras.size();
            long online = cameras.stream()
                    .filter(c -> c.getStatus() == 1)
                    .count();
            
            // 计算在线率
            double rate = total == 0 ? 0 : (double) online / total * 100;
            
            Map<String, Object> data = new HashMap<>();
            data.put("date", dateStr);
            data.put("rate", String.format("%.2f", rate));
            result.add(data);
        }
        
        return result;
    }

    private List<Map<String, Object>> getDeviceFaultTypeDistribution() {
        List<Map<String, Object>> result = new ArrayList<>();
        
        // 查询所有故障状态的设备
        List<Camera> faultDevices = cameraService.list(new LambdaQueryWrapper<Camera>()
                .eq(Camera::getStatus, 2));
        
        // 统计各故障类型数量，处理 null 值的情况
        Map<String, Long> faultTypeCounts = faultDevices.stream()
                .collect(Collectors.groupingBy(
                    camera -> camera.getFaultType() == null ? "other" : camera.getFaultType(),
                    Collectors.counting()
                ));
        
        // 转换为前端需要的格式
        faultTypeCounts.forEach((type, count) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("type", getFaultTypeText(type));
            data.put("count", count);
            result.add(data);
        });
        
        return result;
    }

    private String getFaultTypeText(String type) {
        if (type == null) {
            return "其他故障";
        }
        switch (type) {
            case "network":
                return "网络故障";
            case "hardware":
                return "硬件故障";
            case "software":
                return "软件故障";
            case "power":
                return "电源故障";
            case "other":
            default:
                return "其他故障";
        }
    }

    private List<Map<String, Object>> getInspectionTrend(List<InspectionRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCreateTime().format(DateTimeFormatter.ISO_DATE),
                        Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getInspectionAbnormalTypeDistribution(List<InspectionRecord> records) {
        return records.stream()
                .filter(r -> r.getStatus() == 0)
                .collect(Collectors.groupingBy(InspectionRecord::getResult, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getInspectionAreaDistribution(List<InspectionRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(InspectionRecord::getLocation, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("area", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private Double calculateCompletionRate(List<InspectionRecord> records) {
        if (records.isEmpty()) {
            return 0.0;
        }
        
        // 获取需要整改的记录数（异常状态的记录）
        long needCorrection = records.stream()
                .filter(r -> r.getStatus() == 0)
                .count();
        
        // 获取已完成整改的记录数
        long completed = records.stream()
                .filter(r -> r.getStatus() == 1)
                .count();
        
        // 如果没有需要整改的记录，返回100%
        if (needCorrection == 0) {
            return 100.0;
        }
        
        // 计算整改完成率
        return (double) completed / (needCorrection + completed) * 100;
    }

    private List<Map<String, Object>> getFeedbackTrend(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .collect(Collectors.groupingBy(
                        f -> f.getCreateTime().format(DateTimeFormatter.ISO_DATE),
                        Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getFeedbackTypeDistribution(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .collect(Collectors.groupingBy(Feedback::getType, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getFeedbackSourceDistribution(List<Feedback> feedbacks) {
        List<Map<String, Object>> result = new ArrayList<>();
        
        // 定义反馈来源
        Map<Integer, String> sources = new HashMap<>();
        sources.put(1, "Web端");
        sources.put(2, "移动端");
        sources.put(3, "现场报修");
        sources.put(4, "电话反馈");
        
        // 统计各来源的反馈数量
        feedbacks.forEach(f -> {
            Map<String, Object> data = new HashMap<>();
            String source = sources.getOrDefault(f.getType(), "其他");
            data.put("source", source);
            data.put("count", feedbacks.stream()
                    .filter(fb -> fb.getType().equals(f.getType()))
                    .count());
            result.add(data);
        });
        
        return result;
    }

    private Double calculateAvgHandleTime(List<Feedback> feedbacks) {
        List<Feedback> handledFeedbacks = feedbacks.stream()
                .filter(f -> f.getStatus() == 2 && f.getUpdateTime() != null)
                .collect(Collectors.toList());
        
        if (handledFeedbacks.isEmpty()) {
            return 0.0;
        }
        
        double totalHours = handledFeedbacks.stream()
                .mapToDouble(f -> ChronoUnit.HOURS.between(f.getCreateTime(), f.getUpdateTime()))
                .sum();
        
        return totalHours / handledFeedbacks.size();
    }

    private Double calculateSatisfactionScore(List<Feedback> feedbacks) {
        // 只计算已处理的反馈的满意度
        List<Feedback> handledFeedbacks = feedbacks.stream()
                .filter(f -> f.getStatus() == 2 && f.getSatisfactionScore() != null)
                .collect(Collectors.toList());
        
        if (handledFeedbacks.isEmpty()) {
            return 0.0;
        }
        
        // 计算平均满意度分数
        double totalScore = handledFeedbacks.stream()
                .mapToDouble(Feedback::getSatisfactionScore)
                .sum();
        
        return totalScore / handledFeedbacks.size();
    }

    private Integer countTodayFireEvents(LocalDateTime today) {
        return (int) fireEventService.list(new LambdaQueryWrapper<FireEvent>()
                .ge(FireEvent::getCreateTime, today))
                .size();
    }

    private Integer countTodayInspections(LocalDateTime today) {
        return (int) inspectionRecordService.list(new LambdaQueryWrapper<InspectionRecord>()
                .ge(InspectionRecord::getCreateTime, today))
                .size();
    }

    private Integer countTodayFeedbacks(LocalDateTime today) {
        return (int) feedbackService.list(new LambdaQueryWrapper<Feedback>()
                .ge(Feedback::getCreateTime, today))
                .size();
    }

    private Integer countOnlineDevices() {
        return (int) cameraService.list(new LambdaQueryWrapper<Camera>()
                .eq(Camera::getStatus, 1))
                .size();
    }

    private Integer calculateRunningDays() {
        // 获取系统首次运行时间（第一条系统日志的时间）
        SystemLog firstLog = systemLogMapper.selectOne(new LambdaQueryWrapper<SystemLog>()
                .orderByAsc(SystemLog::getCreateTime)
                .last("LIMIT 1"));
        
        if (firstLog == null) {
            return 0;
        }
        
        // 计算从首次运行到现在的天数
        return (int) ChronoUnit.DAYS.between(firstLog.getCreateTime(), LocalDateTime.now());
    }

    private List<Map<String, Object>> getWeeklyTrend() {
        LocalDateTime startOfWeek = LocalDateTime.now().minusDays(6).withHour(0).withMinute(0).withSecond(0);
        
        // 获取火灾事件数据
        List<FireEvent> fireEvents = fireEventService.list(new LambdaQueryWrapper<FireEvent>()
                .ge(FireEvent::getCreateTime, startOfWeek));
        
        // 获取消防检查数据
        List<InspectionRecord> inspections = inspectionRecordService.list(new LambdaQueryWrapper<InspectionRecord>()
                .ge(InspectionRecord::getCreateTime, startOfWeek));
        
        // 获取用户反馈数据
        List<Feedback> feedbacks = feedbackService.list(new LambdaQueryWrapper<Feedback>()
                .ge(Feedback::getCreateTime, startOfWeek));
        
        // 创建日期列表（最近7天）
        List<String> dates = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            dates.add(LocalDateTime.now().minusDays(i).format(DateTimeFormatter.ISO_DATE));
        }
        
        // 统计每天的事件数量
        Map<String, Long> fireEventCounts = fireEvents.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCreateTime().format(DateTimeFormatter.ISO_DATE),
                        Collectors.counting()));
        
        Map<String, Long> inspectionCounts = inspections.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCreateTime().format(DateTimeFormatter.ISO_DATE),
                        Collectors.counting()));
        
        Map<String, Long> feedbackCounts = feedbacks.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCreateTime().format(DateTimeFormatter.ISO_DATE),
                        Collectors.counting()));
        
        // 组装结果
        return dates.stream().map(date -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", date);
            map.put("fireEvents", fireEventCounts.getOrDefault(date, 0L));
            map.put("inspections", inspectionCounts.getOrDefault(date, 0L));
            map.put("feedbacks", feedbackCounts.getOrDefault(date, 0L));
            return map;
        }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> getDeviceStatusDistribution() {
        List<Camera> cameras = cameraService.list();
        return cameras.stream()
                .collect(Collectors.groupingBy(Camera::getStatus, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getRecentAlarms() {
        // 获取所有火灾事件
        return fireEventService.list(new LambdaQueryWrapper<FireEvent>()
                .orderByDesc(FireEvent::getEventTime))
                .stream()
                .map(event -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", event.getId());
                    map.put("type", event.getType());
                    map.put("location", event.getLocation());
                    map.put("time", event.getEventTime());
                    map.put("status", event.getStatus());
                    map.put("level", event.getLevel());  // 确保包含level字段
                    return map;
                })
                .collect(Collectors.toList());
    }

    private Map<String, Integer> getPendingItems() {
        Map<String, Integer> result = new HashMap<>();
        
        // 待处理火灾事件
        result.put("fireEvents", (int) fireEventService.count(new LambdaQueryWrapper<FireEvent>()
                .eq(FireEvent::getStatus, 0)));
        
        // 待处理检查记录
        result.put("inspections", (int) inspectionRecordService.count(new LambdaQueryWrapper<InspectionRecord>()
                .eq(InspectionRecord::getStatus, 0)));
        
        // 待处理用户反馈
        result.put("feedbacks", (int) feedbackService.count(new LambdaQueryWrapper<Feedback>()
                .eq(Feedback::getStatus, 0)));
        
        return result;
    }

    private Map<String, Integer> getInspectionStatusDistribution() {
        Map<String, Integer> result = new HashMap<>();
        
        // 统计各状态的检查记录数量
        Map<Integer, Long> statusCount = inspectionRecordService.list()
            .stream()
            .collect(Collectors.groupingBy(InspectionRecord::getStatus, Collectors.counting()));
        
        // 已完成(状态1)
        result.put("completed", statusCount.getOrDefault(1, 0L).intValue());
        // 异常(状态0)
        result.put("abnormal", statusCount.getOrDefault(0, 0L).intValue());
        
        return result;
    }
} 