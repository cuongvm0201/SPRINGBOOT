package vn.com.cmcglobal.demoshopcart.maintest;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import vn.com.cmcglobal.demoshopcart.mapper.UserMapper;
import vn.com.cmcglobal.demoshopcart.mapper.model.User;

public class SelectAllTest {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
 
        // create student mapper
        UserMapper userMapper = session.getMapper(UserMapper.class);
 
        // show list student
        List<User> listUsers = userMapper.getAll();
        for (User user : listUsers) {
            System.out.println(user.toString());
        }
         
        // close session
        session.close();
    }
}
