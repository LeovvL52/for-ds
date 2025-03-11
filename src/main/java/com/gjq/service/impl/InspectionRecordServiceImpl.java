package com.gjq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.common.exception.BusinessException;
import com.gjq.dto.inspection.InspectionRecordCreateDTO;
import com.gjq.dto.inspection.InspectionRecordQueryDTO;
import com.gjq.dto.inspection.InspectionRecordUpdateDTO;
import com.gjq.dto.inspection.InspectionRecordVO;
import com.gjq.entity.InspectionRecord;
import com.gjq.entity.User;
import com.gjq.mapper.InspectionRecordMapper;
import com.gjq.service.InspectionRecordService;
import com.gjq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消防检查记录服务实现类
 */
@Service
@RequiredArgsConstructor
public class InspectionRecordServiceImpl extends ServiceImpl<InspectionRecordMapper, InspectionRecord> implements InspectionRecordService {

    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInspectionRecord(InspectionRecordCreateDTO dto) {
        // 校验检查人是否存在
        User inspector = userService.getById(dto.getInspectorId());
        if (inspector == null) {
            throw new BusinessException("检查人不存在");
        }

        // 创建记录
        InspectionRecord record = BeanUtil.copyProperties(dto, InspectionRecord.class);

        // 保存记录
        save(record);
        return record.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInspectionRecord(InspectionRecordUpdateDTO dto) {
        // 查询记录
        InspectionRecord record = getById(dto.getId());
        if (record == null) {
            throw new BusinessException("记录不存在");
        }

        // 更新记录信息
        BeanUtil.copyProperties(dto, record);
        updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInspectionRecord(Long id) {
        // 查询记录
        InspectionRecord record = getById(id);
        if (record == null) {
            throw new BusinessException("记录不存在");
        }

        // 删除记录
        removeById(id);
    }

    @Override
    public IPage<InspectionRecordVO> getInspectionRecordPage(InspectionRecordQueryDTO dto) {
        // 构建查询条件
        LambdaQueryWrapper<InspectionRecord> wrapper = new LambdaQueryWrapper<InspectionRecord>()
                .eq(dto.getInspectorId() != null, InspectionRecord::getInspectorId, dto.getInspectorId())
                .like(StrUtil.isNotBlank(dto.getLocation()), InspectionRecord::getLocation, dto.getLocation())
                .eq(dto.getStatus() != null, InspectionRecord::getStatus, dto.getStatus())
                .ge(dto.getStartTime() != null, InspectionRecord::getInspectionTime, dto.getStartTime())
                .le(dto.getEndTime() != null, InspectionRecord::getInspectionTime, dto.getEndTime())
                .orderByDesc(InspectionRecord::getInspectionTime);

        // 执行分页查询
        IPage<InspectionRecord> page = page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);

        // 转换返回结果
        return page.convert(this::convertToVO);
    }

    @Override
    public InspectionRecordVO getInspectionRecordById(Long id) {
        // 查询记录
        InspectionRecord record = getById(id);
        if (record == null) {
            throw new BusinessException("记录不存在");
        }

        // 转换返回结果
        return convertToVO(record);
    }

    /**
     * 转换为VO对象
     */
    private InspectionRecordVO convertToVO(InspectionRecord record) {
        InspectionRecordVO vo = BeanUtil.copyProperties(record, InspectionRecordVO.class);

        // 设置检查人姓名
        User inspector = userService.getById(record.getInspectorId());
        if (inspector != null) {
            vo.setInspectorName(inspector.getRealName());
        }

        return vo;
    }
} 