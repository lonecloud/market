package cn.lonecloud.market.cts;

/**
 * Created by lonecloud on 2017/10/3.
 */
public enum CartEnum {

    SELECT(0),NOSELECT(1);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    CartEnum(int value) {
        this.value = value;
    }
}
