package com.gjq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gjq.common.Result;
import com.gjq.dto.inspection.InspectionRecordCreateDTO;
import com.gjq.dto.inspection.InspectionRecordQueryDTO;
import com.gjq.dto.inspection.InspectionRecordUpdateDTO;
import com.gjq.dto.inspection.InspectionRecordVO;
import com.gjq.service.InspectionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 消防检查记录控制器
 */
@Tag(name = "消防检查", description = "消防检查记录管理相关接口")
@RestController
@RequestMapping("/inspection-record")
@RequiredArgsConstructor
public class InspectionRecordController {

    private final InspectionRecordService inspectionRecordService;

    /**
     * 创建消防检查记录
     */
    @Operation(summary = "创建检查记录", description = "创建新的消防检查记录")
    @PostMapping
    public Result<Long> createInspectionRecord(@RequestBody @Valid InspectionRecordCreateDTO dto) {
        return Result.success(inspectionRecordService.createInspectionRecord(dto));
    }

    /**
     * 更新消防检查记录
     */
    @Operation(summary = "更新检查记录", description = "更新已有的消防检查记录")
    @PutMapping
    public Result<Void> updateInspectionRecord(@RequestBody @Valid InspectionRecordUpdateDTO dto) {
        inspectionRecordService.updateInspectionRecord(dto);
        return Result.success();
    }

    /**
     * 删除消防检查记录
     */
    @Operation(summary = "删除检查记录", description = "根据ID删除消防检查记录")
    @DeleteMapping("/{id}")
    public Result<Void> deleteInspectionRecord(@Parameter(description = "记录ID") @PathVariable Long id) {
        inspectionRecordService.deleteInspectionRecord(id);
        return Result.success();
    }

    /**
     * 分页查询消防检查记录列表
     */
    @Operation(summary = "分页查询检查记录", description = "分页查询消防检查记录列表")
    @GetMapping("/page")
    public Result<IPage<InspectionRecordVO>> getInspectionRecordPage(@Valid InspectionRecordQueryDTO dto) {
        return Result.success(inspectionRecordService.getInspectionRecordPage(dto));
    }

    /**
     * 获取消防检查记录详情
     */
    @Operation(summary = "获取检查记录详情", description = "根据ID获取消防检查记录详情")
    @GetMapping("/{id}")
    public Result<InspectionRecordVO> getInspectionRecordById(@Parameter(description = "记录ID") @PathVariable Long id) {
        return Result.success(inspectionRecordService.getInspectionRecordById(id));
    }
} 