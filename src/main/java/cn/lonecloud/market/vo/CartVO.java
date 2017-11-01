package cn.lonecloud.market.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lonecloud on 2017/10/3.
 */
public class CartVO {

    private List<CartProductVO> cartProductVOs;

    private BigDecimal cartProductPrice;
    /**
     * 是否已经全部勾选
     */
    private Boolean isAllChecked;
    /**
     * image服务器
     */
    private String imageHost;

    public List<CartProductVO> getCartProductVOs() {
        return cartProductVOs;
    }

    public void setCartProductVOs(List<CartProductVO> cartProductVOs) {
        this.cartProductVOs = cartProductVOs;
    }

    public BigDecimal getCartProductPrice() {
        return cartProductPrice;
    }

    public void setCartProductPrice(BigDecimal cartProductPrice) {
        this.cartProductPrice = cartProductPrice;
    }

    public Boolean getAllChecked() {
        return isAllChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        isAllChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
