package me.jessyan.mvparms.graduation.mvp.model.api.service;

import me.jessyan.mvparms.graduation.mvp.model.entity.SectionChildListBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.ZhihuDetailBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.ZhihuHotBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 作者: 张少林 on 2017/3/22 0022.
 * 邮箱:1083065285@qq.com
 */

public interface ZhihuService {
    /**
     * 知乎热门列表
     *
     * @return
     */
    @GET("http://news-at.zhihu.com/api/4/news/hot")
    Observable<ZhihuHotBean> getZhihuHotData();

    /**
     * 专栏日报详情
     *
     * @param id
     * @return
     */
    @GET("http://news-at.zhihu.com/api/4/section/{id}")
    Observable<SectionChildListBean> getSectionChildList(@Path("id") int id);

    /**
     * 日报详情
     *
     * @return
     */
    @GET("http://news-at.zhihu.com/api/4/news/{id}")
    Observable<ZhihuDetailBean> getZhihuDetail(@Path("id") int id);
}
