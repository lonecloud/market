package cn.lonecloud.market.dao;

import cn.lonecloud.market.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 检查用户名
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 检查登录
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 检查邮箱
     * @param email
     * @return
     */
    int checkEmail(String email);


    /**
     * 获取忘记问题
     * @param username
     * @return
     */
    String getForgetProblem(String username);

    /**
     * 检查答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 重置密码
     * @param username
     * @param md5Pwd
     * @return
     */
    int forgetResetPwd(@Param("username") String username, @Param("md5Pwd") String md5Pwd);

    /**
     * 检查密码
     * @param userId
     * @param password
     */
    int checkPassword(@Param("userId") Integer userId,@Param("password") String password);

    /**
     * 通过邮箱获取用户
     * @param email
     * @return
     */
    List<User> getUserByEmail(String email);
}