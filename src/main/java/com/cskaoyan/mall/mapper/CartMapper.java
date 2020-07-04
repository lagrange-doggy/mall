package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Cart;
import com.cskaoyan.mall.bean.CartExample;
import com.cskaoyan.mall.bean.CartTotal;
import com.cskaoyan.mall.bean.GoodsChecked;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CartMapper {
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    Cart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    CartTotal getCartTotal(int userId);

    @Select("select sum(number) from cskaoyanmall_cart where user_id = #{userId} and deleted=false")
    int countCart(@Param("userId") Integer userId);

    @Update("update cskaoyanmall_cart " +
            "set number = number + #{cart.number}  " +
            "where goods_id = #{cart.goodsId}  " +
            " and product_id = #{cart.productId}" +
            " and user_id = #{cart.userId} " +
            " and deleted = false")
    int updateByAddnumber(@Param("cart") Cart cart);


    @Select("select c.id, " +
            "c.user_id as userId, " +
            "c.product_id as productId, " +
            "c.number as number, " +
            "g.id as goodsId, " +
            "g.goods_sn as goodsSn, " +
            "g.`name` as goodsName, " +
            "gp.price as price, " +
            "gp.specifications as specifications, " +
            "gp.url as picUrl " +
            "from cskaoyanmall_goods g, cskaoyanmall_goods_product gp, cskaoyanmall_cart c " +
            "where g.id = gp.goods_id " +
            "and c.product_id = gp.id " +
            "and c.checked = true " +
            "and c.user_id = #{userId} and c.deleted =false")
    List<GoodsChecked> queryGoodsCheckedByUserId(@Param("userId") Integer userId);

    @Select("select " +
            "g.id as goodsId, " +
            "g.goods_sn as goodsSn, " +
            "g.`name` as goodsName, " +
            "gp.id as productId, " +
            "gp.price as price, " +
            "gp.specifications as specifications, " +
            "gp.url as picUrl " +
            "from cskaoyanmall_goods g, cskaoyanmall_goods_product gp " +
            "where g.id = gp.goods_id " +
            "and g.id = #{goodsId} " +
            "and gp.id = #{productId}" )
    GoodsChecked getGoodsCheckedByGoodsIdAndProductId(@Param("goodsId") Integer goodsId, @Param("productId") Integer productId);

    @Select("select " +
            "id " +
            "from cskaoyanmall_cart " +
            "where user_id = userId " +
            "and goods_Id = goodsId " +
            "and productId " +
            "and deleted = false")
      int selectCartIdByGoodIdProductIdUserId(@Param("userId") Integer userId,@Param("goodsId") Integer goodsId, @Param("productId") Integer productId);
}