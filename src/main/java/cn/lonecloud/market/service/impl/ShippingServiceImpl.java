package cn.lonecloud.market.service.impl;

import cn.lonecloud.market.base.BaseServiceImpl;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.pojo.Shipping;
import cn.lonecloud.market.service.ShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author lonecloud
 * @version v1.0
 * @date 下午8:31 2017/10/25
 */
@Service
public class ShippingServiceImpl extends BaseServiceImpl implements ShippingService{


    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setId(userId);
        int i = shippingMapper.insert(shipping);
        if (i>0){
            Map<String ,Object> map=Maps.newHashMap();
            map.put("shippingId",shipping.getId());
            return ServerResponse.success("新建地址成功",map);
        }
        return ServerResponse.error("新建地址失败");
    }

    @Override
    public ServerResponse<String> delete(Integer userId, Integer shippingId) {
        int i = shippingMapper.deleteByuserIdAndShippingId(userId, shippingId);
        if (i>0){
            ServerResponse.success("删除地址成功");
        }
        return ServerResponse.error("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        int i = shippingMapper.updateByuserIdAndShipping(userId, shipping);
        if (i>0){
            ServerResponse.success("更新地址成功");
        }
        return ServerResponse.error("更新地址失败");
    }

    @Override
    public ServerResponse selectOne(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByUserIdAndSId(userId, shippingId);
        if (shipping==null){
            return ServerResponse.error("地址不存在！");
        }
        return ServerResponse.success(shipping);
    }

    @Override
    public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping>shippingList=shippingMapper.selectByUserId(userId);
        PageInfo pageInfo=new PageInfo(shippingList);

        return ServerResponse.success(pageInfo);
    }

}
