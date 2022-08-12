package vn.com.cmcglobal.demoshopcart.mapper;
import java.util.List;
 
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import vn.com.cmcglobal.demoshopcart.mapper.model.Product;
public interface ProductMapper {
    // get all product
    final String GET_ALL_PRODUCT = "SELECT * FROM PRODUCT";
 
    @Select(GET_ALL_PRODUCT)
    @Results(value = { @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "price", column = "PRICE"),
            @Result(property = "amount", column = "AMOUNT"),
            @Result(property = "category", column = "CATEGORY"),
            @Result(property = "creatAt", column = "CREATAT"),
            @Result(property = "updateAt", column = "UPDATEAT")})
    public List<Product> getAll();
 
    // get product by id
    final String GET_PRODUCT_BY_ID = "SELECT * FROM PRODUCT WHERE ID = #{id}";
 
    @Select(GET_PRODUCT_BY_ID)
    public Product getById(int id);
 
    // inert product
    final String INSERT_PRODUCT = "INSERT INTO PRODUCT (ID, NAME, PRICE,AMOUNT,CATEGORY,CREATAT,UPDATEAT) "
            + "VALUES (#{id}, #{name}, #{price}, #{amount}, #{category}, #{creatAt}, #{updateAt})";
 
    @Update(INSERT_PRODUCT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insert(Product product);


 
    // update user
    final String UPDATE_PRODUCT = "UPDATE PRODUCT SET NAME = #{name}, PRICE = #{price}, AMOUNT = #{amount}, UPDATEAT = #{updateAt} "
            +  "WHERE ID = #{id}";
 
    @Insert(UPDATE_PRODUCT)
    public void update(Product product);
 
    // delete product by id
    final String DELETE_PRODUCT_BY_ID = "DELETE from PRODUCT WHERE ID = #{id}";
 
    @Delete(DELETE_PRODUCT_BY_ID)
    public void delete(int id);
}
