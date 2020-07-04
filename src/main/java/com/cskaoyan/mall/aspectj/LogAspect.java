package com.cskaoyan.mall.aspectj;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.Log;
import com.cskaoyan.mall.service.LogService;
import com.cskaoyan.mall.utils.IpUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/7/2 9:21
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    LogService logService;

    @Autowired
    HttpServletRequest request;

    @Pointcut("@annotation(com.cskaoyan.mall.config.Log)")
    public void loginPointCut(){}

    @Pointcut("execution(* com..admin.AuthController.logout(..))")
    public void logoutPointCut() {}

    /**
     * 切面，配置通知
     */
    @After("loginPointCut()")
    public void saveAdminLog() throws Throwable {
        Log log = new Log();
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();

        log.setAdmin(admin.getUsername());
        log.setIp(IpUtils.getIpAddr(request));
        if (request.getMethod().equals("POST")) log.setType(1);
        else log.setType(0);
        log.setAction(request.getRequestURI());
        if (admin != null) log.setStatus(true);
        else log.setStatus(false);
        Date date = new Date();
        log.setAddTime(date);
        log.setUpdateTime(date);
        log.setDeleted(false);

        logService.insertLog(log);
    }

    @AfterThrowing(value = "loginPointCut()", throwing = "e")
    public void afterThrowing(Exception e) {
        System.out.println(e.getMessage());
    }

    @Before(value = "logoutPointCut()")
    public void saveLogout() throws Throwable {
        Log log = new Log();
        Subject subject = SecurityUtils.getSubject();
        Admin admin = null;
        try {
            admin = (Admin) subject.getPrincipal();
            log.setAdmin(admin.getUsername());
            log.setIp(IpUtils.getIpAddr(request));
            if (request.getMethod().equals("POST")) log.setType(1);
            else log.setType(0);
            log.setAction(request.getRequestURI());
            if (admin != null) log.setStatus(true);
            else log.setStatus(false);
            Date date = new Date();
            log.setAddTime(date);
            log.setUpdateTime(date);
            log.setDeleted(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logService.insertLog(log);
    }

    @AfterThrowing(value = "logoutPointCut()", throwing = "e")
    public void afterLogoutThrowing(Exception e) {
        Log log = new Log();
        log.setIp(IpUtils.getIpAddr(request));
        if (request.getMethod().equals("POST")) log.setType(1);
        else log.setType(0);
        log.setAction(request.getRequestURI());
        log.setStatus(false);
        Date date = new Date();
        log.setAddTime(date);
        log.setUpdateTime(date);
        log.setDeleted(false);
        logService.insertLog(log);
    }
}
