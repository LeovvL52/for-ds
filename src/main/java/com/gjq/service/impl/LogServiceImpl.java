package com.gjq.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.common.exception.BusinessException;
import com.gjq.dto.LogQueryDTO;
import com.gjq.entity.Log;
import com.gjq.mapper.LogMapper;
import com.gjq.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统日志Service实现类
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public IPage<Log> getLogPage(LogQueryDTO queryDTO) {
        // 创建分页对象
        Page<Log> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        // 调用Mapper查询
        return baseMapper.selectLogPage(
                page,
                queryDTO.getUserId(),
                queryDTO.getType(),
                queryDTO.getStatus(),
                queryDTO.getStartTime() != null ? queryDTO.getStartTime().toString() : null,
                queryDTO.getEndTime() != null ? queryDTO.getEndTime().toString() : null
        );
    }

    @Override
    public Log getLogById(Long id) {
        Log log = getById(id);
        if (log == null) {
            throw new BusinessException("日志不存在");
        }
        return log;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLog(Long id) {
        if (!removeById(id)) {
            throw new BusinessException("删除日志失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearLog() {
        baseMapper.clearLog();
    }

    @Override
    public void recordLoginLog(Long userId, Integer status, String ip, String error) {
        Log log = new Log();
        log.setUserId(userId);
        log.setType(1); // 登录日志
        log.setModule("用户登录");
        log.setDescription(status == 1 ? "登录成功" : "登录失败");
        log.setMethod("POST");
        log.setIp(ip);
        log.setStatus(status);
        log.setError(error);
        save(log);
    }

    @Override
    public void recordOperationLog(Long userId, String module, String description, String method, String params, String ip) {
        Log log = new Log();
        log.setUserId(userId);
        log.setType(2); // 操作日志
        log.setModule(module);
        log.setDescription(description);
        log.setMethod(method);
        log.setParams(params);
        log.setIp(ip);
        log.setStatus(1); // 操作成功
        save(log);
    }

    @Override
    public void recordErrorLog(Long userId, String module, String description, String method, String params, String ip, String error) {
        Log log = new Log();
        log.setUserId(userId);
        log.setType(3); // 异常日志
        log.setModule(module);
        log.setDescription(description);
        log.setMethod(method);
        log.setParams(params);
        log.setIp(ip);
        log.setStatus(0); // 操作失败
        log.setError(error);
        save(log);
    }
} 