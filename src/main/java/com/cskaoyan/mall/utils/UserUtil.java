package com.cskaoyan.mall.utils;

import com.cskaoyan.mall.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omg.CORBA.UserException;

/**
 * @ZhidaFeng on 2020/7/1
 **/
public class UserUtil {
    public static Integer getUserId() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        /*if (user == null) {
            throw new Exception();
        }*/
        return user.getId();
    }
}
