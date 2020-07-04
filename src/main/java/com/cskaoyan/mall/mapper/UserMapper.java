package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.UserExample;
import com.cskaoyan.mall.bean.UserStatistics;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<UserStatistics> selectGroupByAddTime();

    User selectIdByUsername(String username);

    @Select("select id from cskaoyanmall_user where mobile = #{mobile}")
    List<Integer> selectByMobile(String mobile);

    @Select("select id from cskaoyanmall_user where mobile = #{username}")
    List<Integer> selectByUsername(String uername);
}