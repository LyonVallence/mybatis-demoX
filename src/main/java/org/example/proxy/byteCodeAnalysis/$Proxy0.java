package org.example.proxy.byteCodeAnalysis;

import org.example.dao.entity.User;
import org.example.dao.mapper.UserMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * MyBatis 生成的 代理类 示例
 * <p/>
 * 代理类 是由JVM自动生成 代理类的命名格式为 com.sun.proxy.$ProxyN
 * 其中 N 是一个数字，代表不同的代理类。
 * 这个数字是根据代理的顺序自动分配的。例如，$Proxy0、$Proxy1、$Proxy2 等。
 * <p/>
 * 有一些细节其实不太确定的，比如获取 Method 对象的方式，这里时我假想的
 * <p/>
 * Created by Super on 2023/9/17 20:18
 */
public class $Proxy0 implements UserMapper {
    private final InvocationHandler mapperProxy;

    public $Proxy0(InvocationHandler mapperProxy) {
        this.mapperProxy = mapperProxy;
    }

    @Override
    public User queryUserById(int id) {
        try {
            Method queryUserById = UserMapper.class.getDeclaredMethod("queryUserById", int.class);
            return (User)mapperProxy.invoke(this, queryUserById, new Object[]{id});
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> queryUserAll() {
        return null;
    }

    @Override
    public void insertUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUserById(int id) {

    }

}
