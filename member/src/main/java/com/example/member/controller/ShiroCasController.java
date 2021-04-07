package com.example.member.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * 主要是登录/登出的重定向地址变了
 */
public class ShiroCasController {

    private static final Logger logger = LoggerFactory.getLogger(ShiroCasController.class);


    public String index(HttpServletRequest request, Model model){
        System.out.println(request.getUserPrincipal().getName());
        System.out.println(SecurityUtils.getSubject());

        return "/index";
    }
}
