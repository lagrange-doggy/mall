package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.bo.UserBo;

public interface UserService {

    BaseData queryUsers(Integer page, Integer limit,String username,String mobile, String sort, String order);

    User selectUserByUserName(String username);

    int createUser(UserBo userBo);

    int updatePassword(UserBo userBo);
}
