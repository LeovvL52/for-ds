package com.gjq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.common.exception.BusinessException;
import com.gjq.dto.user.*;
import com.gjq.entity.User;
import com.gjq.mapper.UserMapper;
import com.gjq.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final HttpSession session;

    @Override
    public UserLoginVO login(UserLoginDTO dto) {
        // 查询用户
        User user = lambdaQuery()
                .eq(User::getUsername, dto.getUsername())
                .one();
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 校验密码
        if (!SecureUtil.md5(dto.getPassword()).equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 校验状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        // 保存登录信息
        session.setAttribute("user", user);

        // 转换返回结果
        return BeanUtil.copyProperties(user, UserLoginVO.class);
    }

    @Override
    public void register(UserRegisterDTO dto) {
        // 校验用户名是否存在
        if (lambdaQuery().eq(User::getUsername, dto.getUsername()).count() > 0) {
            throw new BusinessException("用户名已存在");
        }

        // 创建用户
        User user = BeanUtil.copyProperties(dto, User.class);
        // 设置密码
        user.setPassword(SecureUtil.md5(dto.getPassword()));
        // 设置角色
        user.setRole(0);
        // 设置状态
        user.setStatus(1);

        // 保存用户
        save(user);
    }

    @Override
    public void updatePassword(UserPasswordDTO dto) {
        // 查询用户
        User user = getById(dto.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 校验原密码
        if (!SecureUtil.md5(dto.getOldPassword()).equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        // 更新密码
        user.setPassword(SecureUtil.md5(dto.getNewPassword()));
        updateById(user);
    }

    @Override
    public void updateUser(UserUpdateDTO dto) {
        // 查询用户
        User user = getById(dto.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 更新用户信息
        BeanUtil.copyProperties(dto, user);
        updateById(user);
    }

    @Override
    public IPage<User> getUserPage(UserQueryDTO dto) {
        // 构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(StrUtil.isNotBlank(dto.getUsername()), User::getUsername, dto.getUsername())
                .like(StrUtil.isNotBlank(dto.getRealName()), User::getRealName, dto.getRealName())
                .like(StrUtil.isNotBlank(dto.getPhone()), User::getPhone, dto.getPhone())
                .like(StrUtil.isNotBlank(dto.getEmail()), User::getEmail, dto.getEmail())
                .eq(dto.getRole() != null, User::getRole, dto.getRole())
                .eq(dto.getStatus() != null, User::getStatus, dto.getStatus());

        // 执行分页查询
        return page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    @Override
    public UserLoginVO getCurrentUser() {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new BusinessException(401, "用户未登录");
        }

        // 查询最新用户信息
        user = getById(user.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 转换返回结果
        return BeanUtil.copyProperties(user, UserLoginVO.class);
    }

    @Override
    public void logout() {
        // 清除登录信息
        session.removeAttribute("user");
    }
} 