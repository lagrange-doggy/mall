package com.cskaoyan.mall.shiro.realm;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.service.AuthService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ZhidaFeng on 2020/6/29
 **/
@Component
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AuthService authService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
        String username = admin.getUsername();
        List<String> permissions = authService.selectPermissionByUsername(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String passwordFromDb = authService.queryPasswordByUsername(username);
        Admin admin = authService.selectByUsername(username);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(admin, passwordFromDb, this.getName());
        return simpleAuthenticationInfo;
    }
}
