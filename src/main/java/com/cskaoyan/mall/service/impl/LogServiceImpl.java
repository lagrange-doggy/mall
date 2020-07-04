package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.LogMapper;
import com.cskaoyan.mall.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;

    @Override
    public void insertLog(Log log) {
        logMapper.insert(log);
    }

    @Override
    public BaseData queryUsers(Integer page, Integer limit, String sort, String order, String name) {
        LogExample logExample = new LogExample();
        logExample.setOrderByClause(sort + " " + order);
        //如果你当前的查询有条件，就增加criteria
        if (name != null) {
            logExample.createCriteria().andAdminLike("%" + name + "%");
        } else {
            logExample.createCriteria();
        }
        //执行查询之前使用分页
        PageHelper.startPage(page, limit);
        List<Log> logs = logMapper.selectByExample(logExample);
        PageInfo<Log> pageInfo = new PageInfo<>(logs);
        long total = pageInfo.getTotal();
//        List<AdminListVo> adminListVoList = new ArrayList<>();
//        for (Admin admin : admins) {
////            List<Integer> roleIds = new ArrayList<Integer>();
////            roleIds.add((int) admin.getRoleIds().charAt(1) - '0');
//            adminListVoList.add(new AdminListVo(admin.getId(), admin.getUsername(), admin.getAvatar(), MyUtilWmz.string2List(admin.getRoleIds())));
//        }
        return new BaseData(logs, total);
    }
}
