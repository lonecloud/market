package cn.lonecloud.market.service;

import cn.lonecloud.market.base.BaseService;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.pojo.Product;

import java.util.Set;

/**
 * Created by lonecloud on 2017/8/27.
 */
public interface ProductService extends BaseService<Product> {
    /**
     * 保存或更新产品
     * @param product
     * @return
     */
    ServerResponse saveOrUpdateProduct(Product product);

    /**
     * 更新产品状态
     * @param productId
     * @param status
     * @return
     */
    ServerResponse updateProductStatus(Integer productId, Integer status);

    /**
     * 获取产品
     * @param productId
     * @return
     */
    ServerResponse getProductDetail(Integer productId,boolean isAdmin);

    /**
     * 列表显示
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse list(Integer pageNum, Integer pageSize);


    ServerResponse searchProduct(Integer productId, String productName, Integer pageNum, Integer pageSize);

    ServerResponse getProductByKeyWordCategory(String keyword, Integer categoryId,String orderBy, Integer pageNum, Integer pageSize);
}
