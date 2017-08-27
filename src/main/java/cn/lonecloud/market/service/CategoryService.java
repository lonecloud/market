package cn.lonecloud.market.service;

import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.pojo.Category;

/**
 * Created by lonecloud on 2017/8/24.
 */
public interface CategoryService {
    /**
     * 查询某个id下一级的所有同级节点
     * @param categoryId
     * @return
     */
    ServerResponse getChildCategory(int categoryId);

    /**
     * 添加品类
     * @param category
     * @return
     */
    ServerResponse addCategory(Category category);

    ServerResponse setCategoryName(Integer categoryId,String categoryName);

    ServerResponse getDeepChildCategory(Integer categoryId);
}
