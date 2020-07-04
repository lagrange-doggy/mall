package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.bo.AdminUpdateBo;
import com.cskaoyan.mall.bean.vo.AdminCreateVo;
import com.cskaoyan.mall.bean.vo.AdminUpdateVo;

import java.util.List;

public interface AdminService {

    BaseData queryUsers(Integer page, Integer limit, String sort, String order,String username);

    AdminCreateVo insertAdmin(String username, String password, String avatar, List<Integer> roleIds);

    void delectAdminById(Object id);

    AdminUpdateVo updateAdminByAdminUpdateBo(AdminUpdateBo adminUpdateBo);

}
