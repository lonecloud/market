package cn.lonecloud.market.cts;

/**
 * Created by lonecloud on 2017/8/30.
 * 销售状态
 */
public enum  SaleEnum {
    ON_SALE(1,"在售");

    SaleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
