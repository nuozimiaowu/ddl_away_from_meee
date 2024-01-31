package com.example.project4.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project4.common.Result;
import com.example.project4.pojo.UserDto;

public interface UserService extends IService<UserDto>{
    void update(UserDto userDto);

    Result<UserDto> selectAll();

    UserDto findByUsername(UserDto user);

    UserDto findUserById(String userId);
}
