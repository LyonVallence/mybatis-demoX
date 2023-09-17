package org.example.proxy.upgrade;

/**
 * Created by Super on 2023/9/10 23:58
 */
public class Student implements Person{
    @Override
    public Person work(String jobContent) {
        System.out.println("工作内容是：" + jobContent);
        return this;
    }

    @Override
    public String time() {
        return "2023/9/10 23:58";
    }

}
