package cn.lonecloud.market.controller.portal;

import cn.lonecloud.market.base.BaseController;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.pojo.Shipping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 地址控制层
 * @author lonecloud
 * @version v1.0
 * @date 下午8:30 2017/10/25
 */
@Controller
@RequestMapping("/Shipping")
public class ShippingController extends BaseController {

    /**
     * 添加地址
     * @param shipping
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(Shipping shipping){
        return shippingService.add(currentUser.getId(),shipping);
    }

    /**
     * 删除地址
     * @param shippingId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(Integer shippingId){
        return shippingService.delete(currentUser.getId(),shippingId);
    }
    /**
     * 更新地址
     * @param shipping
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse update(Shipping shipping){
        return shippingService.update(currentUser.getId(),shipping);
    }

    /**
     * 单个查询
     * @param shippingId
     * @return
     */
    @RequestMapping("/selectOne/{shippingId}")
    @ResponseBody
    public ServerResponse selectOne(@PathVariable("shippingId")Integer shippingId){
        return shippingService.selectOne(currentUser.getId(),shippingId);
    }

    /**
     * list列表页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value ="pageSize",defaultValue = "10") Integer pageSize){
        return shippingService.list(currentUser.getId(),pageNum,pageSize);
    }
}
