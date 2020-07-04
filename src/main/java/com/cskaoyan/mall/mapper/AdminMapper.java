package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.AdminExample;
import com.cskaoyan.mall.bean.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {
    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin selectByUsername(String username);

    @Select("select role_ids from cskaoyanmall_admin where id = #{id}")
    String selectRolesIdById(Integer id);
}