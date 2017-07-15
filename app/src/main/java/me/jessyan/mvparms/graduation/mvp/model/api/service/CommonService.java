package me.jessyan.mvparms.graduation.mvp.model.api.service;

import java.util.List;

import me.jessyan.mvparms.graduation.mvp.model.entity.BaseJson;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankItemBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotContentBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyUser;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 存放通用的一些API
 * Created by jess on 8/5/16 12:05
 * contact with jess.yan.effort@gmail.com
 */
public interface CommonService {
    /**
     * 登录请求
     *
     * @return
     */
    @GET("user.php")
    Observable<BaseJson<List<MyUser>>> getUser();

    /**
     * 获取发现页面主要数据
     *
     * @return
     */
    @GET("Home.php")
    Observable<HotContentBean> getHotContent();

    /**
     * 随机妹纸图
     *
     * @param num
     * @return
     */
    @GET("http://gank.io/api/random/data/福利/{num}")
    Observable<GankItemBean> getMeizhi(@Path("num") int num);

}
