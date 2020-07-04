package com.cskaoyan.mall.bean.vo;//wx

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OrderStatus {

    private static Map<Short,String> orderStatusMap = new HashMap();

    static {
        orderStatusMap.put((short) 101, "未付款");
        orderStatusMap.put((short) 102, "已取消");
        orderStatusMap.put((short) 103, "订单完成，已评价");
        orderStatusMap.put((short) 201, "已付款");
        orderStatusMap.put((short) 202, "订单取消，退款中");
        orderStatusMap.put((short) 203, "已退款");
        orderStatusMap.put((short) 301, "已发货");
        orderStatusMap.put((short) 401, "已收货");
        orderStatusMap.put((short) 402, "已收货(系统)");
    }

    public static String getOrderStatusText(Short status) {
        return orderStatusMap.get(status);
    }
    public static List getOrderStatus(Integer showType) {
        List status = new ArrayList();
        if (showType == 1) {
            status.add((short) 101);
            return status;
        } else if (showType == 2) {
            status.add((short) 201);
            return status;
        } else if (showType == 3) {
            status.add((short) 301);
            return status;
        } else if (showType == 4) {
            status.add((short) 401);
            status.add((short) 402);
            return status;
        }
        return null;
    }
}