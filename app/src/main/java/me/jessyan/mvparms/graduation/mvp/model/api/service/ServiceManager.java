package me.jessyan.mvparms.graduation.mvp.model.api.service;

import com.jess.arms.http.BaseServiceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jess on 8/5/16 13:01
 * contact with jess.yan.effort@gmail.com
 */
@Singleton
public class ServiceManager implements BaseServiceManager {
    private CommonService mCommonService;
    private UserService mUserService;
    private ZhihuService mZhihuService;
    private GankService mGankService;
    /**
     * 如果需要添加service只需在构造方法中添加对应的service,在提供get方法返回出去,只要在ServiceModule提供了该service
     * Dagger2会自行注入
     *
     * @param commonService
     */
    @Inject
    public ServiceManager(CommonService commonService, UserService userService, ZhihuService zhihuService,GankService gankService) {
        this.mCommonService = commonService;
        this.mUserService = userService;
        this.mZhihuService = zhihuService;
        this.mGankService = gankService;
    }

    public CommonService getCommonService() {
        return mCommonService;
    }

    public UserService getUserService() {
        return mUserService;
    }

    public ZhihuService getZhihuService() {
        return mZhihuService;
    }

    public GankService getGankService() {
        return mGankService;
    }

    /**
     * 这里可以释放一些资源(注意这里是单例，即不需要在activity的生命周期调用)
     */
    @Override
    public void onDestory() {

    }
}
