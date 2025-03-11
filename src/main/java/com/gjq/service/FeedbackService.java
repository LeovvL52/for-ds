package com.gjq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gjq.dto.FeedbackCreateDTO;
import com.gjq.dto.FeedbackQueryDTO;
import com.gjq.dto.FeedbackReplyDTO;
import com.gjq.entity.Feedback;

/**
 * 用户反馈Service接口
 */
public interface FeedbackService extends IService<Feedback> {
    /**
     * 分页查询用户反馈列表
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    IPage<Feedback> getFeedbackPage(FeedbackQueryDTO queryDTO);

    /**
     * 获取用户反馈详情
     *
     * @param id 反馈ID
     * @return 反馈详情
     */
    Feedback getFeedbackById(Long id);

    /**
     * 创建用户反馈
     *
     * @param createDTO 创建参数
     */
    void createFeedback(FeedbackCreateDTO createDTO);

    /**
     * 回复用户反馈
     *
     * @param replyDTO 回复参数
     */
    void replyFeedback(FeedbackReplyDTO replyDTO);

    /**
     * 删除用户反馈
     *
     * @param id 反馈ID
     */
    void deleteFeedback(Long id);
} 