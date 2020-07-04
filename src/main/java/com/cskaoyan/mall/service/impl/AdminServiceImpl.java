package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.bo.AdminUpdateBo;
import com.cskaoyan.mall.bean.vo.AdminCreateVo;
import com.cskaoyan.mall.bean.vo.AdminListVo;
import com.cskaoyan.mall.bean.vo.AdminUpdateVo;
import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.service.AdminService;
import com.cskaoyan.mall.utils.Md5Utils;
import com.cskaoyan.mall.utils.ConvertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminMapper adminMapper;

    @Override
    public BaseData queryUsers(Integer page, Integer limit, String sort, String order, String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.setOrderByClause(sort + " " + order);
        //如果你当前的查询有条件，就增加criteria
        username = ("".equals(username))?null:username;
        if (username != null) {
            adminExample.createCriteria().andUsernameLike("%" + username + "%");
        } else {
            adminExample.createCriteria();
        }
        //执行查询之前使用分页
        PageHelper.startPage(page, limit);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        long total = pageInfo.getTotal();
        List<AdminListVo> adminListVoList = new ArrayList<>();
        for (Admin admin : admins) {
//            List<Integer> roleIds = new ArrayList<Integer>();
//            roleIds.add((int) admin.getRoleIds().charAt(1) - '0');
            adminListVoList.add(new AdminListVo(admin.getId(), admin.getUsername(), admin.getAvatar(), ConvertUtils.string2List(admin.getRoleIds())));
        }
        return new BaseData(adminListVoList, total);
    }
    @Override
    public AdminCreateVo insertAdmin(String username, String password, String avatar, List<Integer> roleIds) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria();
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(Md5Utils.getMD5(password));
        admin.setAvatar(avatar);
        admin.setRoleIds(ConvertUtils.list2String(roleIds));
        Date time = new Date(System.currentTimeMillis());
        admin.setAddTime(time);
        admin.setUpdateTime(time);
        System.out.println(time);
        adminMapper.insertSelective(admin);
        System.out.println(admin.getId());
        return admin2AdminCreatVo(admin);
    }

    @Override
    public void delectAdminById(Object id) {
        adminMapper.deleteByPrimaryKey((Integer) id);
    }

    @Override
    public AdminUpdateVo updateAdminByAdminUpdateBo(AdminUpdateBo adminUpdateBo) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria();
        Admin admin = new Admin();
        admin.setId(adminUpdateBo.getId());
        admin.setUsername(adminUpdateBo.getUsername());
        admin.setPassword(Md5Utils.getMD5(adminUpdateBo.getPassword()));
        admin.setRoleIds(ConvertUtils.list2String(adminUpdateBo.getRoleIds()));
        admin.setAvatar(adminUpdateBo.getAvatar());
        Date time = new Date(System.currentTimeMillis());
        admin.setUpdateTime(time);
        System.out.println(time);
        adminMapper.updateByPrimaryKeySelective(admin);
        System.out.println(admin.getId());
        return admin2AdminUpdateVo(admin);
    }


    private AdminCreateVo admin2AdminCreatVo(Admin admin) {
//        List<Integer> roleIds = new ArrayList<Integer>();
//        roleIds.add((int) admin.getRoleIds().charAt(1) - '0');
        return new AdminCreateVo(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getAddTime().toString(),
                admin.getUpdateTime().toString(), ConvertUtils.string2List(admin.getRoleIds()));
    }
    private AdminUpdateVo admin2AdminUpdateVo(Admin admin) {
//        List<Integer> roleIds = new ArrayList<Integer>();
//        roleIds.add((int) admin.getRoleIds().charAt(1) - '0');
        return new AdminUpdateVo(admin.getId(), admin.getUsername(), admin.getPassword(),admin.getAvatar(),
                admin.getUpdateTime().toString(), ConvertUtils.string2List(admin.getRoleIds()));
    }
}
