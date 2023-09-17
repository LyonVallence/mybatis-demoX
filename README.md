# mybatis-demoX

> 这是一个原生 `mybatis` 的demo，用来学习 `mybatis` 源码

#### 数据库创建

```sql
-- 创建用户表
CREATE TABLE user(
    id INT,
    name VARCHAR(10),
    age INT
);
```

```sql
-- 插入数据
INSERT INTO tb_user(id, name, age)
VALUES(1001, '张大头', 22),(1002, '李大头', 23);
```

```sql
-- 查询记录
SELECT * FROM user;
```