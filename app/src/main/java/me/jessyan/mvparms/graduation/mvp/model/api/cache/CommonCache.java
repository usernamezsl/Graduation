package me.jessyan.mvparms.graduation.mvp.model.api.cache;

import java.util.concurrent.TimeUnit;

import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankItemBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotContentBean;
import rx.Observable;

/**
 * Created by jess on 8/30/16 13:53
 * Contact with jess.yan.effort@gmail.com
 */
public interface CommonCache {

//
//    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
//    Observable<Reply<List<User>>> getUsers(Observable<List<User>> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);


    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<GankItemBean>> getMeizhiData(Observable<GankItemBean> observable);

    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<Reply<HotContentBean>> getHotContent(Observable<HotContentBean> observable);

}
