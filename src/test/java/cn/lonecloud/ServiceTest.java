package cn.lonecloud;

import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.service.CategoryService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.Bag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lonecloud on 2017/8/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:applicationContext.xml" })
public class ServiceTest {


    @Autowired
    CategoryService categoryService;

    @Test
    public void test(){
        ServerResponse category = categoryService.getDeepChildCategory(1);
        System.out.println(JSON.toJSONString(category));
    }


}
