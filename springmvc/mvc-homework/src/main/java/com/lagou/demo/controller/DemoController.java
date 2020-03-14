package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;
import com.lagou.edu.mvcframework.annotations.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LagouController
@LagouRequestMapping("/demo")
@Security(value = {"wangwu"})
public class DemoController {


    @LagouAutowired
    private IDemoService demoService;


    /**
     * URL: /demo/query?name=lisi
     *
     * @param request
     * @param response
     * @param name
     * @return
     */
    @LagouRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String name) {
        return demoService.get(name);
    }

    /**
     * URL: /demo/query01?username=zhangsan
     *
     * @param request
     * @param response
     * @param username
     * @return
     */
    @LagouRequestMapping("/query01")
    @Security(value = {"zhangsan"})
    public String queryOfZhangSan(HttpServletRequest request, HttpServletResponse response, String username) {
        return demoService.get(username);
    }

    /**
     * URL: /demo/query02?username=lisi
     *
     * @param request
     * @param response
     * @param username
     * @return
     */
    @LagouRequestMapping("/query02")
    @Security(value = {"lisi", "lisi2"})
    public String queryOfLisi(HttpServletRequest request, HttpServletResponse response, String username) {
        return demoService.get(username);
    }

}
