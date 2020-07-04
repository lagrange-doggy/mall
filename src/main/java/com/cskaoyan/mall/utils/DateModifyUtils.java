package com.cskaoyan.mall.utils;

import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/7/1 22:45
 */
public class DateModifyUtils {

    /**
     * 增加天数
     * @param days
     * @return
     */
    public static Date addDays(int days) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

}
