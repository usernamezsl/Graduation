package me.jessyan.mvparms.graduation.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.jessyan.mvparms.graduation.mvp.model.api.service.CommonService;
import me.jessyan.mvparms.graduation.mvp.model.api.service.GankService;
import me.jessyan.mvparms.graduation.mvp.model.api.service.UserService;
import me.jessyan.mvparms.graduation.mvp.model.api.service.ZhihuService;
import retrofit2.Retrofit;

/**
 * Created by zhiyicx on 2016/3/30.
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    CommonService provideCommonService(Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }

    @Singleton
    @Provides
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Singleton
    @Provides
    ZhihuService provideZhihuService(Retrofit retrofit) {
        return retrofit.create(ZhihuService.class);
    }

    @Singleton
    @Provides
    GankService provideGankService(Retrofit retrofit) {
        return retrofit.create(GankService.class);
    }
}
