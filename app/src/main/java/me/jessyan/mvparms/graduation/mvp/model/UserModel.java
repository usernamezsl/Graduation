package me.jessyan.mvparms.graduation.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.UserContract;
import me.jessyan.mvparms.graduation.mvp.model.api.cache.CacheManager;
import me.jessyan.mvparms.graduation.mvp.model.api.service.ServiceManager;

/**
 * Created by jess on 9/4/16 10:56
 * Contact with jess.yan.effort@gmail.com
 */
@ActivityScope
public class UserModel extends BaseModel<ServiceManager, CacheManager> implements UserContract.Model {
    public static final int USERS_PER_PAGE = 10;

    @Inject
    public UserModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }


}
