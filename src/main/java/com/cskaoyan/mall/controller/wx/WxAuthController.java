package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.bo.UserBo;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.config.AliyunComponent;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.service.WxAuthService;
import com.cskaoyan.mall.shiro.token.MallToken;
import com.cskaoyan.mall.utils.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

/**
 * @ZhidaFeng on 2020/6/30
 **/

@RestController
@RequestMapping("wx/auth")
public class WxAuthController {

    @Autowired
    WxAuthService wxAuthService;

    @Autowired
    AliyunComponent aliyunComponent;

    @Autowired
    UserService userService;

    String verifyCode;

    @RequestMapping("login")
    public BaseRespVo login(@RequestBody Map<String,Object>map){
        String username = (String) map.get("username");
        String password = Md5Utils.getMD5((String) map.get("password"));
        MallToken wxToken = new MallToken(username,password,"wx");
        Subject subject = SecurityUtils.getSubject();
        BaseRespVo baseRespVo = new BaseRespVo();
        try {
            subject.login(wxToken);
        } catch (AuthenticationException e) {
            baseRespVo.setErrno(1);
            baseRespVo.setErrmsg("失败");
            return baseRespVo;
        }

        wxAuthService.updateUserLog(username);
        Map userInfo = wxAuthService.getUserInfo(username);
        return BaseRespVo.ok(userInfo);
    }

    @RequestMapping("logout")
    public BaseRespVo logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseRespVo.ok();
    }

    @RequestMapping("regCaptcha")
    public BaseRespVo regCaptcha(@RequestBody Map<String, String> map){
        String mobile = map.get("mobile");
        this.verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        // System.out.println(verifyCode);
        try {
            aliyunComponent.sendMsg(mobile, verifyCode);
        } catch (Exception e) {
            return BaseRespVo.err("验证码发送失败");
        }
        return BaseRespVo.ok();
    }

    @RequestMapping("register")
    public BaseRespVo register(@RequestBody UserBo userBo){
        if (userBo.getCode().equals(verifyCode)) {
            int errno = userService.createUser(userBo);
            if (errno == 0) return BaseRespVo.ok();
            else if (errno == 200) return BaseRespVo.err("用户名已存在");
            else if (errno == 300) return BaseRespVo.err("该手机号已注册");
            else return BaseRespVo.err("注册失败");
        }
        else return BaseRespVo.err("验证码有误");
    }

    @RequestMapping("reset")
    public BaseRespVo reset(@RequestBody UserBo userBo){
        if (userBo.getCode().equals(verifyCode)) {
            int errno = userService.updatePassword(userBo);
            if (errno == 0) return BaseRespVo.ok();
            // else if (errno == 200) return BaseRespVo.err("新密码不能与旧密码一致");
            else return BaseRespVo.err("该手机号尚未注册");
        }
        else return BaseRespVo.err("验证码有误");
    }
}
