package cn.lonecloud.market.controller.portal;

import cn.lonecloud.market.base.BaseController;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.cts.CartEnum;
import cn.lonecloud.market.service.CartService;
import cn.lonecloud.market.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* @Title: CartController.java
* @Description: TODO
* @author lonecloud
* @date 2017/10/24 上午12:19
* @version V1.0
*/
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{
    @Autowired
    CartService cartService;

    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse<CartVO> add(Integer productId, Integer count){
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return cartService.addProductByPIdAndUId(productId,count,currentUser.getId());
    }

    /**
     * 删除product
     * @param productIds
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse<CartVO> deleteProduct(String productIds){
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return cartService.deleteProductByUserIdAndPids(productIds,currentUser.getId());
    }

    /**
     * 更新产品
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse<CartVO> updateProduct(Integer productId,Integer count){
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return cartService.updateProductCountByUserId(currentUser.getId(),productId,count);
    }

    /**
     * 遍历list
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse<CartVO> list(){
        return cartService.list(currentUser.getId());
    }
    /**
     * 遍历list
     * @return
     */
    @RequestMapping("/selectAll")
    @ResponseBody
    public ServerResponse<CartVO> selectAll(){
        return cartService.selectOrUnSelect(currentUser.getId(), CartEnum.SELECT,null);
    }

    /**
     * 全不选
     * @return
     */
    @RequestMapping("/unSelectAll")
    @ResponseBody
    public ServerResponse<CartVO> unSelectAll(){
        return cartService.selectOrUnSelect(currentUser.getId(), CartEnum.NOSELECT,null);
    }
    /**
     * 选一个
     * @return
     */
    @RequestMapping("/select/{productId}")
    @ResponseBody
    public ServerResponse<CartVO> select(@PathVariable("productId") Integer productId){
        return cartService.selectOrUnSelect(currentUser.getId(), CartEnum.NOSELECT,productId);
    }
    /**
     * 选一个
     * @return
     */
    @RequestMapping("/unSelect/{productId}")
    @ResponseBody
    public ServerResponse<CartVO> unSelect(@PathVariable("productId") Integer productId){
        return cartService.selectOrUnSelect(currentUser.getId(), CartEnum.NOSELECT,productId);
    }
    @RequestMapping("/getCartProductCount")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(){
        return cartService.getCartProductCount(currentUser.getId());
    }

}
