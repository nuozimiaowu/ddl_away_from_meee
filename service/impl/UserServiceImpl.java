package com.example.project4.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project4.mapper.UserMapper;
import com.example.project4.common.Result;
import com.example.project4.pojo.UserDto;
import com.example.project4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,UserDto> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void update(UserDto userDto) {
        UpdateWrapper<UserDto> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userDto.getUserId()); // 假设以userId为条件进行更新
        userMapper.update(userDto, updateWrapper);
    }

    @Override
    public Result<UserDto> selectAll() {
/*
        QueryWrapper<UserDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq()
*/
        return null;
    }

    @Override
    public UserDto findByUsername(UserDto user) {
        return userMapper.selectOne(new LambdaQueryWrapper<UserDto>().eq(UserDto::getUserName,user.getUserName()));
    }

    @Override
    public UserDto findUserById(String userId) {
        return userMapper.selectById(userId);
    }
}
