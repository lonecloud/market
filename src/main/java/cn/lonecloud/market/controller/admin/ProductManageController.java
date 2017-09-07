package cn.lonecloud.market.controller.admin;

import cn.lonecloud.market.base.BaseController;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.cts.Constants;
import cn.lonecloud.market.pojo.Product;
import cn.lonecloud.market.utils.PropertiesUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lonecloud on 2017/8/27.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController extends BaseController {

    /**
     * 保存产品
     *
     * @param product
     * @return
     */
    @PostMapping("/saveProduct")
    @ResponseBody
    public ServerResponse saveProduct(Product product) {
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return productService.saveOrUpdateProduct(product);
    }

    /**
     * 更新产品
     *
     * @param product
     * @return
     */
    @PostMapping("/updateProduct")
    @ResponseBody
    public ServerResponse updateProduct(Product product) {
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return productService.saveOrUpdateProduct(product);
    }

    /**
     * 更新产品状态上下架
     *
     * @param productId
     * @param status
     * @return
     */
    @PostMapping("/updateSaleStatus")
    @ResponseBody
    public ServerResponse updateSaleStatus(Integer productId, Integer status) {
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return productService.updateProductStatus(productId, status);
    }

    /**
     * 获取产品
     *
     * @param productId
     * @return
     */
    @PostMapping("/getProduct")
    @ResponseBody
    public ServerResponse getProduct(Integer productId) {
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return productService.getProductDetail(productId, true);
    }

    /**
     * 列出产品
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public ServerResponse list(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return productService.list(pageNum, pageSize);
    }

    @PostMapping("/search")
    @ResponseBody
    public ServerResponse search(Integer productId, String productName,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        return productService.searchProduct(productId, productName, pageNum, pageSize);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        //权限判断
//        ServerResponse serverResponse = checkCurrentUser();
//        if (!serverResponse.isSuccess()) {
//            return serverResponse;
//        }
        String targetFileName = fileService.uploadFile(file);
        String url = PropertiesUtil.getProperty(Constants.FTPSERVERPREFIX);
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.success(fileMap);
    }

    /**
     * 富文本上传插件
     *
     * @param file
     * @param response
     * @return
     */
    @PostMapping("/richUpload")
    @ResponseBody
    public Map richUpload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletResponse response) {
        //权限判断
        ServerResponse serverResponse = checkCurrentUser();
        Map resultMap = Maps.newHashMap();
        if (!serverResponse.isSuccess()) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员权限");
            return resultMap;
        }
        String targetFileName = fileService.uploadFile(file);
        String url = PropertiesUtil.getProperty(Constants.FTPSERVERPREFIX);
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url + "/" + targetFileName);
        //对后端返回的需要的要求
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;
    }
}
