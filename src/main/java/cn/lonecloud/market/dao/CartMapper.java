package cn.lonecloud.market.dao;

import cn.lonecloud.market.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByPIdAndUid(@Param("productId") Integer productId, @Param("userId") Integer userId);

    List<Cart> selectByUserId(Integer userId);

    /**
     * 查找购物车总购物车数量
     * @param userId
     * @return
     */
    int selectCheckCartProductStatusByUserId(Integer userId);

    /**
     * 删除某个产品
     * @param userId
     * @param productIdList
     * @return
     */
    int deleteByUserIdAndProductIds(@Param("userId")Integer userId, @Param("productIdList") List<String> productIdList);

    /**
     * 根据用户id更新所有商品状态
     * @param userId
     * @param checked
     * @return
     */
    int updateProductChecked(@Param("userId") Integer userId, @Param("checked") int checked,@Param("productId") Integer productId);

    int getCartProductCount(Integer userId);
}