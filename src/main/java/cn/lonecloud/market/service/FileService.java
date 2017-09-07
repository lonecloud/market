package cn.lonecloud.market.service;

import cn.lonecloud.market.base.BaseService;
import cn.lonecloud.market.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lonecloud on 2017/8/29.
 */
public interface FileService extends BaseService{

    String uploadFile(MultipartFile file);
}
