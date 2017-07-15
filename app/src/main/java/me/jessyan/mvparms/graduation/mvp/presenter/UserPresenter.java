package me.jessyan.mvparms.graduation.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.UserContract;
import me.jessyan.mvparms.graduation.mvp.model.api.User;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by jess on 9/4/16 10:59
 * Contact with jess.yan.effort@gmail.com
 */
@ActivityScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<User> mUsers = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int lastUserId = 1;
    private boolean isFirst = true;


    @Inject
    public UserPresenter(UserContract.Model model, UserContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
//        mAdapter = new UserAdapter(mUsers);
        mRootView.setAdapter(mAdapter);//设置Adapter
    }

    public void requestUsers(final boolean pullToRefresh) {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }
        }, mRootView.getRxPermissions(), mRootView, mErrorHandler);


        if (pullToRefresh) lastUserId = 1;//上拉刷新默认只请求第一页

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst){//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mUsers = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
