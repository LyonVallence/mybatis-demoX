package org.example.proxy.upgrade;

import java.lang.reflect.Proxy;

/**
 * Created by Super on 2023/9/11 00:08
 */
public class TestUpgrade {

    public static void main(String[] args) {
        Student student = new Student();

        WorkHandlerUpgrade worker = new WorkHandlerUpgrade(student);

        Person proxy = (Person)Proxy.newProxyInstance(worker.getClass().getClassLoader(), student.getClass().getInterfaces(), worker);

        Person p = proxy.work("写代码").work("开会").work("上课");
        System.out.println("打印返回的对象：" + p.getClass());

        System.out.println("打印返回的内容：" + proxy.time());
        /* 执行结果如下:
                before 动态代理...
                代理对象类名：com.sun.proxy.$Proxy0
                真实对象类名：org.example.proxy.upgrade.Student
                将要执行的方法：work
                工作内容是：写代码
                after 动态代理... 返回代理对象

                before 动态代理...
                代理对象类名：com.sun.proxy.$Proxy0
                真实对象类名：org.example.proxy.upgrade.Student
                将要执行的方法：work
                工作内容是：开会
                after 动态代理... 返回代理对象

                before 动态代理...
                代理对象类名：com.sun.proxy.$Proxy0
                真实对象类名：org.example.proxy.upgrade.Student
                将要执行的方法：work
                工作内容是：上课
                after 动态代理... 返回代理对象

                打印返回的对象：class com.sun.proxy.$Proxy0

                before 动态代理...
                代理对象类名：com.sun.proxy.$Proxy0
                真实对象类名：org.example.proxy.upgrade.Student
                将要执行的方法：time
                after 动态代理... 返回方法执行的结果

                打印返回的内容：2023/9/10 23:58

            我们可以看到WorkHandlerUpgrade 代理调用处理程序打印proxy参数输出的结果是com.sun.proxy.$Proxy0，
            这也说明proxy参数是代理类的代理对象实例；
            Proxy类生成的代理对象可以调用work方法并且返回真实的代理对象，也可以通过反射来对真实的代理对象进行操作。
         */

    }

}
