package cn.lonecloud.market.service.impl;

import cn.lonecloud.market.base.BaseServiceImpl;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.cts.CartEnum;
import cn.lonecloud.market.cts.Constants;
import cn.lonecloud.market.cts.ServerResponseCode;
import cn.lonecloud.market.pojo.Cart;
import cn.lonecloud.market.pojo.Product;
import cn.lonecloud.market.service.CartService;
import cn.lonecloud.market.utils.BigDecimalUtil;
import cn.lonecloud.market.vo.CartProductVO;
import cn.lonecloud.market.vo.CartVO;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lonecloud on 2017/10/3.
 */
@Service
public class CartServiceImpl extends BaseServiceImpl implements CartService {


    @Override
    public ServerResponse<CartVO> addProductByPIdAndUId(Integer productId, Integer count, Integer userId) {
        if (productId == null || count == null) {
            return ServerResponse.error(ServerResponseCode.ILLEGAL_ARGUMENT);
        }
        //先查询是否有该产品
        Cart cart = cartMapper.selectCartByPIdAndUid(productId, userId);
        if (cart != null) {
            //没发现有该产品则新增该项目
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cart.setChecked(CartEnum.SELECT.getValue());
            cart.setProductId(productId);
            cart.setUserId(userId);
            int insert = cartMapper.insert(cartItem);
            if (insert > 0) {
                return ServerResponse.success("添加商品成功");
            } else {
                return ServerResponse.error("添加商品失败");
            }
        } else {
            //更新该产品数量
            cart.setQuantity(cart.getQuantity() > 0 ? count + cart.getQuantity() : count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        //进行数据返回
        return list(userId);
    }

    @Override
    public ServerResponse<CartVO> deleteProductByUserIdAndPids(String productIds, Integer userId) {
        if (userId == null) {
            return ServerResponse.error(ServerResponseCode.ILLEGAL_ARGUMENT);
        }
        //判断是否是空
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productIdList)){
            return ServerResponse.error(ServerResponseCode.ILLEGAL_ARGUMENT);
        }
        int count = cartMapper.deleteByUserIdAndProductIds(userId, productIdList);
        if (count > 0) {
            return list(userId);
        }
        return ServerResponse.error("删除失败");
    }

    @Override
    public ServerResponse<CartVO> updateProductCountByUserId(Integer userId, Integer productId, Integer count) {
        //查询产品
        Cart cart = cartMapper.selectCartByPIdAndUid(productId, userId);
        if (cart != null){
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    @Override
    public ServerResponse<CartVO> list(Integer userId) {
        return ServerResponse.success(getCartLimit(userId));
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        if (userId==null){
            return ServerResponse.success(0);
        }
        return ServerResponse.success(cartMapper.getCartProductCount(userId));
    }

    @Override
    public ServerResponse<CartVO> selectOrUnSelect(Integer userId, CartEnum selectEnum,Integer productId) {
        cartMapper.updateProductChecked(userId,selectEnum.getValue(),productId);
        return list(userId);
    }


    private CartVO getCartLimit(Integer userId) {
        CartVO cartVO = new CartVO();
        List<Cart> cartList = cartMapper.selectByUserId(userId);

        List<CartProductVO> cartProductVOList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (CollectionUtils.isEmpty(cartList)) {
            CartProductVO cartProductVo = null;
            for (Cart cartItem : cartList) {
                cartProductVo = new CartProductVO();
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());
                cartProductVo.setId(cartItem.getId());
                //查询产品信息
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    //设置属性值
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存设置其添加信息
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuality(Constants.LimitCartCount.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuality(Constants.LimitCartCount.LIMIT_NUM_FAIL);
                        //购物车更新库存
                        Cart cartUpdate = new Cart();
                        cartUpdate.setId(cartItem.getId());
                        cartUpdate.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartUpdate);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.multiply(cartProductVo.getProductPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                //判断是否被勾选
                if (cartItem.getChecked() == CartEnum.SELECT.getValue()) {
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVOList.add(cartProductVo);
            }
        }
        cartVO.setCartProductPrice(cartTotalPrice);
        cartVO.setCartProductVOs(cartProductVOList);
        cartVO.setAllChecked(getAllCheckStatus(userId));
        return cartVO;
    }

    private boolean getAllCheckStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        int count = cartMapper.selectCheckCartProductStatusByUserId(userId);
        return count == 0;
    }
}
