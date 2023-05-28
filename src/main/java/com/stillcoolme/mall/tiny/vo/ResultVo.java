package com.stillcoolme.mall.tiny.vo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ResultVo {

    // 0 fail， 1 success
    private Integer resultCode;
    // 提示信息
    private String msg;
    // 返回体
    private Object resultObject;
    // 日期
    private Object date;
}
