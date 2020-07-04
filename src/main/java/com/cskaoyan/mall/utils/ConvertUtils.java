package com.cskaoyan.mall.utils;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConvertUtils {

    public static List string2List(String string) {
        ArrayList<Integer> objects = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c >= '0' && c <= '9') {
                objects.add(c - '0');
            }
        }
        return objects;
    }

    public static String list2String(List list) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('[');
        for (Object o : list) {
            stringBuffer.append(o);
        }
        return list.toString();
    }
}
