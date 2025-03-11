package com.gjq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.common.exception.BusinessException;
import com.gjq.dto.fire.FireEventCreateDTO;
import com.gjq.dto.fire.FireEventHandleDTO;
import com.gjq.dto.fire.FireEventQueryDTO;
import com.gjq.dto.fire.FireEventUpdateDTO;
import com.gjq.dto.fire.FireEventVO;
import com.gjq.entity.Camera;
import com.gjq.entity.FireEvent;
import com.gjq.entity.User;
import com.gjq.mapper.FireEventMapper;
import com.gjq.service.CameraService;
import com.gjq.service.FireEventService;
import com.gjq.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 火灾事件服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FireEventServiceImpl extends ServiceImpl<FireEventMapper, FireEvent> implements FireEventService {

    private final CameraService cameraService;
    private final UserService userService;

    @Value("${file.server.url}")
    private String fileServerUrl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFireEvent(FireEventCreateDTO dto) {
        // 校验摄像头是否存在
        Camera camera = cameraService.getById(dto.getCameraId());
        if (camera == null) {
            throw new BusinessException("摄像头不存在");
        }

        // 创建事件
        FireEvent fireEvent = BeanUtil.copyProperties(dto, FireEvent.class);
        // 设置状态为未处理
        fireEvent.setStatus(0);

        // 保存事件
        save(fireEvent);
        
        // 发送火灾警报短信
        sendFireAlertSMS(camera, fireEvent);
        
        return fireEvent.getId();
    }

    /**
     * 模拟发送火灾警报短信
     */
    private void sendFireAlertSMS(Camera camera, FireEvent fireEvent) {
        // 获取所有管理员用户
        List<User> admins = userService.list(new LambdaQueryWrapper<User>()
                .eq(User::getRole, 1)  // 1表示管理员角色
                .eq(User::getStatus, 1));  // 1表示正常状态
        
        if (admins.isEmpty()) {
            log.warn("没有找到可通知的管理员用户");
            return;
        }
        
        // 格式化时间
        String eventTime = fireEvent.getEventTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // 构建短信内容
        String smsContent = String.format(
            "【火灾预警】检测到火情！\n位置：%s\n时间：%s\n火情等级：%d级\n请及时处理！",
            camera.getLocation(),
            eventTime,
            fireEvent.getLevel()
        );
        
        // 模拟发送短信给每个管理员
        for (User admin : admins) {
            try {
                // 这里是模拟发送短信，实际项目中需要替换为真实的短信发送接口
                log.info("模拟发送短信到 {} ({}): {}", 
                    admin.getRealName(), 
                    admin.getPhone(), 
                    smsContent);
                
                // 在实际项目中，这里需要调用短信服务商的API
                // 例如：smsService.send(admin.getPhone(), smsContent);
                
            } catch (Exception e) {
                log.error("发送短信给管理员{}失败: {}", admin.getRealName(), e.getMessage());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleFireEvent(FireEventHandleDTO dto) {
        // 查询事件
        FireEvent fireEvent = getById(dto.getId());
        if (fireEvent == null) {
            throw new BusinessException("事件不存在");
        }

        // 校验处理人是否存在
        User handler = userService.getById(dto.getHandlerId());
        if (handler == null) {
            throw new BusinessException("处理人不存在");
        }

        // 更新事件信息
        fireEvent.setHandlerId(dto.getHandlerId());
        fireEvent.setHandleTime(LocalDateTime.now());
        fireEvent.setHandleResult(dto.getHandleResult());
        fireEvent.setStatus(2);
        updateById(fireEvent);
    }

    @Override
    public IPage<FireEventVO> getFireEventPage(FireEventQueryDTO dto) {
        // 构建查询条件
        LambdaQueryWrapper<FireEvent> wrapper = new LambdaQueryWrapper<FireEvent>()
                .eq(dto.getCameraId() != null, FireEvent::getCameraId, dto.getCameraId())
                .like(StrUtil.isNotBlank(dto.getLocation()), FireEvent::getLocation, dto.getLocation())
                .eq(dto.getLevel() != null, FireEvent::getLevel, dto.getLevel())
                .eq(dto.getStatus() != null, FireEvent::getStatus, dto.getStatus())
                .eq(dto.getHandlerId() != null, FireEvent::getHandlerId, dto.getHandlerId())
                .ge(dto.getStartTime() != null, FireEvent::getEventTime, dto.getStartTime())
                .le(dto.getEndTime() != null, FireEvent::getEventTime, dto.getEndTime())
                .orderByDesc(FireEvent::getEventTime);

        // 执行分页查询
        IPage<FireEvent> page = page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);

        // 转换返回结果
        return page.convert(this::convertToVO);
    }

    @Override
    public FireEventVO getFireEventById(Long id) {
        // 查询事件
        FireEvent fireEvent = getById(id);
        if (fireEvent == null) {
            throw new BusinessException("事件不存在");
        }

        // 转换返回结果
        return convertToVO(fireEvent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFireEventStatus(Long id, Integer status) {
        // 查询事件
        FireEvent fireEvent = getById(id);
        if (fireEvent == null) {
            throw new BusinessException("事件不存在");
        }

        // 更新状态
        fireEvent.setStatus(status);
        updateById(fireEvent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFireEvent(FireEventUpdateDTO dto) {
        // 查询事件
        FireEvent fireEvent = getById(dto.getId());
        if (fireEvent == null) {
            throw new BusinessException("事件不存在");
        }

        // 如果更新了摄像头，校验摄像头是否存在
        if (dto.getCameraId() != null && !dto.getCameraId().equals(fireEvent.getCameraId())) {
            Camera camera = cameraService.getById(dto.getCameraId());
            if (camera == null) {
                throw new BusinessException("摄像头不存在");
            }
        }

        // 更新事件信息
        BeanUtil.copyProperties(dto, fireEvent, "id", "status", "eventTime", "handlerId", "handleTime", "handleResult");
        updateById(fireEvent);
    }

    /**
     * 转换为VO对象
     */
    private FireEventVO convertToVO(FireEvent fireEvent) {
        FireEventVO vo = BeanUtil.copyProperties(fireEvent, FireEventVO.class);

        // 设置摄像头名称
        Camera camera = cameraService.getById(fireEvent.getCameraId());
        if (camera != null) {
            vo.setCameraName(camera.getName());
        }

        // 设置处理人姓名
        if (fireEvent.getHandlerId() != null) {
            User handler = userService.getById(fireEvent.getHandlerId());
            if (handler != null) {
                vo.setHandlerName(handler.getRealName());
            }
        }

        return vo;
    }

    /**
     * 删除火灾事件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFireEvent(Long id) {
        // 删除前检查记录是否存在
        FireEvent event = getById(id);
        if (event == null) {
            throw new RuntimeException("火灾事件不存在");
        }
        
        // 如果有图片，删除图片文件
        if (event.getImageUrl() != null && !event.getImageUrl().isEmpty()) {
            try {
                // 从图片URL中提取bucket和objectKey
                // URL格式：http://localhost:5001/api/file/fire-images/xxx.jpg
                String imageUrl = event.getImageUrl();
                if (imageUrl.startsWith(fileServerUrl)) {
                    String path = imageUrl.substring(fileServerUrl.length());
                    String[] parts = path.split("/");
                    if (parts.length >= 3) {
                        String bucket = parts[1];
                        String objectKey = parts[2];
                        // 调用文件服务删除图片
                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.delete(fileServerUrl + "/" + bucket + "/" + objectKey);
                    }
                }
            } catch (Exception e) {
                log.error("删除火灾事件图片失败: {}", e.getMessage());
                // 继续执行，不影响事件删除
            }
        }
        
        // 执行删除
        removeById(id);
    }

    /**
     * 批量删除火灾事件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteFireEvents(List<Long> ids) {
        // 删除前检查记录是否存在
        List<FireEvent> events = listByIds(ids);
        if (events.size() != ids.size()) {
            throw new RuntimeException("部分火灾事件不存在");
        }
        
        // 删除图片文件
        RestTemplate restTemplate = new RestTemplate();
        for (FireEvent event : events) {
            if (event.getImageUrl() != null && !event.getImageUrl().isEmpty()) {
                try {
                    // 从图片URL中提取bucket和objectKey
                    String imageUrl = event.getImageUrl();
                    if (imageUrl.startsWith(fileServerUrl)) {
                        String path = imageUrl.substring(fileServerUrl.length());
                        String[] parts = path.split("/");
                        if (parts.length >= 3) {
                            String bucket = parts[1];
                            String objectKey = parts[2];
                            // 调用文件服务删除图片
                            restTemplate.delete(fileServerUrl + "/" + bucket + "/" + objectKey);
                        }
                    }
                } catch (Exception e) {
                    log.error("删除火灾事件图片失败: {}", e.getMessage());
                    // 继续执行，不影响事件删除
                }
            }
        }
        
        // 执行批量删除
        removeBatchByIds(ids);
    }
} 