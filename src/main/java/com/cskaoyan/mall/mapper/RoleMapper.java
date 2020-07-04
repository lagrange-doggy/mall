package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Menu;
import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.bean.RoleExample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    @Select("select * from cskaoyanmall_role where enabled =1")
    Role[] selectRole();

    @Update("update cskaoyanmall_role set `name` =#{name}, `desc` = #{desc}, `update_time` = NOW() where id = #{id}")
    void updateNameAndDescById(int id, String name, String desc);

    @Select("select `id`,`name`,`label`,`parent_id` as parentId , `api` from cskaoyanmall_menu")
    List<Menu> queryMenu();

    @Select("select `permission` from cskaoyanmall_permission where `role_id` = #{roleId} ")
    List<String> queryPermissionById(Integer roleId);

    @Select("select `name` as permission from cskaoyanmall_menu")
    List<String> queryPermission();

    void deletePermissionsById(@Param("id") Integer id, @Param("strings") List<String> strings);

    @Update("update cskaoyanmall_permission set `update_time` = NOW() where role_id = #{id} and permission = #{s} ")
    void updatePermissionByRoleIdAndPermission(@Param("id") Integer id, @Param("s") String s);

    @Insert("insert into cskaoyanmall_permission (role_id , permission , add_time , `update_time` ) values (#{id} ,#{permission} ,NOW(),NOW())")
    void insertPermissionByRoleIdAndPermission(@Param("id") Integer id, @Param("permission") String permission);
}