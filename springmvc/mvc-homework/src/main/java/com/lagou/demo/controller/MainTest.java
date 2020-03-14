package com.lagou.demo.controller;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class MainTest {
    public static void main(String[] args) {
        String[] a = {"a1", "a2"};
        System.out.println(a);
        String[] b = {"b1"};
        String securityStrClass = StringUtils.join(a, ",");
        System.out.println(securityStrClass);
    }
}
