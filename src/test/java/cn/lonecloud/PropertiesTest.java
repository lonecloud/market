package cn.lonecloud;

import cn.lonecloud.market.utils.PropertiesUtil;
import org.junit.Test;

/**
 * Created by lonecloud on 2017/8/27.
 */
public class PropertiesTest {

    @Test
    public void proTest(){
        String property = PropertiesUtil.getProperty("ftp.server.prefix");
        System.out.println(property);
    }

}
