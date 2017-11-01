package cn.lonecloud.market.service;

import cn.lonecloud.market.base.BaseService;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.cts.CartEnum;
import cn.lonecloud.market.vo.CartVO;

/**
 * Created by lonecloud on 2017/10/3.
 */
public interface CartService extends BaseService {


    /**
     * 添加购物车商品
     * @param productId
     * @param count
     * @param userId
     * @return
     */
    ServerResponse<CartVO> addProductByPIdAndUId(Integer productId, Integer count, Integer userId);

    /**
     * 删除商品
     * @param productIds
     * @param userId
     * @return
     */
    ServerResponse<CartVO> deleteProductByUserIdAndPids(String productIds, Integer userId);

    /**
     * 更新购物车商品数量
     * @param id
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVO> updateProductCountByUserId(Integer id, Integer productId, Integer count);

    /**
     * 查询购物车
     * @param id
     * @return
     */
    ServerResponse<CartVO> list(Integer id);

    /**
     * 选中全部
     * @param userId
     * @param selectEnum
     * @param  productId
     * @return
     */
    ServerResponse<CartVO> selectOrUnSelect(Integer userId, CartEnum selectEnum,Integer productId);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
