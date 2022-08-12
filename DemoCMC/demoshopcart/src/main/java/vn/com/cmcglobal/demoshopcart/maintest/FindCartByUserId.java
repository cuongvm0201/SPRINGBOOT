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
import vn.com.cmcglobal.demoshopcart.mapper.CartMapper;
import vn.com.cmcglobal.demoshopcart.mapper.model.Cart;
public class FindCartByUserId {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
 
        // create user & cart mapper
        UserMapper userMapper = session.getMapper(UserMapper.class);
        CartMapper cartMapper = session.getMapper(CartMapper.class);
        
        User user = userMapper.getByIdandCart(1);
        System.out.println("User info: "+ user);
    
        // close session
        session.close();
    }
}
