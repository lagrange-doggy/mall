package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.UserExample;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.AuthService;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.service.WxAuthService;
import com.cskaoyan.mall.utils.IpUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ZhidaFeng on 2020/6/30
 **/
@Service
public class WxAuthServiceImpl implements WxAuthService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    AuthService authService;
    @Override
    public int updateUserLog(String username) {

        User user = userService.selectUserByUserName(username);
        user.setLastLoginTime(new Date());
        String ipAddress = IpUtils.getIpAddr(request);
        user.setLastLoginIp(ipAddress);
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i;

    }


    @Override
    public Map getUserInfo(String username) {
        User user=userService.selectUserByUserName(username);
        HashMap<String, Object> userInfoMap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();

        userInfoMap.put("nickName",user.getNickname());
        userInfoMap.put("avatarUrl",user.getAvatar());

        map.put("userInfo",userInfoMap);
        map.put("tokenExpire",new Date());

        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getSession().getId();

        map.put("token",token);

        return map;
    }
}
