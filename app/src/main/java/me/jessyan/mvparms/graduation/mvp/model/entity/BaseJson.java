package me.jessyan.mvparms.graduation.mvp.model.entity;

import java.io.Serializable;

import me.jessyan.mvparms.graduation.mvp.model.api.Api;

/**
 * 如果你服务器返回的数据固定为这种方式(字段名可根据服务器更改)
 * 替换范型即可重用BaseJson
 * Created by jess on 26/09/2016 15:19
 * Contact with jess.yan.effort@gmail.com
 */

public class BaseJson<T> implements Serializable {
    private Boolean status;
    private String msg;
    private T data;


    public Boolean getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (msg.equals(Api.message)) {
            return true;
        } else {
            return false;
        }
    }
}
