package com.example.cooperation.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("通用返回对象")
public class Result<T> implements Serializable {

    @ApiModelProperty("响应状态")
    private Integer code; //编码：1成功，0和其它数字为失败
    @ApiModelProperty("错误信息")
    private String msg; //错误信息
    @ApiModelProperty("响应数据")
    private T data; //数据

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<T>();
        r.data = data;
        r.code = 1;
        return r;
    }


    /**
     * static后面的<T>：表示在方法上声明泛型的固定写法
     * R<T>：方法的返回值，R带泛型
     */
    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

}
