package org.example.proxy.byteCodeAnalysis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Super on 2023/9/12 13:53
 */
public class ProxyByteCode {

    public static void main(String[] args) {

        // 创建一个实现了 MyInterface 接口的代理类
        MyInterface myProxy = (MyInterface) Proxy.newProxyInstance(
                ProxyByteCode.class.getClassLoader(),
                new Class<?>[]{MyInterface.class},
                new MyInvocationHandler()
        );

        // 调用代理对象的方法
        myProxy.doSomething();
    }

    /**
     * 接口
     */
    public interface MyInterface {
        void doSomething();
    }

    /**
     * 代理调用程序
     */
    public static class MyInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            // 在这里根据需要进行处理
            System.out.println("MyInvocationHandler中执行一些自定义操作");
            return null;
        }
    }

}
