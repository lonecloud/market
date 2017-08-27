package cn.lonecloud.market.controller.admin;

import cn.lonecloud.market.base.BaseController;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.pojo.Category;
import cn.lonecloud.market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lonecloud on 2017/8/24.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController extends BaseController {

    /**
     * 查询节点信息
     * @param categoryId
     * @return
     */
    @GetMapping("/getChildCategory")
    @ResponseBody
    public ServerResponse getChildCategory(@RequestParam(defaultValue = "0") Integer categoryId){
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        return categoryService.getChildCategory(categoryId);
    }
    @GetMapping("/getDeepChildCategory")
    @ResponseBody
    public ServerResponse getDeepChildCategory(@RequestParam(defaultValue = "0") Integer categoryId){
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        return categoryService.getDeepChildCategory(categoryId);
    }


    @PostMapping("/addCategory")
    @ResponseBody
    public ServerResponse addCategory(Category category){
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        //TODO 用户鉴权
        return categoryService.addCategory(category);
    }
    @PostMapping("/setCategoryName")
    @ResponseBody
    public ServerResponse setCategoryName(@RequestParam(defaultValue = "0") Integer categoryId,String categoryName){
        ServerResponse serverResponse = checkCurrentUser();
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        //TODO 用户鉴权
        return categoryService.setCategoryName(categoryId,categoryName);
    }

}
