package com.gjq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gjq.common.Result;
import com.gjq.dto.FeedbackCreateDTO;
import com.gjq.dto.FeedbackQueryDTO;
import com.gjq.dto.FeedbackReplyDTO;
import com.gjq.entity.Feedback;
import com.gjq.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户反馈Controller
 */
@Tag(name = "用户反馈", description = "用户反馈管理相关接口")
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    /**
     * 分页查询用户反馈列表
     */
    @Operation(summary = "分页查询反馈", description = "分页查询用户反馈列表")
    @GetMapping("/page")
    public Result<IPage<Feedback>> getFeedbackPage(@Validated FeedbackQueryDTO queryDTO) {
        return Result.success(feedbackService.getFeedbackPage(queryDTO));
    }

    /**
     * 获取用户反馈详情
     */
    @Operation(summary = "获取反馈详情", description = "根据ID获取用户反馈详情")
    @GetMapping("/{id}")
    public Result<Feedback> getFeedbackById(@Parameter(description = "反馈ID") @PathVariable Long id) {
        return Result.success(feedbackService.getFeedbackById(id));
    }

    /**
     * 创建用户反馈
     */
    @Operation(summary = "创建反馈", description = "创建新的用户反馈")
    @PostMapping
    public Result<Void> createFeedback(@Validated @RequestBody FeedbackCreateDTO createDTO) {
        feedbackService.createFeedback(createDTO);
        return Result.success();
    }

    /**
     * 回复用户反馈
     */
    @Operation(summary = "回复反馈", description = "管理员回复用户反馈")
    @PutMapping
    public Result<Void> replyFeedback(@Validated @RequestBody FeedbackReplyDTO replyDTO) {
        feedbackService.replyFeedback(replyDTO);
        return Result.success();
    }

    /**
     * 删除用户反馈
     */
    @Operation(summary = "删除反馈", description = "根据ID删除用户反馈")
    @Parameter(name = "id", description = "反馈ID", required = true)
    @DeleteMapping("/{id}")
    public Result<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return Result.success();
    }
} 