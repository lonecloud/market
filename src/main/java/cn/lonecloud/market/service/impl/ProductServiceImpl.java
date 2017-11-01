package cn.lonecloud.market.service.impl;

import cn.lonecloud.market.base.BaseServiceImpl;
import cn.lonecloud.market.common.ServerResponse;
import cn.lonecloud.market.cts.Constants;
import cn.lonecloud.market.cts.SaleEnum;
import cn.lonecloud.market.cts.ServerResponseCode;
import cn.lonecloud.market.pojo.Category;
import cn.lonecloud.market.pojo.Product;
import cn.lonecloud.market.service.CategoryService;
import cn.lonecloud.market.service.ProductService;
import cn.lonecloud.market.utils.DateTimeUtil;
import cn.lonecloud.market.utils.PropertiesUtil;
import cn.lonecloud.market.vo.ProductDetailVO;
import cn.lonecloud.market.vo.ProductListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lonecloud on 2017/8/27.
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    CategoryService categoryService;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        //判断product是否为空
        if (product != null) {
            //判断id是否为空
            if (product.getId() != null) {
                //update
                int count = productMapper.updateByPrimaryKey(product);
                if (count > 0) {
                    return ServerResponse.success("更新产品成功");
                }
                return ServerResponse.error("更新产品失败");
            } else {
                //update
                int count = productMapper.insert(product);
                if (count > 0) {
                    return ServerResponse.success("保存产品成功");
                }
                return ServerResponse.error("保存产品失败");
            }
        }
        return ServerResponse.error(ServerResponseCode.ILLEGAL_ARGUMENT);
    }

    @Override
    public ServerResponse updateProductStatus(Integer productId, Integer status) {

        if (productId != null && status != null) {
            Product product = new Product();
            product.setId(productId);
            product.setStatus(status);
            int count = productMapper.updateByPrimaryKeySelective(product);
            if (count > 0) {
                return ServerResponse.success("更新产品成功");
            }
            return ServerResponse.error("更新产品失败");
        }
        return ServerResponse.error(ServerResponseCode.ILLEGAL_ARGUMENT);
    }

    @Override
    public ServerResponse<ProductDetailVO> getProductDetail(Integer productId, boolean isAdmin) {
        if (productId != null) {
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product == null) {
                ServerResponse.error("未找到该商品");
            }
            //判断是不是在后台用户的时候
            if (!isAdmin && product.getStatus() != SaleEnum.ON_SALE.getCode()) {
                ServerResponse.error("该商品已经下架了");
            }
            ProductDetailVO productDetailVO = transferProductVO(product);
            return ServerResponse.success("成功", productDetailVO);
        }
        return ServerResponse.error(ServerResponseCode.ILLEGAL_ARGUMENT);
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.list();
        List<ProductDetailVO> proDetailVos = new ArrayList<>(products.size());
        for (Product product : products) {
            proDetailVos.add(transferProductVO(product));
        }
        PageInfo pageResult = new PageInfo(proDetailVos);
        return ServerResponse.success("成功", pageResult);
    }

    @Override
    public ServerResponse searchProduct(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        //根据名字查找
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder("%").append(productName).append("%").toString();
        }
        List<Product> products = productMapper.selectByProductIdOrProductName(productId, productName);
        List<ProductDetailVO> proDetailVos = new ArrayList<>(products.size());
        for (Product product : products) {
            proDetailVos.add(transferProductVO(product));
        }
        PageInfo pageResult = new PageInfo(proDetailVos);
        return ServerResponse.success("成功", pageResult);
    }

    @Override
    public ServerResponse getProductByKeyWordCategory(String keyword, Integer categoryId, String orderBy, Integer pageNum, Integer pageSize) {
        //判断商品类别是否为空
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.error(ServerResponseCode.ILLEGAL_ARGUMENT);
        }
        //获取所有categoryList
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && keyword == null) {
                ArrayList<ProductListVO> productListVos = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVos);
                ServerResponse.success(pageInfo);
            }
            categoryIdList.addAll(categoryService.getDeepChildCategoryIds(categoryId).getData());
            if (StringUtils.isNotBlank(keyword)) {
                keyword = new StringBuilder("%").append(keyword).append("%").toString();
            }
            PageHelper.startPage(pageNum, pageSize);
//            //排序规则 price_desc price_asc
//            if (StringUtils.isNotBlank(orderBy)){
////                PageHelper.
//                if (Constants.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
//                    String[] orderByArr=orderBy.split("_");
//                    PageHelper
//                }
//            }
            List<ProductListVO> productListVOs=Lists.newArrayList();
            List<Product> products = productMapper.selectByProductNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword,
                    categoryIdList.size() == 0 ? null : categoryIdList);
            List<ProductListVO> productListVOList = listTransferProductListVO(products);
            PageInfo pageInfo=new PageInfo();
            pageInfo.setList(productListVOList);
            return ServerResponse.success(pageInfo);
        }
        return  ServerResponse.error(ServerResponseCode.ILLEGAL_ARGUMENT);
    }

    /**
     * 转换VO
     *
     * @param product
     * @return
     */
    private ProductDetailVO transferProductVO(Product product) {
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setId(product.getId());
        productDetailVO.setName(product.getName());
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setDetail(product.getDetail());
        if (product.getCategoryId() != null) {
            Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
            productDetailVO.setParentCategoryId(category != null ? category.getParentId() : 0);
        }
        productDetailVO.setImgPrefixUrl(PropertiesUtil.getProperty(Constants.FTPSERVERPREFIX));
        productDetailVO.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVO.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVO;
    }

    /**
     * 转换ListVo
     * @param products
     * @return
     */
    private List<ProductListVO> listTransferProductListVO(List<Product> products){
        List<ProductListVO> productListVos=Lists.newArrayList();
        for (Product product:products) {
            productListVos.add(transferProductListVO(product));
        }
        return productListVos;
    }

    private ProductListVO transferProductListVO(Product product) {
        ProductListVO listVo=new ProductListVO();
        listVo.setId(product.getId());
        listVo.setCategoryId(product.getCategoryId());
        listVo.setSubtitle(product.getSubtitle());
        listVo.setImgPrefixUrl(PropertiesUtil.getProperty(Constants.FTPSERVERPREFIX));
        listVo.setName(product.getName());
        listVo.setPrice(product.getPrice());
        listVo.setStatus(product.getStatus());
        return listVo;
    }
}
