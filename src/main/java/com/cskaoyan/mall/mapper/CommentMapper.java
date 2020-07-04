package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Comment;
import com.cskaoyan.mall.bean.CommentExample;
import com.cskaoyan.mall.bean.vo.WxGoodsDetailCommentDataVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    int logicalDeleteCommentById(@Param("id") Integer id);  //逻辑删除

    //  comment & user
    List<WxGoodsDetailCommentDataVO> selectCommentAndUserByGoodsId(@Param("id") Integer id, @Param("type") Integer type, @Param("isLimit") boolean isLimit);

    @Select("select count(*) from cskaoyanmall_comment where value_id = #{valueId} and deleted = 0 and type = #{type} ")
    int countAllCommentByGoodsId(@Param("valueId") Integer valueId, @Param("type") Integer type);

    @Select("select count(*) from cskaoyanmall_comment where type = #{type} and deleted = 0 and value_id = #{valueId} and has_picture = 1")
    int countHasPic(Integer valueId, Integer type);
}