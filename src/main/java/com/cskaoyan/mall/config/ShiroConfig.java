package com.cskaoyan.mall.config;

import com.cskaoyan.mall.shiro.realm.AdminRealm;
import com.cskaoyan.mall.shiro.realm.WxRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * @ZhidaFeng on 2020/6/30
 **/

@Configuration
public class ShiroConfig {

    @Autowired
    AdminRealm adminRealm;

    @Autowired
    WxRealm wxRealm;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/admin/auth/login");

        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/admin/auth/**","anon");
        filterMap.put("/admin/**","authc");

        filterMap.put("/wx/auth/**","anon");
        filterMap.put("/wx/user/**","authc");
        filterMap.put("/wx/cart/goodscount","anon");
        filterMap.put("/wx/cart/**","authc");
        filterMap.put("/wx/collect/**","authc");
        filterMap.put("/wx/address/**","authc");
        filterMap.put("/wx/order/**","authc");
        filterMap.put("/wx/feedback/**","authc");
        filterMap.put("/wx/coupon/list","anon");
        filterMap.put("/wx/coupon/**","authc");
        filterMap.put("/wx/**","anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 为了可以声明式使用shiro
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     *
     * @param sessionManager
     * @param authenticator
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(DefaultWebSessionManager sessionManager,
                                                     CustomAuthenticator authenticator){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        ArrayList<Realm> arrayList = new ArrayList<>();

        arrayList.add(adminRealm);
        arrayList.add(wxRealm);
        defaultWebSecurityManager.setRealms(arrayList);
        defaultWebSecurityManager.setAuthenticator(authenticator);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        return defaultWebSecurityManager;
    }

    /**
     * 解决前后端分离的session传递问题
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager(){
        CustomWebSessionManager customWebSessionManager = new CustomWebSessionManager();
        // 去掉 login;JSESSIONID
        // customWebSessionManager.setSessionIdUrlRewritingEnabled(false);
        return customWebSessionManager;
    }
    /**
     *
     * @param adminRealm
     * @param wxRealm
     * @return
     */
    @Bean
    public CustomAuthenticator authenticator(AdminRealm adminRealm, WxRealm wxRealm){
        CustomAuthenticator customAuthenticator = new CustomAuthenticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        customAuthenticator.setRealms(realms);
        return customAuthenticator;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.put("org.apache.shiro.authz.AuthorizationException","/404");
        simpleMappingExceptionResolver.setExceptionMappings(mappings);
        return simpleMappingExceptionResolver;
    }

}
