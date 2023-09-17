package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.dao.entity.User;
import org.example.dao.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * Created by Super on 2023/9/17 23:00
 */
public class AppMain {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try(SqlSession session = sqlSessionFactory.openSession()){
            // 这里的 mapper 是一个代理对象 org.apache.ibatis.binding.MapperProxy@35d176f7
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user1 = mapper.queryUserById(1001);
            System.out.println("第一次查询结果:" + user1);

            System.out.println("+++++++++++++++++++++ 我是分割线 +++++++++++++++++++++");
            User user2 = mapper.queryUserById(1001);
            System.out.println("第二次查询结果:" + user2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 通过 xml 中 sql 的全限定名(org.example.dao.mapper.UserMapper.queryUserById) 来执行sql
     */
    private static void fullyQualifiedNameTest() throws IOException{
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try(SqlSession session = sqlSessionFactory.openSession()){

            User user1 = session.selectOne("org.example.dao.mapper.UserMapper.queryUserById", 1001);
            System.out.println("第一次查询结果:" + user1);

            System.out.println("+++++++++++++++++++++ 我是分割线 +++++++++++++++++++++");
            User user2 = session.selectOne("org.example.dao.mapper.UserMapper.queryUserById", 1001);
            System.out.println("第二次查询结果:" + user2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 二级缓存 需要在 UserMapper.xml 中配置上 <cache/> 节点
     *
     * @param sqlSessionFactory {@link SqlSessionFactory}
     */
    private static void SecondCacheTest(SqlSessionFactory sqlSessionFactory) {
        try{
            SqlSession sqlSession1 = sqlSessionFactory.openSession();
            User user1 = sqlSession1.selectOne("org.example.dao.mapper.UserMapper.queryUserById", 1001);
            System.out.println("第一次查询结果:" + user1);
            // 只有执行sqlSession.commit 或 sqlSession.close方法，一级缓存中的内容才会刷新到二级缓存中
            sqlSession1.close();

            SqlSession sqlSession2 = sqlSessionFactory.openSession();
            User user2 = sqlSession2.selectOne("org.example.dao.mapper.UserMapper.queryUserById", 1001);
            System.out.println("第二次查询结果:" + user2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 标准 JDBC 实现查询分析的基本步骤
     */
    private static void jdbcTest(){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立并获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ry_vue", "root", "root");
            // 设置SQL语句的传入参数
            String sql = "select * from tb_user where id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "1001");
            // 执行SQL语句并获得查询结果
            rs  = pst.executeQuery();
            // 对查询结果进行转换处理并将处理结果返回
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                System.out.println(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 释放相关资源 依次关闭ResultSet / Statement / Connection
            try {
                if (rs != null){
                    rs.close();
                }
                if (pst != null){
                    pst.close();
                }
                if (conn!=null){
                    conn.close();
                }
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

}
