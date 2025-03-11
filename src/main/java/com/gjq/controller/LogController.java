package com.gjq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gjq.common.Result;
import com.gjq.dto.LogQueryDTO;
import com.gjq.entity.Log;
import com.gjq.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统日志Controller
 */
@Tag(name = "系统日志管理", description = "系统日志相关接口")
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    /**
     * 分页查询系统日志列表
     */
    @Operation(summary = "分页查询系统日志列表", description = "根据查询条件分页获取系统日志列表")
    @Parameters({
        @Parameter(name = "current", description = "当前页码", required = true),
        @Parameter(name = "size", description = "每页数量", required = true),
        @Parameter(name = "type", description = "日志类型：1-登录日志，2-操作日志，3-异常日志"),
        @Parameter(name = "status", description = "操作状态：0-失败，1-成功"),
        @Parameter(name = "startTime", description = "开始时间"),
        @Parameter(name = "endTime", description = "结束时间")
    })
    @GetMapping("/page")
    public Result<IPage<Log>> getLogPage(@Validated LogQueryDTO queryDTO) {
        return Result.success(logService.getLogPage(queryDTO));
    }

    /**
     * 获取系统日志详情
     */
    @Operation(summary = "获取系统日志详情", description = "根据ID获取系统日志详细信息")
    @Parameter(name = "id", description = "日志ID", required = true)
    @GetMapping("/{id}")
    public Result<Log> getLogById(@PathVariable Long id) {
        return Result.success(logService.getLogById(id));
    }

    /**
     * 删除系统日志
     */
    @Operation(summary = "删除系统日志", description = "根据ID删除指定的系统日志")
    @Parameter(name = "id", description = "日志ID", required = true)
    @DeleteMapping("/{id}")
    public Result<Void> deleteLog(@PathVariable Long id) {
        logService.deleteLog(id);
        return Result.success();
    }

    /**
     * 清空系统日志
     */
    @Operation(summary = "清空系统日志", description = "清空所有系统日志")
    @DeleteMapping("/clear")
    public Result<Void> clearLog() {
        logService.clearLog();
        return Result.success();
    }
}