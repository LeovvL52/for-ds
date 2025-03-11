package com.gjq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.common.exception.BusinessException;
import com.gjq.dto.log.SystemLogCreateDTO;
import com.gjq.dto.log.SystemLogQueryDTO;
import com.gjq.dto.log.SystemLogVO;
import com.gjq.entity.SystemLog;
import com.gjq.entity.User;
import com.gjq.mapper.SystemLogMapper;
import com.gjq.service.SystemLogService;
import com.gjq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统日志服务实现类
 */
@Service
@RequiredArgsConstructor
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements SystemLogService {

    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSystemLog(SystemLogCreateDTO dto) {
        // 创建日志
        SystemLog log = BeanUtil.copyProperties(dto, SystemLog.class);

        // 保存日志
        save(log);
        return log.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSystemLog(Long id) {
        // 查询日志
        SystemLog log = getById(id);
        if (log == null) {
            throw new BusinessException("日志不存在");
        }

        // 删除日志
        removeById(id);
    }

    @Override
    public IPage<SystemLogVO> getSystemLogPage(SystemLogQueryDTO dto) {
        // 构建查询条件
        LambdaQueryWrapper<SystemLog> wrapper = new LambdaQueryWrapper<SystemLog>()
                .eq(dto.getUserId() != null, SystemLog::getUserId, dto.getUserId())
                .like(StrUtil.isNotBlank(dto.getOperation()), SystemLog::getOperation, dto.getOperation())
                .like(StrUtil.isNotBlank(dto.getIpAddress()), SystemLog::getIpAddress, dto.getIpAddress())
                .ge(dto.getStartTime() != null, SystemLog::getCreateTime, dto.getStartTime())
                .le(dto.getEndTime() != null, SystemLog::getCreateTime, dto.getEndTime())
                .orderByDesc(SystemLog::getCreateTime);

        // 执行分页查询
        IPage<SystemLog> page = page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);

        // 转换返回结果
        return page.convert(this::convertToVO);
    }

    @Override
    public SystemLogVO getSystemLogById(Long id) {
        // 查询日志
        SystemLog log = getById(id);
        if (log == null) {
            throw new BusinessException("日志不存在");
        }

        // 转换返回结果
        return convertToVO(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearSystemLog() {
        // 清空日志
        remove(null);
    }

    /**
     * 转换为VO对象
     */
    private SystemLogVO convertToVO(SystemLog log) {
        SystemLogVO vo = BeanUtil.copyProperties(log, SystemLogVO.class);

        // 设置用户名称
        if (log.getUserId() != null) {
            User user = userService.getById(log.getUserId());
            if (user != null) {
                vo.setUserName(user.getRealName());
            }
        }

        return vo;
    }
} 