package cn.lonecloud.market.utils;

import cn.lonecloud.market.cts.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lonecloud on 2017/8/29.
 * FTP上传工具类
 * @author lonecloud
 */
public class FTPUtil {

    static final Logger logger= LoggerFactory.getLogger(FTPUtil.class);

    private static final String FTPIP;
    private static final String FTPUSER;
    private static final String FTPPASSWORD;
    private static final int FTPPORT;

    static {
        FTPIP=PropertiesUtil.getProperty(Constants.FTPCts.IP);
        FTPUSER=PropertiesUtil.getProperty(Constants.FTPCts.USER);
        FTPPASSWORD=PropertiesUtil.getProperty(Constants.FTPCts.PASSWORD);
        String port=PropertiesUtil.getProperty(Constants.FTPCts.PORT);
        FTPPORT= StringUtils.isNotBlank(port)?Integer.parseInt(port):21;
    }

    private FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * 上传文件到FTP服务器上
     * @param files
     * @return
     * @throws IOException
     */
    public static boolean uploadFile(List<File> files) throws IOException {
        FTPUtil ftpUtil=new FTPUtil(FTPIP,FTPPORT,FTPUSER,FTPPASSWORD);
        logger.info("开始上传文件到ftp服务器");
        boolean uploaded = ftpUtil.uploadFile("img", files);
        logger.info("上传结束上传结果为:{}",uploaded);
        return uploaded;
    }

    /**
     * 上传文件
     * @param remotePath
     * @param files
     * @return
     */
    private boolean uploadFile(String remotePath,List<File> files) throws IOException {
        boolean uploaded=false;
        FileInputStream fis=null;
        if (connectServer(ip,port,user,pwd)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding(Constants.PROJECTENDING);
                //设置二进制模式
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                //上传文件
                for (File file:files) {
                    fis=new FileInputStream(file);
                    ftpClient.storeFile(file.getName(),fis);
                }
                uploaded=true;
            } catch (IOException e) {
                logger.error("上传文件失败"+e);
            }finally {
                if (ftpClient!=null){
                    fis.close();
                    ftpClient.disconnect();
                }
            }
        }
        return uploaded;
    }

    /**
     * 链接服务器
     * @param ip
     * @param port
     * @param user
     * @param pwd
     * @return
     */
    private boolean connectServer(String ip,int port,String user,String pwd){
        ftpClient=new FTPClient();
        try {
            ftpClient.connect(ip,port);
            return ftpClient.login(user,pwd);
        } catch (IOException e) {
            logger.error("ftp链接失败",e);
        }
        return false;
    }

    private String ip;

    private int port;

    private String user;

    private String pwd;

    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
