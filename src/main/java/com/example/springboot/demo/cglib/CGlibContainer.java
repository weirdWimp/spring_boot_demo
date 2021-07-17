package com.example.springboot.demo.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author guo
 * @date 2021/7/17
 */
public class CGlibContainer {

    static enum AdviseType {
        BEFORE, AFTER
    }

    private static Class<?>[] aspectClazzArray = {LogMethodAspect.class};

    // Method: advise method
    private static Map<Class<?>, Map<AdviseType, List<Method>>> interceptMethodsMap = new HashMap<>();

    static {
        for (Class<?> clazz : aspectClazzArray) {
            try {
                CGlibAspect aspect = clazz.getAnnotation(CGlibAspect.class);
                Method before = clazz.getMethod("before", Object.class, Method.class, Object[].class);
                Method after = clazz.getMethod("after", Object.class, Method.class, Object[].class, Object.class);

                Class<?>[] cutPointClazzArray = aspect.value();
                for (Class<?> cutPointClazz : cutPointClazzArray) {
                    addAdviseMethods(cutPointClazz, AdviseType.BEFORE, before);
                    addAdviseMethods(cutPointClazz, AdviseType.AFTER, after);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addAdviseMethods(Class<?> clazz, AdviseType type, Method method) {
        if (method == null) {
            return;
        }
        Map<AdviseType, List<Method>> adviseTypeListMap = interceptMethodsMap.computeIfAbsent(clazz, k -> new HashMap<>());
        List<Method> methods = adviseTypeListMap.computeIfAbsent(type, t -> new ArrayList<>());
        methods.add(method);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        if (!interceptMethodsMap.containsKey(clazz)) {
            return ((T) clazz.newInstance());
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new AspectIntercept());
        return ((T) enhancer.create());
    }

    private static class AspectIntercept implements MethodInterceptor {

        /**
         * object 为代理对象 ！！
         *
         * @param object
         * @param method
         * @param args
         * @param methodProxy
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            // 前置通知
            List<Method> beforeAdviseMethods = getAdviseMethods(object.getClass().getSuperclass(), AdviseType.BEFORE);
            for (Method beforeAdviseMethod : beforeAdviseMethods) {
                // beforeAdviseMethod is a static advise method
                beforeAdviseMethod.invoke(null, object, method, args);
            }

            Object result = methodProxy.invokeSuper(object, args);

            // 后置通知
            List<Method> afterAdviseMethods = getAdviseMethods(object.getClass().getSuperclass(), AdviseType.AFTER);
            for (Method afterAdviseMethod : afterAdviseMethods) {
                // afterAdviseMethod is a static advise method
                afterAdviseMethod.invoke(null, object, method, args, result);
            }

            return result;
        }
    }

    private static List<Method> getAdviseMethods(Class<?> clazz, AdviseType adviseType) {
        Map<AdviseType, List<Method>> adviseTypeListMap = interceptMethodsMap.get(clazz);
        if (adviseType == null) {
            return Collections.emptyList();
        }

        List<Method> methods = adviseTypeListMap.get(adviseType);
        if (methods == null) {
            return Collections.emptyList();
        }
        return methods;
    }


}
