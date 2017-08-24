package cn.lonecloud.market.controller.portal;

import cn.lonecloud.market.base.BaseController;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.cts.Constants;
import cn.lonecloud.market.pojo.User;
import cn.lonecloud.market.service.UserService;
import cn.lonecloud.market.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lonecloud on 2017/8/23.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User> {

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
        ServerResponse serverResponse = userService.login(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Constants.CURRENT_USER, serverResponse.getData());
        }
        return serverResponse;
    }

    /**
     * 登出
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    @ResponseBody
    public ServerResponse<User> logout(HttpSession session) {
        //清除session
        session.setAttribute(Constants.CURRENT_USER, null);
        return ServerResponse.success("注销成功");
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/doRegister")
    @ResponseBody
    public ServerResponse<String> register(User user) {
        user.setCreateIp(RequestUtils.getIpAddress());
        return userService.register(user);
    }

    /**
     * 校验信息
     *
     * @param type
     * @param value
     * @return
     */
    @PostMapping("/checkValid")
    @ResponseBody
    public ServerResponse<String> checkValid(String type, String value) {
        return userService.checkValid(type, value);
    }

    /**
     * 获取用户信息
     *
     * @param session
     * @return
     */
    @GetMapping("/getUserInfo")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        ServerResponse serverResponse = checkCurrentUser();
        return serverResponse;
    }

    /**
     * 获取忘记密码的问题
     *
     * @param username
     * @return
     */
    @GetMapping("/getForgetProblem")
    @ResponseBody
    public ServerResponse<String> getForgetProblem(String username) {
        if (StringUtils.isBlank(username)) {
            return ServerResponse.error("用户名为空");
        }
        return userService.getForgetProblem(username);
    }

    /**
     * 检查忘记用户时候的答案
     *
     * @param username
     * @param problem
     * @param answer
     * @return
     */
    @PostMapping("/checkAnswer")
    @ResponseBody
    public ServerResponse<String> checkAnswer(String username, String problem, String answer) {
        return userService.checkAnswer(username, problem, answer);
    }

    /**
     * 忘记密码重置密码
     *
     * @param username
     * @param forgetToken
     * @param newPassword
     * @return
     */
    @PostMapping("/forgetRestPwd")
    @ResponseBody
    public ServerResponse<String> forgetResetPwd(String username, String forgetToken, String newPassword) {
        return userService.forgetResetPwd(username, forgetToken, newPassword);
    }

    /**
     * 更新密码
     *
     * @param session
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping("/restPwd")
    @ResponseBody
    public ServerResponse resetPwd(HttpSession session, String oldPwd, String newPwd) {
        ServerResponse<User> userServerResponse = checkCurrentUser();
        if (!userServerResponse.isSuccess()) {
            return userServerResponse;
        }
        return userService.resetPwd(currentUser, oldPwd, newPwd);
    }

    /**
     * 更新用户信息
     *
     * @param session
     * @param user
     * @return
     */
    @PostMapping("/updateInfo")
    @ResponseBody
    public ServerResponse<User> updateInfo(HttpSession session, User user) {
        ServerResponse<User> userServerResponse = checkCurrentUser();
        if (!userServerResponse.isSuccess()) {
            return userServerResponse;
        }
        //设置userId
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> serverResponse = userService.updateUser(user);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Constants.CURRENT_USER, user);
        }
        return serverResponse;
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @GetMapping("/getInfo")
    @ResponseBody
    public ServerResponse<User> getInfo(HttpSession session) {
        ServerResponse<User> userServerResponse = checkCurrentUser();
        if (!userServerResponse.isSuccess()) {
            return userServerResponse;
        }
        return userService.getUserInfo(currentUser.getId());
    }

}
