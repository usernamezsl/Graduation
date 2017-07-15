package me.jessyan.mvparms.graduation.mvp.model.api;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseApplication;

import java.util.List;

import common.WEApplication;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankHttpResponse;
import rx.Observable;
import rx.functions.Func1;

/**
 * 作者: 张少林 on 2017/4/8 0008.
 * 邮箱:1083065285@qq.com
 */

public class ComposeString2List<T> implements Observable.Transformer<String, List<T>> {

    public TypeToken<GankHttpResponse<List<T>>> mTypeToken;

    public ComposeString2List(TypeToken<GankHttpResponse<List<T>>> typeToken) {
        mTypeToken = typeToken;
    }

    public static ComposeString2List newCompose(TypeToken typeToken) {
        return new ComposeString2List(typeToken);
    }

    @Override
    public Observable<List<T>> call(Observable<String> observable) {
//        return NetworkUtils.IS;
        return null;
    }

    //读取列表数据
    private static class ReadListFun<E> implements Func1<String, Observable<List<E>>> {
        private TypeToken<GankHttpResponse<List<E>>> mTypeToken;

        public ReadListFun(TypeToken<GankHttpResponse<List<E>>> typeToken) {
            mTypeToken = typeToken;
        }

        @Override
        public Observable<List<E>> call(String s) {
            GankHttpResponse<List<E>> response = ((WEApplication) BaseApplication.getInstance())
                    .getAppComponent().gson().fromJson(s, mTypeToken.getType());
            if (response != null && response.isError()) {
                return Observable.just(response.getResults());
            } else {

                return Observable.error(new Throwable());
            }
        }
    }
}
