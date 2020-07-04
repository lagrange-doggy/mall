package com.cskaoyan.mall.bean;

import java.util.ArrayList;
import java.util.List;

public class Region {
    private Integer id;

    private Integer pid;

    private String name;

    private Byte type;

    public List<Region> getChildren() {
        return children;
    }

    public void setChildren(List<Region> children) {
        this.children = children;
    }

    private Integer code;


    private List<Region> children=new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}