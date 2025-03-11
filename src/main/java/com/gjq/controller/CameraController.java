package com.gjq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gjq.common.Result;
import com.gjq.dto.camera.CameraCreateDTO;
import com.gjq.dto.camera.CameraQueryDTO;
import com.gjq.dto.camera.CameraUpdateDTO;
import com.gjq.entity.Camera;
import com.gjq.service.CameraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 摄像头设备控制器
 */
@Tag(name = "摄像头管理", description = "摄像头设备管理相关接口")
@RestController
@RequestMapping("/camera")
@RequiredArgsConstructor
public class CameraController {

    private final CameraService cameraService;

    /**
     * 创建摄像头设备
     */
    @Operation(summary = "创建摄像头", description = "创建新的摄像头设备")
    @PostMapping
    public Result<Long> createCamera(@RequestBody @Valid CameraCreateDTO dto) {
        return Result.success(cameraService.createCamera(dto));
    }

    /**
     * 更新摄像头设备
     */
    @Operation(summary = "更新摄像头", description = "更新摄像头设备信息")
    @PutMapping
    public Result<Void> updateCamera(@RequestBody @Valid CameraUpdateDTO dto) {
        cameraService.updateCamera(dto);
        return Result.success();
    }

    /**
     * 删除摄像头设备
     */
    @Operation(summary = "删除摄像头", description = "根据ID删除摄像头设备")
    @DeleteMapping("/{id}")
    public Result<Void> deleteCamera(@PathVariable Long id) {
        cameraService.deleteCamera(id);
        return Result.success();
    }

    /**
     * 分页查询摄像头设备列表
     */
    @Operation(summary = "分页查询摄像头列表", description = "分页查询摄像头设备列表")
    @GetMapping("/page")
    public Result<IPage<Camera>> getCameraPage(CameraQueryDTO dto) {
        return Result.success(cameraService.getCameraPage(dto));
    }

    /**
     * 获取摄像头设备详情
     */
    @Operation(summary = "获取摄像头详情", description = "根据ID获取摄像头设备详情")
    @GetMapping("/{id}")
    public Result<Camera> getCameraById(@PathVariable Long id) {
        return Result.success(cameraService.getCameraById(id));
    }

    /**
     * 更新摄像头设备状态
     */
    @Operation(summary = "更新摄像头状态", description = "更新摄像头设备的在线状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        cameraService.updateCameraStatus(id, body.get("status"));
        return Result.success();
    }
} 