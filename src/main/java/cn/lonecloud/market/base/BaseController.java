package cn.lonecloud.market.base;

import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.cts.Constants;
import cn.lonecloud.market.cts.ServerResponseCode;
import cn.lonecloud.market.pojo.User;
import cn.lonecloud.market.service.*;
import cn.lonecloud.market.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 基础控制层
 * Created by lonecloud on 2017/8/23.
 */
public class BaseController<T> {

    protected User currentUser = null;

    @Autowired
    protected UserService userService;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected ProductService productService;
    @Autowired
    protected FileService fileService;

    @Autowired
    protected ShippingService shippingService;

    protected ServerResponse<User> checkCurrentUser() {
        currentUser = (User) RequestUtils.getRequest().getSession().getAttribute(Constants.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.error(ServerResponseCode.NEED_LOGIN.getCode(), "用户未登录,需要强制登录Status=10");
        } else {
            return ServerResponse.success("成功", currentUser);
        }
    }

}
