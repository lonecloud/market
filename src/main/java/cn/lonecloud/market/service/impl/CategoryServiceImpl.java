package cn.lonecloud.market.service.impl;

import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.dao.CategoryMapper;
import cn.lonecloud.market.pojo.Category;
import cn.lonecloud.market.service.CategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lonecloud on 2017/8/24.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger= LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse getChildCategory(int categoryId) {

        List<Category> categories = categoryMapper.getCategoryByPId(categoryId);
        if (CollectionUtils.isEmpty(categories)){
            logger.info("未找到该类");
        }
        return ServerResponse.success("成功",categories);
    }

    @Override
    public ServerResponse addCategory(Category category) {
        if(StringUtils.isBlank(category.getName())){
            return ServerResponse.error("品类名字不能为空");
        }
        category.setStatus(true);
        int count = categoryMapper.insert(category);
        if (count>0){
            return ServerResponse.error("添加品类成功");
        }
        return ServerResponse.success("添加品类失败");
    }

    @Override
    public ServerResponse setCategoryName(Integer categoryId,String categoryName) {
        if (categoryId==null||StringUtils.isBlank(categoryName)){
            return ServerResponse.error("参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int updateCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (updateCount>0){
            return ServerResponse.success("更新成功");
        }
        return ServerResponse.error("更新错误");
    }

    @Override
    public ServerResponse getDeepChildCategory(Integer categoryId) {
        Set<Category> categories=new HashSet<>();
        getAllChildCategoryByPId(categories,categoryId);
        if (CollectionUtils.isEmpty(categories)){
            logger.info("未找到该类别");
        }
        return ServerResponse.success("成功",categories);
    }

    /**
     * 递归获取类别
     * @param categorieSet
     * @param pid
     * @return
     */
    private Set<Category> getAllChildCategoryByPId(Set<Category> categorieSet,Integer pid){
        List<Category> categories = categoryMapper.getCategoryByPId(pid);
        if (!CollectionUtils.isEmpty(categories)){
            categorieSet.addAll(categories);
            for (int i = 0; i < categories.size(); i++) {
                getAllChildCategoryByPId(categorieSet,categories.get(i).getId());
            }
        }
        return categorieSet;
    }
}
