package me.jessyan.mvparms.graduation.mvp.model.api.cache;

import java.util.concurrent.TimeUnit;

import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankTechBean;
import rx.Observable;

/**
 * 作者: 张少林 on 2017/4/5 0005.
 * 邮箱:1083065285@qq.com
 */

public interface GankCache {

    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<GankTechBean>> getTechdData(Observable<GankTechBean> observable);
}
