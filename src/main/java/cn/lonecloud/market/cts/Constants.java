package cn.lonecloud.market.cts;

/**
 * Created by lonecloud on 2017/8/23.
 * 常量类
 */
public interface Constants {

    /**
     * 当前用户
     */
    public static final String CURRENT_USER="user";

    public static final String LAST_USER_IP_PREFIX="lastIp_";

    public static final String FTPSERVERPREFIX="ftp.server.prefix";

    public static final String PROJECTENDING ="UTF-8";

    /**
     * 角色类
     */
    public interface RoleCts{
        public static final int ADMIN=1;
        public static final int NORMAL=0;
    }

    /**
     * 用户类
     */
    public interface UserCts{
        static final String USERNAME="username";
        static final String EMAIL="email";

    }

    /**
     * FTP常量类
     */
    public interface FTPCts{
        static final String IP="ftp.server.ip";
        static final String USER="ftp.server.user";
        static final String PASSWORD="ftp.server.password";
        static final String PORT="ftp.server.port";

    }

}
