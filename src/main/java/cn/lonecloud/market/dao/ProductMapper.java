package cn.lonecloud.market.dao;

import cn.lonecloud.market.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> list();


    List<Product> selectByProductIdOrProductName(@Param("productId") Integer productId, @Param("productName") String productName);

    List<Product> selectByProductNameAndCategoryIds(@Param("keyword")String keyword,@Param("categoryIds") List<Integer> categoryIds);
}