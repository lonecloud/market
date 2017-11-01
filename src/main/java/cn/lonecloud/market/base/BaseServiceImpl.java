package cn.lonecloud.market.base;

import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lonecloud on 2017/8/27.
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected CategoryMapper categoryMapper;

    @Autowired
    protected ProductMapper productMapper;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected CartMapper cartMapper;

    @Autowired
    protected ShippingMapper shippingMapper;


}
