package com.gjq.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.common.exception.BusinessException;
import com.gjq.dto.FeedbackCreateDTO;
import com.gjq.dto.FeedbackQueryDTO;
import com.gjq.dto.FeedbackReplyDTO;
import com.gjq.entity.Feedback;
import com.gjq.mapper.FeedbackMapper;
import com.gjq.service.FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户反馈Service实现类
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {
    @Override
    public IPage<Feedback> getFeedbackPage(FeedbackQueryDTO queryDTO) {
        Page<Feedback> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        return baseMapper.selectFeedbackPage(
                page,
                null,
                queryDTO.getUserId(),
                queryDTO.getType(),
                queryDTO.getStatus(),
                queryDTO.getStartTime() != null ? queryDTO.getStartTime().toString() : null,
                queryDTO.getEndTime() != null ? queryDTO.getEndTime().toString() : null
        );
    }

    @Override
    public Feedback getFeedbackById(Long id) {
        // 查询反馈记录
        Feedback feedback = baseMapper.selectFeedbackPage(
                new Page<>(1, 1),
                id,
                null,
                null,
                null,
                null,
                null
        ).getRecords().stream().findFirst().orElse(null);
        
        if (feedback == null) {
            throw new BusinessException("反馈记录不存在");
        }
        return feedback;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createFeedback(FeedbackCreateDTO createDTO) {
        Feedback feedback = new Feedback();
        feedback.setUserId(createDTO.getUserId());
        feedback.setType(createDTO.getType());
        feedback.setContent(createDTO.getContent());
        feedback.setStatus(0); // 初始状态：未处理
        save(feedback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyFeedback(FeedbackReplyDTO replyDTO) {
        Feedback feedback = getFeedbackById(replyDTO.getId());
        feedback.setStatus(replyDTO.getStatus());
        feedback.setReply(replyDTO.getReply());
        updateById(feedback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFeedback(Long id) {
        if (!removeById(id)) {
            throw new BusinessException("删除反馈记录失败");
        }
    }
} 