package cn.lonecloud.market.controller.admin;

import cn.lonecloud.market.base.BaseController;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.cts.Constants;
import cn.lonecloud.market.pojo.User;
import cn.lonecloud.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by lonecloud on 2017/8/24.
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController extends BaseController<User>{

    @Autowired
    UserService userService;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> serverResponse = userService.login(username, password);
        if (serverResponse.isSuccess()) {
            User user = serverResponse.getData();
            if (Constants.RoleCts.ADMIN==user.getRole()){
                session.setAttribute(Constants.CURRENT_USER,user);
            }else {
                ServerResponse.error("该用户不是管理员登录失败");
            }
        }
        return serverResponse;
    }
}
