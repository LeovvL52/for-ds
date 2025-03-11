package com.gjq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gjq.dto.camera.CameraCreateDTO;
import com.gjq.dto.camera.CameraQueryDTO;
import com.gjq.dto.camera.CameraUpdateDTO;
import com.gjq.entity.Camera;
import org.springframework.stereotype.Service;

/**
 * 摄像头设备服务接口
 */
@Service
public interface CameraService extends IService<Camera> {
    /**
     * 创建摄像头设备
     *
     * @param dto 创建参数
     * @return 设备ID
     */
    Long createCamera(CameraCreateDTO dto);

    /**
     * 更新摄像头设备
     *
     * @param dto 更新参数
     */
    void updateCamera(CameraUpdateDTO dto);

    /**
     * 删除摄像头设备
     *
     * @param id 设备ID
     */
    void deleteCamera(Long id);

    /**
     * 分页查询摄像头设备列表
     *
     * @param dto 查询参数
     * @return 设备列表
     */
    IPage<Camera> getCameraPage(CameraQueryDTO dto);

    /**
     * 获取摄像头设备详情
     *
     * @param id 设备ID
     * @return 设备详情
     */
    Camera getCameraById(Long id);

    /**
     * 更新摄像头设备状态
     *
     * @param id     设备ID
     * @param status 状态(0:离线 1:在线 2:故障)
     */
    void updateCameraStatus(Long id, Integer status);
} 