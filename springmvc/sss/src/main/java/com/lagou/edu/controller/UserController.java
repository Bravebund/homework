package com.lagou.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session) {
        if ("admin".equals(username) && "admin".equals(password)) {
            session.setAttribute("username", username);
            return "redirect:/resume/all";
        } else {
            //跳转登录页面
            return "redirect:/user/toLogin";
        }
    }

}
