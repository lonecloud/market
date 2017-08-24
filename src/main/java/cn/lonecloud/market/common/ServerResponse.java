package cn.lonecloud.market.common;

import cn.lonecloud.market.cts.ServerResponseCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by lonecloud on 2017/8/23.
 */
 @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//非空时候才会输出
public class ServerResponse<T> {

    private int status;

    private String msg;

    private T data;

    public ServerResponse(int status) {
        this.status = status;
    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ServerResponse(int status, String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    /**
     * 判断是否是成功返回
     *
     * @return
     */
    @JsonIgnore
    public boolean isSuccess() {
        return status == ServerResponseCode.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> success() {
        return new ServerResponse<T>(ServerResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> success(String msg) {
        return new ServerResponse<T>(ServerResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> success(String msg, T data) {
        return new ServerResponse<T>(ServerResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> error() {
        return new ServerResponse<T>(ServerResponseCode.ERROR.getCode(),ServerResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> error(String errorMsg) {
        return new ServerResponse<T>(ServerResponseCode.ERROR.getCode(), errorMsg);
    }
    public static <T> ServerResponse<T> error(int status,String errorMsg) {
        return new ServerResponse<T>(status, errorMsg);
    }
    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
