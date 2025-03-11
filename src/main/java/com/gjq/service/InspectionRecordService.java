package com.gjq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gjq.dto.inspection.InspectionRecordCreateDTO;
import com.gjq.dto.inspection.InspectionRecordQueryDTO;
import com.gjq.dto.inspection.InspectionRecordUpdateDTO;
import com.gjq.dto.inspection.InspectionRecordVO;
import com.gjq.entity.InspectionRecord;

/**
 * 消防检查记录服务接口
 */
public interface InspectionRecordService extends IService<InspectionRecord> {
    /**
     * 创建消防检查记录
     *
     * @param dto 创建参数
     * @return 记录ID
     */
    Long createInspectionRecord(InspectionRecordCreateDTO dto);

    /**
     * 更新消防检查记录
     *
     * @param dto 更新参数
     */
    void updateInspectionRecord(InspectionRecordUpdateDTO dto);

    /**
     * 删除消防检查记录
     *
     * @param id 记录ID
     */
    void deleteInspectionRecord(Long id);

    /**
     * 分页查询消防检查记录列表
     *
     * @param dto 查询参数
     * @return 记录列表
     */
    IPage<InspectionRecordVO> getInspectionRecordPage(InspectionRecordQueryDTO dto);

    /**
     * 获取消防检查记录详情
     *
     * @param id 记录ID
     * @return 记录详情
     */
    InspectionRecordVO getInspectionRecordById(Long id);
} 