package com.pickdream.wechatorder.utils;

import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.enums.ResultEnum;

public final class ResultVoUtil {

    public static ResultVo success(Object obj){
        ResultVo resultVo = ResultVo.builder()
                .code(ResultEnum.SUCCESS.getCode())
                .message(ResultEnum.SUCCESS.getMsg())
                .data(obj).build();
        return resultVo;
    }

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo failed(Object obj){
        ResultVo resultVo = ResultVo.builder()
                .code(ResultEnum.FAILED.getCode())
                .message(ResultEnum.FAILED.getMsg())
                .data(obj).build();
        return resultVo;
    }

    public static ResultVo failed(){
        return failed(null);
    }
}
