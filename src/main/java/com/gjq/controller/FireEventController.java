package com.gjq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gjq.common.Result;
import com.gjq.common.exception.BusinessException;
import com.gjq.dto.fire.*;
import com.gjq.service.FireEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 火灾事件控制器
 */
@Tag(name = "火灾事件", description = "火灾事件管理相关接口")
@RestController
@RequestMapping("/fire-event")
@RequiredArgsConstructor
public class FireEventController {

    private final FireEventService fireEventService;

    /**
     * 创建火灾事件
     */
    @Operation(summary = "创建火灾事件", description = "创建新的火灾事件记录")
    @PostMapping
    public Result<Long> createFireEvent(@RequestBody @Valid FireEventCreateDTO dto) {
        return Result.success(fireEventService.createFireEvent(dto));
    }

    /**
     * 处理火灾事件
     */
    @Operation(summary = "处理火灾事件", description = "处理火灾事件并记录处理结果")
    @PostMapping("/{id}/handle")
    public Result<Void> handleFireEvent(@PathVariable Long id, @RequestBody @Valid FireEventHandleDTO dto) {
        if (!id.equals(dto.getId())) {
            throw new BusinessException("路径ID与请求体ID不一致");
        }
        fireEventService.handleFireEvent(dto);
        return Result.success();
    }

    /**
     * 更新火灾事件
     */
    @Operation(summary = "更新火灾事件", description = "更新火灾事件信息")
    @PutMapping("/{id}")
    public Result<Void> updateFireEvent(@PathVariable Long id, @RequestBody @Valid FireEventUpdateDTO dto) {
        if (!id.equals(dto.getId())) {
            throw new BusinessException("路径ID与请求体ID不一致");
        }
        fireEventService.updateFireEvent(dto);
        return Result.success();
    }

    /**
     * 分页查询火灾事件列表
     */
    @Operation(summary = "分页查询火灾事件", description = "分页查询火灾事件列表")
    @GetMapping("/page")
    public Result<IPage<FireEventVO>> getFireEventPage(FireEventQueryDTO dto) {
        return Result.success(fireEventService.getFireEventPage(dto));
    }

    /**
     * 获取火灾事件详情
     */
    @Operation(summary = "获取火灾事件详情", description = "根据ID获取火灾事件详情")
    @GetMapping("/{id}")
    public Result<FireEventVO> getFireEventById(@PathVariable Long id) {
        return Result.success(fireEventService.getFireEventById(id));
    }

    /**
     * 更新火灾事件状态
     */
    @Operation(summary = "更新火灾事件状态", description = "更新火灾事件的处理状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateFireEventStatus(@PathVariable Long id, @RequestParam Integer status) {
        fireEventService.updateFireEventStatus(id, status);
        return Result.success();
    }

    /**
     * 删除火灾事件
     */
    @Operation(summary = "删除火灾事件", description = "根据ID删除火灾事件")
    @DeleteMapping("/{id}")
    public Result<Void> deleteFireEvent(@PathVariable Long id) {
        fireEventService.deleteFireEvent(id);
        return Result.success();
    }

    /**
     * 批量删除火灾事件
     */
    @Operation(summary = "批量删除火灾事件", description = "批量删除火灾事件")
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteFireEvents(@RequestBody List<Long> ids) {
        fireEventService.batchDeleteFireEvents(ids);
        return Result.success();
    }
} 