package com.lagou.edu.utils;

public class MainTest {
    public static void main(String[] args) throws ClassNotFoundException {

        Class<?> aClass = Class.forName("com.lagou.edu.dao.AccountDao");
        System.out.println(aClass.getSimpleName());
        System.out.println(aClass.isInterface());
        //System.out.println(aClass.is);


        /*Class<MainTest> aClass = MainTest.class;
        System.out.println("getName: "+ aClass.getName());
        System.out.println("getSimpleName: "+ aClass.getSimpleName());
        System.out.println("getPackageName: "+ aClass.getPackageName());
        System.out.println("getPackage: "+ aClass.getPackage());
        System.out.println("getTypeName: "+ aClass.getTypeName());
        System.out.println("getCanonicalName: "+ aClass.getCanonicalName());*/
    }
}
