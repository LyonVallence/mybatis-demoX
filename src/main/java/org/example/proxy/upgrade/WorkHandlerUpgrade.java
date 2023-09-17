package org.example.proxy.upgrade;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Super on 2023/9/11 00:00
 */
public class WorkHandlerUpgrade implements InvocationHandler {

    Object obj;

    public WorkHandlerUpgrade(Object obj){
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before 动态代理...");
        System.out.println("代理对象类名：" + proxy.getClass().getName());
        System.out.println("真实对象类名：" + this.obj.getClass().getName());

        String methodName = method.getName();
        System.out.println("将要执行的方法：" + methodName);
        if("work".equals(methodName)) {
            method.invoke(this.obj, args);
            System.out.println("after 动态代理... 返回代理对象");
            return proxy;
        } else {
            Object invoke = method.invoke(this.obj, args);
            System.out.println("after 动态代理... 返回方法执行的结果");
            return invoke;
        }

    }
}
