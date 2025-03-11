package com.gjq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.common.exception.BusinessException;
import com.gjq.dto.camera.CameraCreateDTO;
import com.gjq.dto.camera.CameraQueryDTO;
import com.gjq.dto.camera.CameraUpdateDTO;
import com.gjq.entity.Camera;
import com.gjq.mapper.CameraMapper;
import com.gjq.service.CameraService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 摄像头设备服务实现类
 */
@Service
public class CameraServiceImpl extends ServiceImpl<CameraMapper, Camera> implements CameraService {

    @Override
    public Long createCamera(CameraCreateDTO dto) {
        // 校验设备名称是否存在
        if (lambdaQuery().eq(Camera::getName, dto.getName()).count() > 0) {
            throw new BusinessException("设备名称已存在");
        }

        // 创建设备
        Camera camera = BeanUtil.copyProperties(dto, Camera.class);
        // 设置状态为在线
        camera.setStatus(1);

        // 保存设备
        save(camera);
        return camera.getId();
    }

    @Override
    public void updateCamera(CameraUpdateDTO dto) {
        // 查询设备
        Camera camera = getById(dto.getId());
        if (camera == null) {
            throw new BusinessException("设备不存在");
        }

        // 校验设备名称是否存在
        if (!camera.getName().equals(dto.getName()) && 
            lambdaQuery().eq(Camera::getName, dto.getName()).count() > 0) {
            throw new BusinessException("设备名称已存在");
        }

        // 更新设备信息
        BeanUtil.copyProperties(dto, camera);
        
        // 如果状态不是故障，清空故障类型
        if (camera.getStatus() != 2) {
            camera.setFaultType(null);
        }
        
        updateById(camera);
    }

    @Override
    public void deleteCamera(Long id) {
        // 查询设备
        Camera camera = getById(id);
        if (camera == null) {
            throw new BusinessException("设备不存在");
        }

        // 删除设备
        removeById(id);
    }

    @Override
    public IPage<Camera> getCameraPage(CameraQueryDTO dto) {
        // 构建查询条件
        LambdaQueryWrapper<Camera> wrapper = new LambdaQueryWrapper<Camera>()
                .like(StrUtil.isNotBlank(dto.getName()), Camera::getName, dto.getName())
                .like(StrUtil.isNotBlank(dto.getLocation()), Camera::getLocation, dto.getLocation())
                .like(StrUtil.isNotBlank(dto.getIpAddress()), Camera::getIpAddress, dto.getIpAddress())
                .eq(dto.getStatus() != null, Camera::getStatus, dto.getStatus());

        // 执行分页查询
        return page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    @Override
    public Camera getCameraById(Long id) {
        // 查询设备
        Camera camera = getById(id);
        if (camera == null) {
            throw new BusinessException("设备不存在");
        }
        return camera;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCameraStatus(Long id, Integer status) {
        // 查询摄像头
        Camera camera = getById(id);
        if (camera == null) {
            throw new BusinessException("摄像头不存在");
        }
        // 更新状态
        camera.setStatus(status);
        updateById(camera);
    }
} 