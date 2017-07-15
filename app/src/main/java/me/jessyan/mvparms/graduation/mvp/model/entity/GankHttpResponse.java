package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/4/21 0021.
 * 邮箱:1083065285@qq.com
 */

public class GankHttpResponse<T> {
    private boolean error;
    private T results;


    public boolean isError() {
        return error;
    }

    public T getResults() {
        return results;
    }
}
