package cn.lonecloud.market.vo;

import java.math.BigDecimal;

/**
 * Created by lonecloud on 2017/8/27.
 */
public class ProductListVO {


    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private BigDecimal price;

    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String imgPrefixUrl;

    public String getImgPrefixUrl() {
        return imgPrefixUrl;
    }

    public void setImgPrefixUrl(String imgPrefixUrl) {
        this.imgPrefixUrl = imgPrefixUrl;
    }
}
