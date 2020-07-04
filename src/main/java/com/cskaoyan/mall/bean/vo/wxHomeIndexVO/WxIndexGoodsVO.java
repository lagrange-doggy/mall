package com.cskaoyan.mall.bean.vo.wxHomeIndexVO;

import com.cskaoyan.mall.bean.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: GZ
 * @description: wx index请求newGoodsList/hotGoodsList/goodsList/goods obj
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxIndexGoodsVO {
    private Integer id;

    private String name;

    private String brief;

    private String picUrl;

    private Boolean isNew;

    private Boolean isHot;

    private BigDecimal counterPrice;

    private BigDecimal retailPrice;

    public WxIndexGoodsVO(Goods goods) {
        this.id = goods.getId();
        this.name = goods.getName();
        this.brief = goods.getBrief();
        this.picUrl = goods.getPicUrl();
        this.isNew = goods.getIsNew();
        this.isHot = goods.getIsHot();
        this.counterPrice = goods.getCounterPrice();
        this.retailPrice = goods.getRetailPrice();
    }
}
