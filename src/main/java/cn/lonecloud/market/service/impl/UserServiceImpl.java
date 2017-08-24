package cn.lonecloud.market.service.impl;

import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.common.TokenCache;
import cn.lonecloud.market.cts.Constants;
import cn.lonecloud.market.dao.UserMapper;
import cn.lonecloud.market.pojo.User;
import cn.lonecloud.market.service.UserService;
import cn.lonecloud.market.utils.MD5Util;
import cn.lonecloud.market.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.util.List;
import java.util.UUID;

import static cn.lonecloud.market.common.TokenCache.TOKEN_PREFIX;

/**
 * Created by lonecloud on 2017/8/23.
 */
@Service
public class UserServiceImpl implements UserService<User> {

    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int count = userMapper.checkUsername(username);
        if (count == 0) {
            return ServerResponse.error("用户名不存在");
        }
        //TODO 密码登录MD5
        String MD5Pwd = MD5Util.MD5EncodeUtf8(password);
        //查询用户
        User user = userMapper.selectLogin(username, MD5Pwd);
        if (user == null) {
            return ServerResponse.error("密码输入错误");
        }
        //将用户密码设置为空
        user.setPassword(StringUtils.EMPTY);
        //更新上次登录IP并且将user登录次数+1
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setLastIp(RequestUtils.getIpAddress());
        updateUser.setLogincount(user.getLogincount()!=null?user.getLogincount()+1:1);
        //设置用户登录次数
        user.setLogincount(updateUser.getLogincount());
        userMapper.updateByPrimaryKeySelective(updateUser);
        return ServerResponse.success("登录成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse<String> serverResponse = checkUser(user);
        //成功则返回数据
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        //设置role
        user.setRole(Constants.RoleCts.NORMAL);
        //密码加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        //插入数据
        int count = userMapper.insert(user);
        if (count == 0) {
            return ServerResponse.error("注册失败");
        }
        return ServerResponse.success("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String type, String value) {
        //判断空
        if (StringUtils.isNotBlank(type)) {
            //校验用户名
            if (Constants.UserCts.USERNAME.equals(type)) {
                if (userMapper.checkUsername(value) != 0) {
                    return ServerResponse.error("用户名已存在");
                }
            }
            //校验邮箱
            if (Constants.UserCts.EMAIL.equals(type)) {
                if (userMapper.checkEmail(value) != 0) {
                    return ServerResponse.error("邮箱已经被注册");
                }
            }
        } else {
            return ServerResponse.error("参数错误");
        }
        return ServerResponse.success("校验成功");
    }

    @Override
    public ServerResponse<String> getForgetProblem(String username) {
        ServerResponse serverResponse = this.checkValid(Constants.UserCts.USERNAME,username);
        if (serverResponse.isSuccess()) {
            return ServerResponse.error("用户名不存在");
        }
        String problem = userMapper.getForgetProblem(username);
        if (StringUtils.isNotBlank(problem)) {
            return ServerResponse.success("成功", problem);
        }
        return ServerResponse.error("问题不存在!");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String problem, String answer) {
        ServerResponse<String> usernameResp = checkValid(Constants.UserCts.USERNAME, username);
        if (!usernameResp.isSuccess()) {
            int count=userMapper.checkAnswer(username, problem, answer);
            if (count>0){
                //生成缓存信息token
//                可以使用本地或者redis
                String forgetToken= UUID.randomUUID().toString();
                TokenCache.setKey(TOKEN_PREFIX+username,forgetToken);
                return ServerResponse.success(forgetToken);
            }
        }
        return ServerResponse.error("问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPwd(String username, String forgetToken, String newPassword) {
        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.error("参数错误,Token需要传递");
        }
        ServerResponse serverResponse = this.checkValid(username, Constants.UserCts.USERNAME);
        if (serverResponse.isSuccess()) {
            return ServerResponse.error("用户名不存在");
        }
        String token=TokenCache.getKey(TOKEN_PREFIX+username);
        if (StringUtils.isBlank(token)){
            return ServerResponse.error("token无效或过期");
        }
        if (StringUtils.equals(token,forgetToken)){
            String md5Pwd = MD5Util.MD5EncodeUtf8(newPassword);
            int count=userMapper.forgetResetPwd(username,md5Pwd);
            if (count>0){
                return ServerResponse.success("修改密码成功");
            }

        }else {
            return ServerResponse.error("token错误,请重新获取重置密码");
        }
        return ServerResponse.error("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPwd(User user, String oldPwd, String newPwd) {
        String md5OldPwd = MD5Util.MD5EncodeUtf8(oldPwd);
        String md5newPwd=MD5Util.MD5EncodeUtf8(newPwd);
        int userCount=userMapper.checkPassword(user.getId(),md5OldPwd);
        if (userCount==0){
            return ServerResponse.error("用旧密码错误");
        }
        user.setPassword(md5newPwd);
        userCount = userMapper.updateByPrimaryKeySelective(user);
        if (userCount>0){
            return ServerResponse.success("密码更新成功");
        }
        return ServerResponse.error("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateUser(User user) {
        //判断用户的email是否存在数据库中
        List<User> emailUsers=userMapper.getUserByEmail(user.getEmail());
        if (emailUsers!=null&&emailUsers.size()!=0){
            for (User us:emailUsers) {
                if (us.getId()!=user.getId()){
                    return ServerResponse.error("该Email已经被其他人使用了");
                }
            }
        }
        //更新user
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount>0){
            return ServerResponse.success("更新个人信息成功!",user);
        }
        return ServerResponse.error("更新个人信息失败!");
    }

    @Override
    public ServerResponse<User> getUserInfo(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user!=null){
            return ServerResponse.success("成功",user);
        }
        return ServerResponse.error("没有找到该用户信息");
    }


    /**
     * 检查用户
     * @param user
     * @return
     */
    private ServerResponse<String> checkUser(User user) {
        //校验用户名是否已经存在
        int resultCount = userMapper.checkUsername(user.getUsername());
        if (resultCount > 0) {
            return ServerResponse.error("用户名已存在");
        }
        //校验邮箱
        resultCount = userMapper.checkEmail(user.getEmail());
        if (resultCount > 0) {
            return ServerResponse.error("邮箱已经被注册");
        }
        //TODO 校验电话号码
        return ServerResponse.success();
    }
}
