package me.jessyan.mvparms.graduation.mvp.model.api.cache;

import com.jess.arms.http.BaseCacheManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jess on 8/30/16 13:54
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
public class CacheManager implements BaseCacheManager {
    private CommonCache mCommonCache;
    private ZhihuCache mZhihuCache;
    private GankCache mGankCache;

    /**
     * 如果需要添加Cache只需在构造方法中添加对应的Cache,
     * 在提供get方法返回出去,只要在CacheModule提供了该Cache Dagger2会自行注入
     *
     * @param commonCache
     */
    @Inject
    public CacheManager(CommonCache commonCache, ZhihuCache zhihuCache, GankCache gankCache) {
        this.mCommonCache = commonCache;
        this.mZhihuCache = zhihuCache;
        this.mGankCache = gankCache;
    }

    public CommonCache getCommonCache() {
        return mCommonCache;
    }

    public ZhihuCache getZhihuCache() {
        return mZhihuCache;
    }

    public GankCache getGankCache() {
        return mGankCache;
    }

    /**
     * 这里可以释放一些资源(注意这里是单例，即不需要在activity的生命周期调用)
     */
    @Override
    public void onDestory() {
        mCommonCache = null;
        mZhihuCache = null;
        mGankCache = null;
    }
}
