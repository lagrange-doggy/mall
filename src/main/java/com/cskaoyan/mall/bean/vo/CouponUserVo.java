package com.cskaoyan.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.net.InetAddress;
import java.util.Date;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/7/2 10:52
 */
@Data
public class CouponUserVo {

    private InetAddress id;

    private String name;

    private String desc;

    private String tag;

    private Double min;

    private Double discount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

}
