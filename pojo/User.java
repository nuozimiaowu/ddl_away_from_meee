package com.example.cooperation.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("winter_hoiliday")
@Data
public class User {
    @TableId(value = "user_id")
    private String userId;
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "user_password")
    private String userPassword;
    @TableField(value = "avatar_url")
    private String avatarUrl;
    @TableField(value = "introduction")
    private String introduction;

}
