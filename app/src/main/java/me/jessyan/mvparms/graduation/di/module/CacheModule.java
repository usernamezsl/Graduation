package me.jessyan.mvparms.graduation.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache.internal.RxCache;
import me.jessyan.mvparms.graduation.mvp.model.api.cache.CommonCache;
import me.jessyan.mvparms.graduation.mvp.model.api.cache.GankCache;
import me.jessyan.mvparms.graduation.mvp.model.api.cache.ZhihuCache;

/**
 * Created by zhiyicx on 2016/3/30.
 */
@Module
public class CacheModule {

    @Singleton
    @Provides
    CommonCache provideCommonService(RxCache rxCache) {
        return rxCache.using(CommonCache.class);
    }

    @Singleton
    @Provides
    ZhihuCache provideZhihuService(RxCache rxCache) {
        return rxCache.using(ZhihuCache.class);
    }

    @Singleton
    @Provides
    GankCache provideGankService(RxCache rxCache) {
        return rxCache.using(GankCache.class);
    }
}
