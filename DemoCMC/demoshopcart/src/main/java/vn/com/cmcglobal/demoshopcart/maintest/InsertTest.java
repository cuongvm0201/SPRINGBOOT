package vn.com.cmcglobal.demoshopcart.maintest;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import vn.com.cmcglobal.demoshopcart.mapper.CartMapper;
import vn.com.cmcglobal.demoshopcart.mapper.UserMapper;
import vn.com.cmcglobal.demoshopcart.mapper.model.Cart;
import vn.com.cmcglobal.demoshopcart.mapper.model.Roles;
import vn.com.cmcglobal.demoshopcart.mapper.model.User;
public class InsertTest {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();


 
        // create user mapper
        UserMapper userMapper = session.getMapper(UserMapper.class);

        // create cart mapper
        CartMapper cartMapper = session.getMapper(CartMapper.class);
 
        // insert user
        User user = 
        new User(1,
        "cuong123",
        "123456",
        "Vu Manh Cuong",
        "0945940246",
        "test@gmail.com",
        "112 Thanh Nhan",
        Roles.MEMBER,
        LocalDateTime.of(2022, 8, 7, 10, 10, 10),
         null);
        userMapper.insert(user);
        // insert cart
        Cart cart = new Cart(101,0,1);
        cartMapper.insert(cart);
        session.commit();
        System.out.println("insert user & cart sucessfully");
 
        // close session
        session.close();
    }
}
