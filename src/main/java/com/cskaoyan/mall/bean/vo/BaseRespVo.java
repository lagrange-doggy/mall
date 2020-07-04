package com.cskaoyan.mall.bean.vo;

import lombok.Data;

@Data
public class BaseRespVo<T> {
    T data;
    String errmsg;
    Integer errno;

    public static BaseRespVo ok(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }
    public static BaseRespVo ok(Object data){
        BaseRespVo baseRespVo = ok();
        baseRespVo.setData(data);
        return baseRespVo;
    }

    public static BaseRespVo err() {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("失败");
        baseRespVo.setErrno(200);
        return baseRespVo;
    }

    public static BaseRespVo err(String errmsg) {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg(errmsg);
        baseRespVo.setErrno(200);
        return baseRespVo;
    }

    public static BaseRespVo err(Integer errno, String errmsg) {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(errno);
        baseRespVo.setErrmsg(errmsg);
        return baseRespVo;
    }
}
