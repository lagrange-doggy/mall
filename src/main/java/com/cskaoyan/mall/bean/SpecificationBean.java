package com.cskaoyan.mall.bean;

import lombok.Data;
import org.aspectj.lang.annotation.DeclareAnnotation;

import java.util.ArrayList;
import java.util.List;

/**
 * @ZhidaFeng on 2020/7/3
 **/

@Data
public class SpecificationBean {
    private String name;
    private List<GoodsSpecification> valueList;

    public SpecificationBean() {
        this.valueList = new ArrayList<GoodsSpecification>();
    }

    public SpecificationBean(String name, List<GoodsSpecification> valueList) {
        this.name = name;
        this.valueList = valueList;
    }
}
