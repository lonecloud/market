package cn.lonecloud.market.service;

import cn.lonecloud.market.base.BaseService;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.pojo.User;

/**
 * Created by lonecloud on 2017/8/23.
 */
public interface UserService extends BaseService<User>{
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public ServerResponse<User> login(String username,String password);

    /**
     * 注册
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 校验数据
     * @param type 类型
     * @param value 值
     * @return
     */
    ServerResponse<String> checkValid(String type, String value);

    /**
     * 获取用户忘记密码时候的问题
     * @param username
     * @return
     */
    ServerResponse<String> getForgetProblem(String username);

    /**
     * 检查问题答案
     * @param username
     * @param problem
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username, String problem, String answer);

    /**
     * 重置密码
     * @param username
     * @param forgetToken
     * @param newPassword
     * @return
     */
    ServerResponse<String> forgetResetPwd(String username, String forgetToken, String newPassword);

    ServerResponse<String> resetPwd(User user, String oldPwd, String newPwd);

    ServerResponse<User> updateUser(User user);


    ServerResponse<User> getUserInfo(Integer userId);

}
