package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 实现InvocationHandler 接口，重写invoke方法，实现代理实例上的方法调用
 * 代理类的调用处理程序，用于处理代理实例上的方法调用
 */
public class WorkHandler implements InvocationHandler {

    // 代理类中的真实对象
    private Object obj;

    /**
     * 这里注释记录一下程序运行时真实的 入参和出参
     *
     * @param obj {org.example.proxy.Teacher@497}
     */
    public WorkHandler(Object obj) {
        this.obj = obj;
    }

    /**
     * 这里注释记录一下程序运行时真实的 入参和出参
     *
     * @param proxy {com.sun.proxy.$Proxy0@551} org.example.proxy.Teacher@1d44bcfa
     * @param method {java.lang.reflect.Method@557} public abstract java.lang.String org.example.proxy.People.work()
     * @param args null
     *
     * @return Teaching
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在真实的对象执行之前我们可以添加自己的操作
        System.out.println("before invoke, do something");
        // 执行真实对象的方法
        // obj-{org.example.proxy.Teacher@497}
        Object invoke = method.invoke(obj, args);
        // 在真实的对象执行之后我们可以添加自己的操作
        System.out.println("after invoke, do something");
        return invoke;
    }

}
