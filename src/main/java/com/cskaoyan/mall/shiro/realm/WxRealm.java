package com.cskaoyan.mall.shiro.realm;

import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.shiro.token.MallToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ZhidaFeng on 2020/6/30
 **/
@Component
public class WxRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        MallToken token = (MallToken) authenticationToken;

        String username = token.getUsername();
        User user = userService.selectUserByUserName(username);
        String credential = user.getPassword();

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, credential, this.getName());
        return authenticationInfo;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
