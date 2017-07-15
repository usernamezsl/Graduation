package me.jessyan.mvparms.graduation.mvp.model.api.service;

import java.util.List;

import me.jessyan.mvparms.graduation.mvp.model.entity.GanHuoDataBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankHttpResponse;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankTechBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 作者: 张少林 on 2017/4/5 0005.
 * 邮箱:1083065285@qq.com
 */

public interface GankService {

    @GET("http://gank.io/api/data/{tech}/{num}/{page}")
    Observable<GankTechBean> getTechList(@Path("tech") String tech,
                                                                 @Path("num") int num, @Path("page") int page);


    @GET("http://www.gank.io/api/data/all/20/{page}")
    Observable<GankHttpResponse<List<GanHuoDataBean>>> getData(@Path("page") int page);

}
