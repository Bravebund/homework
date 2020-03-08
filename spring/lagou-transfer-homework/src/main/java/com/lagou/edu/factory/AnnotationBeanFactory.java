package com.lagou.edu.factory;

import com.lagou.edu.stereotype.*;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工厂类，生产对象（扫描注解，使用反射技术）
 */
public class AnnotationBeanFactory {

    /**
     * 任务一：扫描包，通过反射技术实例化对象并且存储待用（map集合）
     * 任务二：对外提供获取实例对象的接口（根据id获取）
     */

    /**
     * Bean对象容器
     */
    private static final Map<String, Object> beanContainer = new HashMap<String, Object>();

    // 待扫描的包
    private static final String BASE_PACKAGE = "com.lagou.edu";

    // 全限定类名集合
    private static List<String> fullyQualifiedClassNames = new ArrayList<>();

    // 对象中已处理的属性，避免死循环
    private static List<String> completedObjectFields = new ArrayList<>();

    // 创建Bean
    static {
        try {
            // 1.扫描指定包下的类的全限定类名
            scanAnnoClass(BASE_PACKAGE);

            // 2.实例化加了自定义注解的组件
            doInstance();

            // 3.注入属性
            doSetterInjection();

            // 4.Bean的后置处理：自定义事务注解
            doBeanProcessor();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 实例化对象
     */
    private static void doInstance() {
        if (fullyQualifiedClassNames.size() == 0) return;
        try {
            // 处理自定义组件注解
            for (int i = 0; i < fullyQualifiedClassNames.size(); i++) {
                Class<?> aClass = Class.forName(fullyQualifiedClassNames.get(i));
                if (aClass.isAnnotationPresent(Service.class)
                        || aClass.isAnnotationPresent(Repository.class)
                        || aClass.isAnnotationPresent(Component.class)) {
                    String beanName = null;
                    if (aClass.isAnnotationPresent(Service.class)) {
                        beanName = aClass.getAnnotation(Service.class).value();
                    } else if (aClass.isAnnotationPresent(Repository.class)) {
                        beanName = aClass.getAnnotation(Repository.class).value();
                    } else if (aClass.isAnnotationPresent(Component.class)) {
                        beanName = aClass.getAnnotation(Component.class).value();
                    }
                    // 处理注解放在接口上
                    /*if (aClass.isInterface()) {
                        // 利用反射找到其实现类，并实例化，并记录状态
                    }*/
                    Object newInstance = aClass.newInstance();
                    if ("".equals(beanName.trim())) {
                        beanName = firstFoweCase(aClass.getSimpleName());
                    }
                    beanContainer.put(beanName, newInstance);

                    // 支持接口多态的方式导入
                    Class<?>[] interfaces = aClass.getInterfaces();
                    if (interfaces != null && interfaces.length > 0) {
                        for (int j = 0; j < interfaces.length; j++) {
                            Class<?> anInterface = interfaces[j];
                            // 以接口的全限定类名作为id放入
                            beanContainer.put(anInterface.getName(), newInstance);
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理自动注入注解，Setter方式注入
     */
    private static void doSetterInjection() {
        if (beanContainer.isEmpty()) return;

        for (Map.Entry<String, Object> entry : beanContainer.entrySet()) {
            try {
                doObjectInjection(entry.getValue());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理已实例化对象的属性
     *
     * @param object 已实例化对象
     * @throws IllegalAccessException
     */
    private static void doObjectInjection(Object object) throws IllegalAccessException {
        // 获取对象的属性
        Field[] declaredFields = object.getClass().getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) return;

        // 处理自定义Autowired注解
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            if (!declaredField.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            // 当前类的当前属性的ID
            String filedId = object.getClass().getName() + "." + declaredField.getName();
            if (completedObjectFields.contains(filedId)) {
                continue;
            }

            // 从容器中获取当前处理的对象：优先根据接口类型获取
            Object dependObject = beanContainer.get(declaredField.getType().getName());
            if (dependObject == null) {
                dependObject = beanContainer.get(firstFoweCase(declaredField.getType().getSimpleName()));
            }

            // 记录当前对象的当前属性已设置，避免死循环
            completedObjectFields.add(filedId);

            // 循环依赖
            doObjectInjection(dependObject);

            // 处理完后，把依赖的属性值设置好
            declaredField.setAccessible(true);
            declaredField.set(object, dependObject);
        }
    }

    /**
     * Bean的后置处理器：事务增强
     */
    private static void doBeanProcessor() {
        ProxyFactory proxyFactory = (ProxyFactory) beanContainer.get("proxyFactory");
        for (Map.Entry<String, Object> entry : beanContainer.entrySet()) {
            String beanName = entry.getKey();
            Object newInstance = entry.getValue();
            Class<?> aClass = entry.getValue().getClass();
            // 处理自定义注解Transactional，进行事务增强
            if (aClass.isAnnotationPresent(Transactional.class)) {
                // 若有实现接口，使用JDK动态代理；否则使用CGLIB动态代理
                Class<?>[] interfaces = aClass.getInterfaces();
                if (interfaces != null && interfaces.length > 0) {
                    beanContainer.put(beanName, proxyFactory.getJdkProxy(newInstance));
                } else {
                    beanContainer.put(beanName, proxyFactory.getCglibProxy(newInstance));
                }
            }
        }

    }


    // 任务二：对外提供获取实例对象的接口（根据id获取）

    /**
     * 根据注解名获取实例
     *
     * @param id 注解的ID，即value的值，或
     * @return 对应实例
     */
    public static Object getBean(String id) {
        if (id == null) return null;
        return beanContainer.get(id);
    }

    /**
     * 根据注解名获取指定类型的实例
     *
     * @param beanName  bean名称，注解指定的value值
     * @param beanClass bean类型
     * @return 指定类型的实例
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return beanClass.cast(getBean(beanName));
    }


    /**
     * 扫描指定包下的类的全限定类名，并缓存
     *
     * @param scanPackage 待扫描的包
     */
    private static void scanAnnoClass(String scanPackage) {
        String scanPackagePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                + scanPackage.replaceAll("\\.", "/");
        File pack = new File(scanPackagePath);
        File[] files = pack.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                scanAnnoClass(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = scanPackage + "." + file.getName().replaceAll(".class", "");
                fullyQualifiedClassNames.add(className);
            }
        }
    }

    /**
     * 将字符串首字母小写
     *
     * @param str 字符串
     * @return
     */
    private static String firstFoweCase(String str) {
        char[] chars = str.toCharArray();
        if ('A' <= chars[0] && chars[0] <= 'Z') {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }


}
