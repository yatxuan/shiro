package com.xuan.controller;

import com.xuan.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ASUS
 */
@Controller
public class UserController {

    @RequestMapping(value = "getIndex", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getIndex(User user,@RequestParam(value = "rememberMe",required = false) boolean rememberMe) {

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try {
            //为 true 自动保存cookie 实现自动登录
            token.setRememberMe(true);

            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }

        if (subject.hasRole("admin")) {
            try {
                subject.checkPermission("user:delete");
                return "有user:delete功能";
            } catch (AuthorizationException e) {
                e.printStackTrace();
                return "无user:delete功能";
            }

        }
        return "无admin权限";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("user:delete")
    @RequestMapping("/testRole1")
    @ResponseBody
    public String testRole1() {
        return "testRole1:success";
    }

    @RequiresRoles("admin2")
    @RequestMapping("/testRole2")
    @ResponseBody
    public String testRole2() {
        return "testRole2:success";
    }

    @RequestMapping("/testRole3")
    @ResponseBody
    public String testRole3() {
        return "testRole3:success";
    }

    @RequestMapping("/testRole4")
    @ResponseBody
    public String testRole4() {
        return "testRole4:success";
    }

    @RequestMapping("/testRole5")
    @ResponseBody
    public String testRole5() {
        return "testRole5:success";
    }
}
