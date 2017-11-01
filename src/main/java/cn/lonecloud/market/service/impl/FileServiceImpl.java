package cn.lonecloud.market.service.impl;

import cn.lonecloud.market.base.BaseService;
import cn.lonecloud.market.base.BaseServiceImpl;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.service.FileService;
import cn.lonecloud.market.utils.FTPUtil;
import cn.lonecloud.market.utils.PropertiesUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传服务层
 * Created by lonecloud on 2017/8/29.
 * @author lonecloud
 *
 */
@Service
public class FileServiceImpl extends BaseServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file) {
        return upload(file);
    }

    private String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String realFileName = UUID.randomUUID().toString() + "." + fileType;
        String filePath = PropertiesUtil.getProperty("market.uploadFilePath");
        logger.info("开始上传文件,上传文件的文件名为:{},上传的路径为:{},新文件名:{}", fileName, filePath, realFileName);
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File descFile = new File(filePath, realFileName);
        try {
            file.transferTo(descFile);
            //文件上传到FTP服务器
            FTPUtil.uploadFile(Lists.<File>newArrayList(descFile));
            //上传服务器上后删除该文件
            descFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return descFile.getName();
    }
}
