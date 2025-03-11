package com.gjq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjq.entity.FireEvent;
import org.apache.ibatis.annotations.Mapper;

/**
 * 火灾事件记录Mapper接口
 */
@Mapper
public interface FireEventMapper extends BaseMapper<FireEvent> {
} 