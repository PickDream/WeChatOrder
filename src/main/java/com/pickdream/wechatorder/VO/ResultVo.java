package com.pickdream.wechatorder.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultVo<T> {
    private Integer code;

    private String message;

    private T data;
}
