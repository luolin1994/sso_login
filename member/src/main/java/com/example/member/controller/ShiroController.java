package com.example.member.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ShiroController {

    private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);


    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("userName","maple");
        return "/index";
    }

    //@RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping("/user")
    public String user(Model model){
        model.addAttribute("userName","maple");
        return "/user";
    }

    /**
     * 要求必须有admin:manage的权限才能执行下述方法
     * @param model
     * @return
     */
    @RequiresPermissions("admin:manage")
    @GetMapping("/manage")
    public String manage(Model model){
        model.addAttribute("userName","maple");
        return "/user_manage";
    }

    @RequiresPermissions("admin:query")
    @GetMapping("/query")
    public String query(Model model){
        model.addAttribute("userName","maple");
        return "/user_query";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("userName","maple");
        return "/login";
    }

    @PostMapping("/login")
    public String login(String username, String password){
        System.out.println(username + password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,false);

        try {
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            subject.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        }catch (UnknownAccountException uae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
        }catch (IncorrectCredentialsException ice){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
        }catch (LockedAccountException lae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
        }catch (ExcessiveAttemptsException eae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
        }catch (AuthenticationException ae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
        }
        if (subject.isAuthenticated()){
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            return "redirect:/user";
        }
        token.clear();
        return "redirect:/login";

    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        return "redirect:/login";
    }

    @GetMapping("/403")
    public String unauthorizedRole(){
        logger.info("------没有权限-------");
        return "/403";
    }



}
