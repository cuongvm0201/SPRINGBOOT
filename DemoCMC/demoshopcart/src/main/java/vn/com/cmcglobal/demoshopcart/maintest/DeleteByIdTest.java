package vn.com.cmcglobal.demoshopcart.maintest;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import vn.com.cmcglobal.demoshopcart.mapper.CartMapper;
import vn.com.cmcglobal.demoshopcart.mapper.UserMapper;
import vn.com.cmcglobal.demoshopcart.mapper.model.User;
public class DeleteByIdTest {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
 
        // create user mapper
        UserMapper userMapper = session.getMapper(UserMapper.class);
        
        // create cart mapper
        CartMapper cartMapper = session.getMapper(CartMapper.class);

        
        // delete cart
        cartMapper.delete(101);
        // delete user
        userMapper.delete(1);
        session.commit();
        System.out.println("delete successfully");
 
        // close session
        session.close();
    }
}
