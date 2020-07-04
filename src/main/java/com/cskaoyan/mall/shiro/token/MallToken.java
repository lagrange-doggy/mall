package com.cskaoyan.mall.shiro.token;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @ZhidaFeng on 2020/6/29
 **/

@Data
public class MallToken extends UsernamePasswordToken {
    String type;

    public MallToken(String username,String password,String type){
        super(username,password);
        this.type = type;
    }
}
