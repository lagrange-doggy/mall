package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderExample;
import com.cskaoyan.mall.bean.OrderStistiscs;
import com.cskaoyan.mall.bean.vo.UserOrderInfoVO;
import com.cskaoyan.mall.bean.vo.UserOrderListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<OrderStistiscs> selectGroupByOrder();

    List<Order> selectOrderPageList(@Param("orderStatusArray") List<Integer> orderStatusArray, @Param("sortWay") String sortWay, @Param("orderWay") String orderWay, @Param("userId") Integer userId, @Param("orderSn") String orderSn);

    @Select("SELECT COUNT(0) FROM `cskaoyanmall_order` where user_id = #{id} and `order_status` = #{orderStatus} ")
    Integer queryOrderNumByUserIdAndOrderStatus(@Param("id") Integer id, @Param("orderStatus") int orderStatus);

    List<UserOrderListVO> selectOrderInfoByExample(OrderExample orderExample);

    UserOrderInfoVO selectOrderInfoByPrimaryKey(Integer orderId);
}