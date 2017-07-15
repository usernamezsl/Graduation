package me.jessyan.mvparms.graduation.mvp.model.api.cache;

import java.util.concurrent.TimeUnit;

import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import me.jessyan.mvparms.graduation.mvp.model.entity.SectionChildListBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.ZhihuHotBean;
import rx.Observable;

/**
 * 作者: 张少林 on 2017/3/24 0024.
 * 邮箱:1083065285@qq.com
 */

public interface ZhihuCache {
    /**
     * 瞎扯
     *
     * @param observable
     * @return
     */
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<ZhihuHotBean>> getZhihuHotData(Observable<ZhihuHotBean> observable);

    /**
     * 瞎扯
     *
     * @param observable
     * @return
     */
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<SectionChildListBean>> getXiaCheData(Observable<SectionChildListBean> observable);

    /**
     * 深夜惊奇接口
     *
     * @param observable
     * @return
     */
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<SectionChildListBean>> getSectionChildList(Observable<SectionChildListBean> observable);

    /**
     * 广告接口
     *
     * @param observable
     * @return
     */
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<SectionChildListBean>> getAdawrewData(Observable<SectionChildListBean> observable);

    /**
     * 上个好大学接口
     *
     * @param observable
     * @return
     */
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<SectionChildListBean>> getUniversity(Observable<SectionChildListBean> observable);

    /**
     * 职人介绍所
     *
     * @param observable
     * @return
     */
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<SectionChildListBean>> getJobData(Observable<SectionChildListBean> observable);

    /**
     * 独自生活
     *
     * @param observable
     * @return
     */
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<SectionChildListBean>> getAlone(Observable<SectionChildListBean> observable);

    /**
     * 好工作
     *
     * @param observable
     * @return
     */
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<SectionChildListBean>> getMajor(Observable<SectionChildListBean> observable);

}
