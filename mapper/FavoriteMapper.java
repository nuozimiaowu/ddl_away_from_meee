package com.example.cooperation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cooperation.pojo.Favorite;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
    // 可以根据需要添加自定义方法
}
