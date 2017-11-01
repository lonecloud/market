package cn.lonecloud.market.dao;

import cn.lonecloud.market.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    /**
     * 删除地址
     * @param userId
     * @param shippingId
     * @return
     */
    int deleteByuserIdAndShippingId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    int updateByuserIdAndShipping(@Param("userId") Integer userId, @Param("shipping") Shipping shipping);

    Shipping selectByUserIdAndSId(@Param("userId")Integer userId, @Param("shippingId") Integer shippingId);

    List<Shipping> selectByUserId(Integer userId);
}