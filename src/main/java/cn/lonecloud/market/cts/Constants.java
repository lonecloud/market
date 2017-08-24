package cn.lonecloud.market.cts;

/**
 * Created by lonecloud on 2017/8/23.
 */
public interface Constants {

    /**
     * 当前用户
     */
    public static final String CURRENT_USER="user";

    public static final String LAST_USER_IP_PREFIX="lastIp_";

    public interface RoleCts{
        public static final int ADMIN=1;
        public static final int NORMAL=0;
    }
    public interface UserCts{
        static final String USERNAME="username";
        static final String EMAIL="email";

    }

}
