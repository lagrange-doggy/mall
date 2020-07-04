package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.UserExample;
import com.cskaoyan.mall.bean.bo.UserBo;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.utils.GenSerialUtils;
import com.cskaoyan.mall.utils.IpUtils;
import com.cskaoyan.mall.utils.Md5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    HttpServletRequest request;

    @Override
    public BaseData queryUsers(Integer page, Integer limit, String username, String mobile, String sort, String order) {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause(sort + " " + order);
        //如果你当前的查询有条件，就增加criteria
        //userExample.createCriteria().andAddTimeBetween()
//        userExample.createCriteria().andUsernameLike("%"+username+"%");
        if ("".equals(username)) username = null;
        if ("".equals(mobile)) mobile = null;
        if (username == null && mobile == null) {
            userExample.createCriteria().andDeletedEqualTo(false);
        } else if (username == null) {
            userExample.createCriteria().andDeletedEqualTo(false).andMobileLike("%" + mobile + "%");
        }else if (mobile == null){
            userExample.createCriteria().andUsernameLike("%" + username + "%").andDeletedEqualTo(false);
        }else {
            userExample.createCriteria().andUsernameLike("%" + username + "%").andDeletedEqualTo(false).andMobileLike("%" + mobile + "%");
        }
        /* 张总之前的代码
        if (username != null && mobile == null) {
            userExample.createCriteria().andUsernameLike("%" + username + "%");
        }
        if (username == null && mobile != null) {
            userExample.createCriteria().andMobileLike(mobile);
        }
        if (username != null && mobile != null) {
            userExample.createCriteria().andMobileLike(mobile).andUsernameLike("%" + username + "%");
        }*/
        //执行查询之前使用分页
        PageHelper.startPage(page, limit);
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        long total = pageInfo.getTotal();
        return new BaseData(users, total);
    }

    @Override
    public int updatePassword(UserBo userBo) {
        List<Integer> users = userMapper.selectByMobile(userBo.getMobile());
        if (users.size() == 0) return 200;
        for (Integer userId : users) {
            User user = userMapper.selectByPrimaryKey(userId);
            user.setUpdateTime(new Date());
            user.setPassword(Md5Utils.getMD5(userBo.getPassword()));
            userMapper.updateByPrimaryKey(user);
        }
        return 0;
    }

    @Override
    public int createUser(UserBo userBo) {
        UserExample userExample = new UserExample();
        List<Integer> users1 = userMapper.selectByMobile(userBo.getMobile());
        List<Integer> users2 = userMapper.selectByUsername(userBo.getUsername());
        userExample.createCriteria().andUsernameEqualTo(userBo.getUsername())
                .andMobileEqualTo(userBo.getMobile());
        List<User> users = userMapper.selectByExample(userExample);
        if (users2.size() != 0) return 200;
        else if (users1.size() != 0) return 300;
        else if (users.size() != 0) return 500;
        User user = new User();
        user.setUsername(userBo.getUsername());
        user.setPassword(Md5Utils.getMD5(userBo.getPassword()));
        user.setGender((byte) 0);
        Date date = new Date();
        user.setBirthday(date);
        user.setLastLoginIp(IpUtils.getIpAddr(request));
        user.setMobile(userBo.getMobile());
        user.setNickname(GenSerialUtils.generateNewCode());
        user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80");
        user.setWeixinOpenid(userBo.getWxCode());
        user.setStatus((byte) 0);
        user.setUserLevel((byte) 0);
        user.setAddTime(date);
        user.setUpdateTime(date);
        user.setDeleted(false);
        userMapper.insert(user);
        return 0;
    }

    @Override
    public User selectUserByUserName(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(userExample);
        return (userList == null ? null : userList.get(0));
    }
}
