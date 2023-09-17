package org.example.proxy;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        // 要代理的真实对象
        // 这里注释记录一下程序运行时真实的对象地址 teacher-{org.example.proxy.Teacher@497}
        Teacher teacher = new Teacher();

        // 代理对象的调用处理程序，需要将 被代理的真实对象 作为 调用处理程序的构造函数的参数，最终代理对象的调用处理程序会调用真实对象的方法
        // workHandler-{class org.example.proxy.WorkHandler@502}
        WorkHandler workHandler = new WorkHandler(teacher);

        /*
          通过 Proxy 类的 newProxyInstance 方法 为 teacher 创建代理对象
          介绍一下三个参数:
            1. workHandler.getClass().getClassLoader() 表示使用 workHandler 的类加载器来加载我们的代理对象
            2. teacher.getClass().getInterfaces() 表示为代理对象提供真实对象实现的接口
            3. workHandler 用来将代理对象关联到上面的InvocationHandler对象上
         */
        // proxy-{com.sun.proxy.$Proxy0@551} org.example.proxy.Teacher@1d44bcfa
        People proxy = (People)Proxy.newProxyInstance(workHandler.getClass().getClassLoader(), teacher.getClass().getInterfaces(), workHandler);

        System.out.println(proxy.work());


        /*
            以下程序会报错：
            java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to org.example.proxy.Student
            原因是 Student 类不是一个接口
            被代理的类没有继承接口而是继承了一个基类(Student)
         */
//        Student student = new Student();
//        WorkHandler study = new WorkHandler(student);
//        Student studentProxy = (Student)Proxy.newProxyInstance(study.getClass().getClassLoader(), student.getClass().getInterfaces(), study);
//        studentProxy.study();

    }

}
