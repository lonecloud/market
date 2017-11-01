package cn.lonecloud.market.controller.portal;

import cn.lonecloud.market.base.BaseController;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.pojo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lonecloud on 2017/8/30.
 * 用户产品控制层
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController<Product> {

    /**
     * 获取产品详情
     *
     * @param productId
     * @return
     */
    @GetMapping("/getProductDetail")
    @ResponseBody
    public ServerResponse getProductDetail(Integer productId) {
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return productService.getProductDetail(productId, false);
    }

    /**
     * 列出产品
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "categoryId", required = false,defaultValue = "1") Integer categoryId,
                               @RequestParam(value = "orderBy", required = false) String orderBy,

                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return productService.getProductByKeyWordCategory(keyword,categoryId,orderBy,pageNum, pageSize);
    }
}
