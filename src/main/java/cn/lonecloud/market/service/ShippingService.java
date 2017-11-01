package cn.lonecloud.market.service;

import cn.lonecloud.market.base.BaseService;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.pojo.Shipping;
import com.github.pagehelper.PageInfo;

/**
 * @author lonecloud
 * @version v1.0
 * @date 下午8:31 2017/10/25
 */
public interface ShippingService extends BaseService {
    /**
     * 添加
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> delete(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse selectOne(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize);
}
