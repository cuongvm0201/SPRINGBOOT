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
public interface CartMapper {
    // get all cart
    final String GET_ALL_CART = "SELECT * FROM CART";
 
    @Select(GET_ALL_CART)
    @Results(value = { @Result(property = "id", column = "ID"),
            @Result(property = "total", column = "TOTAL"),
            @Result(property = "user_id", column = "USER_ID")})
    public List<Cart> getAll();
 
    // get cart by id_cart
    final String GET_CART_BY_ID = "SELECT * FROM CART WHERE ID = #{id}";
 
    @Select(GET_CART_BY_ID)
    public Cart getById(int id);

    // get cart by user_id
    final String GET_CART_BY_USERID = "SELECT *"
    +"\n"+"FROM USER INNER JOIN CART"
    +"\n"+"ON user.id = cart.user_id";
    @Select(GET_CART_BY_USERID)
    public Cart getByUserId(int user_id);

    // inert cart
    final String INSERT_CART = "INSERT INTO CART (ID, TOTAL, USER_ID) "
            + "VALUES (#{id}, #{total}, #{user_id})";
 
    @Update(INSERT_CART)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insert(Cart cart);


 
    // // update user
    // final String UPDATE_USER = "UPDATE USER SET FULLNAME = #{fullname}, MOBILE = #{mobile}, ADDRESS = #{address}, UPDATEAT = #{updateAt} "
    //         +  "WHERE ID = #{id}";
 
    // @Insert(UPDATE_USER)
    // public void update(User user);
 
    // delete cart by id
    final String DELETE_CART_BY_ID = "DELETE from CART WHERE ID = #{id}";
 
    @Delete(DELETE_CART_BY_ID)
    public void delete(int id);
    
}
