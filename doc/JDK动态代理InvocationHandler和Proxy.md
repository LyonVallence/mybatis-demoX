### `JDK` 动态代理 `Proxy` 和 `InvocationHandler`

[TOC]

`java` 的动态代理机制中有两个重要的类和接口（`Proxy` 类和 `InvocationHandler`接口），他们是实现动态代理的核心。

#### Proxy 类

`java.lang.reflect.Proxy` 类 核心功能是用来创建 **指定接口** 的代理对象实例。它提供了很多方法，但是最常用的是 `newProxyInstance` 方法。

```java
/**
 * Returns an instance of a proxy class for the specified interfaces
 * that dispatches method invocations to the specified invocation
 * handler.
 *
 * @param   loader the class loader to define the proxy class
 * @param   interfaces the list of interfaces for the proxy class
 *          to implement
 * @param   h the invocation handler to dispatch method invocations to
 * @return  a proxy instance with the specified invocation handler of a
 *          proxy class that is defined by the specified class loader
 *          and that implements the specified interfaces
 */
public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, 																																			InvocationHandler h)
```

这个方法的作用就是创建一个代理类对象，它接收三个参数，我们来看下几个参数的含义：

> - `loader`：一个 `classloader` 对象，定义了由哪个 `classloader` 对生成的代理类进行加载
> - `interfaces`：一个 `interface` 对象数组，表示要给代理对象提供一组什么样的接口，如果我们提供了这样一个接口对象数组，那么也就是 **声明了代理类实现了这些接口**，代理类就可以调用接口中声明的所有方法。所以，必须是接口，不能是基类。
> - `h`：一个 `InvocationHandler` 对象，表示的是当动态代理对象调用方法的时候会关联到哪一个`InvocationHandler` 对象上，并最终由其调用。

除了上述的方法，`Proxy` 类中还有如下常见方法：

> * `getInvocationHandler`：返回指定代理实例的调用处理程序
> * `getProxyClass`：获取 给定类加载器`(loader)`和接口数组`(interfaces)` 的代理类的 `java.lang.Class` 对象
> * isProxyClass：判断是否是代理类。当且仅当使用 `getProxyClass` 方法或 `newProxyInstance` 方法将指定的类动态生成为代理类时，才返回true



#### InvocationHandler 接口

`InvocationHandler` 是 `proxy` 代理实例的 **调用处理程序** 需要实现的一个接口，每一个 `proxy` 代理实例都有一个关联的调用处理程序；**在代理实例调用方法时，会被分派到调用处理程序的 `invoke` 方法**。

```java
/**
 * {@code InvocationHandler} is the interface implemented by
 * the <i>invocation handler</i> of a proxy instance.
 *
 * <p>Each proxy instance has an associated invocation handler.
 * When a method is invoked on a proxy instance, the method
 * invocation is encoded and dispatched to the {@code invoke}
 * method of its invocation handler.
 */
public interface InvocationHandler {

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     */
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
  
}
```

每一个动态代理类的调用处理程序都必须实现 `InvocationHandler` 接口，并且每个代理类的实例都关联到了实现该接口的动态代理类调用处理程序中，当我们通过动态代理对象调用一个方法时候，这个方法的调用就会被转发到实现`InvocationHandler` 接口类的invoke方法来调用，invoke方法参数有：

> * `proxy`：调用该方法的 **代理类代理的真实对象** 的实例，即代理实例
> * method：我们所要调用某个对象真实方法的 `Method` 对象
> * args：指代代理对象方法传递的参数

**重点介绍一下第一个参数 `Proxy`**

* `proxy` 代表什么意思

`proxy` 是真实对象的代理对象实例。`invoke` 方法可以返回调用代理对象方法的返回结果，也可以返回真实对象的代理对象。

* `proxy` 参数怎么用及什么时候用

`proxy` 参数是 `invoke` 方法的第一个参数，通常情况下我们都是返回真实对象方法的返回结果，但是我们也可以将 `proxy` 返回，`proxy` 是真实对象的代理对象，我们可以通过这个返回该代理对象后对真实对象做各种各样的操作。

* `proxy` 参数运行时的类型是什么

代理对象实例： `com.sun.proxy.$Proxy0`

* `invoke` 方法返回 `proxy`时为什么不用 `this` 替代

因为 `this` 代表的是 `InvocationHandler` 接口实现类本身，并不是真实的代理对象。



#### Demo

##### 新建一个接口

```java
public interface People {

    public String work();

}
```

##### 新建一个接口的实现类

```java
public class Teacher implements People{

    @Override
    public String work() {
        System.out.println("老师教书育人!");
        return "Teaching";
    }

}
```

##### 新建一个 *代理类的调用处理程序* 类

下面代码注释中记录了程序运行中真实的对象地址，用来理解动态代理的过程，例如 `obj {org.example.proxy.Teacher@497}` ：代表着测试类中传递了一个地址为{org.example.proxy.Teacher@497}` 的 `Teacher`对象给` `WorkHandler` 的有参构造函数，用于创建一个 `WorkHandler` 的对象。

```java
/**
 * 实现InvocationHandler 接口，重写invoke方法，实现代理实例上的方法调用
 * 代理类的调用处理程序，用于处理代理实例上的方法调用
 */
public class WorkHandler implements InvocationHandler {

    // 代理类代理的真实对象
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
```

##### 测试方法

```java
public class Test {

    public static void main(String[] args) {
        // 要代理的真实对象
        // 这里注释记录一下程序运行时真实的对象地址 teacher-{org.example.proxy.Teacher@497}
        Teacher teacher = new Teacher();

        // 代理对象的调用处理程序，需要将 被代理的真实对象 作为 调用处理程序的构造函数的参数，最终代理对象的调用处理程序会调用真实对象的方法
        // workHandler-{org.example.proxy.WorkHandler@502}
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
    }
    
}
```

运行结果

```shell
before invoke, do something
老师教书育人！
after invoke, do something
Teaching
```



#### 错误记录 `ClassCastException`

`java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to org.example.proxy.Student`

新建一个普通类  --`Student` 类

```java
public class Student {

    public void study(){
        System.out.println("拿出课本和笔记本 --> 自习");
    }

}
```

测试类

```java
public class Test {

    public static void main(String[] args) {
        Student student = new Student();
        WorkHandler study = new WorkHandler(student);
        
      Student studentProxy = (Student)Proxy.newProxyInstance(study.getClass().getClassLoader(), student.getClass().getInterfaces(), study);
        
      	studentProxy.study();
    }

}
```

运行结果

```shell
Exception in thread "main" java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to org.example.proxy.Student
```

原因是 `Student` 类是一个普通类，`JDK` 动态代理的设计就是 用传入的接口创建一个新类。如果传入的是一个普通类，那么被代理的类没有去实现接口而是继承了一个基类，此时就出现了`ClassCastException` 。

