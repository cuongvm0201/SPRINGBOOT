package vn.com.cmcglobal.demoshopcart.mapper;
import java.util.List;
 
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import vn.com.cmcglobal.demoshopcart.mapper.model.Cart;
import vn.com.cmcglobal.demoshopcart.mapper.model.User;


public interface UserMapper {
     
    // get all user
     final String GET_ALL_USER = "SELECT * FROM USER";
 
     @Select(GET_ALL_USER)
     @Results(value = { @Result(property = "id", column = "ID"),
             @Result(property = "username", column = "USERNAME"),
             @Result(property = "password", column = "PASSWORD"),
             @Result(property = "fullname", column = "FULLNAME"),
             @Result(property = "mobile", column = "MOBILE"),
             @Result(property = "email", column = "EMAIL"),
             @Result(property = "address", column = "ADDRESS"),
             @Result(property = "role", column = "ROLE"),
             @Result(property = "creatAt", column = "CREATAT"),
             @Result(property = "updateAt", column = "UPDATEAT")})
     public List<User> getAll();
     
     // get user by id
     final String GET_USER_BY_ID = "SELECT * FROM USER WHERE ID = #{id}";
  
     @Select(GET_USER_BY_ID)
     public User getById(int id);

     // get user by id show cart
     final String GET_USER_BY_ID_CART = "SELECT cart.*, user.*"
     +"\n"+"FROM USER INNER JOIN CART"
     +"\n"+"ON user.id = cart.user_id";
     @Select(GET_USER_BY_ID_CART)
     public User getByIdandCart(int id);
  
     // inert user
     final String INSERT_USER = "INSERT INTO USER (ID, USERNAME, PASSWORD,FULLNAME,MOBILE, EMAIL,ADDRESS,ROLE,CREATAT,UPDATEAT) "
             + "VALUES (#{id}, #{username}, #{password},#{fullname}, #{mobile}, #{email},#{address},#{role},#{creatAt},#{updateAt})";
  
     @Update(INSERT_USER)
     @Options(useGeneratedKeys = true, keyProperty = "id")
     public void insert(User user);


  
     // update user
     final String UPDATE_USER = "UPDATE USER SET FULLNAME = #{fullname}, MOBILE = #{mobile}, ADDRESS = #{address}, UPDATEAT = #{updateAt} "
             +  "WHERE ID = #{id}";
  
     @Insert(UPDATE_USER)
     public void update(User user);
  
     // delete user by id
     final String DELETE_USER_BY_ID = "DELETE from USER WHERE ID = #{id}";
  
     @Delete(DELETE_USER_BY_ID)
     public void delete(int id);
}
