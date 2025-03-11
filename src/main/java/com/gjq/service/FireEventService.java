package com.gjq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gjq.dto.fire.FireEventCreateDTO;
import com.gjq.dto.fire.FireEventHandleDTO;
import com.gjq.dto.fire.FireEventQueryDTO;
import com.gjq.dto.fire.FireEventVO;
import com.gjq.dto.fire.FireEventUpdateDTO;
import com.gjq.entity.FireEvent;

import java.util.List;

/**
 * 火灾事件服务接口
 */
public interface FireEventService extends IService<FireEvent> {
    /**
     * 创建火灾事件
     *
     * @param dto 创建参数
     * @return 事件ID
     */
    Long createFireEvent(FireEventCreateDTO dto);

    /**
     * 处理火灾事件
     *
     * @param dto 处理参数
     */
    void handleFireEvent(FireEventHandleDTO dto);

    /**
     * 分页查询火灾事件列表
     *
     * @param dto 查询参数
     * @return 事件列表
     */
    IPage<FireEventVO> getFireEventPage(FireEventQueryDTO dto);

    /**
     * 获取火灾事件详情
     *
     * @param id 事件ID
     * @return 事件详情
     */
    FireEventVO getFireEventById(Long id);

    /**
     * 更新火灾事件状态
     *
     * @param id     事件ID
     * @param status 状态(0:未处理 1:处理中 2:已处理)
     */
    void updateFireEventStatus(Long id, Integer status);

    /**
     * 删除火灾事件
     */
    void deleteFireEvent(Long id);

    /**
     * 批量删除火灾事件
     */
    void batchDeleteFireEvents(List<Long> ids);

    /**
     * 更新火灾事件
     */
    void updateFireEvent(FireEventUpdateDTO dto);
} 