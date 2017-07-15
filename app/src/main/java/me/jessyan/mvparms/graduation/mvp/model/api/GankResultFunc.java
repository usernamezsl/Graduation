package me.jessyan.mvparms.graduation.mvp.model.api;

import me.jessyan.mvparms.graduation.mvp.model.entity.GankHttpResponse;
import rx.functions.Func1;

/**
 * 作者: 张少林 on 2017/4/5 0005.
 * 邮箱:1083065285@qq.com
 */

public class GankResultFunc<T> implements Func1<GankHttpResponse<T>, T> {
    @Override
    public T call(GankHttpResponse<T> tGankHttpResponse) {
        if (!tGankHttpResponse.isError()) {
            try {
                throw new ApiException("数据加载异常~");
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return tGankHttpResponse.getResults();
    }
}
