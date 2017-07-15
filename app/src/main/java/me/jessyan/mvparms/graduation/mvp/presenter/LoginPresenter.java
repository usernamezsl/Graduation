package me.jessyan.mvparms.graduation.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.LoginContract;
import me.jessyan.mvparms.graduation.mvp.model.api.User;
import me.jessyan.mvparms.graduation.mvp.model.entity.BaseJson;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyUser;
import me.jessyan.mvparms.graduation.mvp.ui.activity.BottomViewActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */


/**
 * 作者: 张少林 on 2017/3/6 0006.
 * 邮箱:1083065285@qq.com
 */

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    public static final String NAME = "NAME";

    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private SPUtils mSpUtils;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    /**
     * 登录请求验证
     *
     * @param username
     * @param password
     */
    public void loginRequest(final String username, final String password) {
        mModel.login(username, password)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (username != null && password != null) {
                            mRootView.showLoading();//显示加载框
                        } else {
                            mRootView.showMessage("用户名,密码不能为空，请重新输入!");
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
//                        mRootView.hideLoading();//隐藏加载框
                    }
                })
                .compose(RxUtils.<BaseJson<List<MyUser>>>bindToLifecycle(mRootView))////使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new Action1<BaseJson<List<MyUser>>>() {
                    @Override
                    public void call(BaseJson<List<MyUser>> listBaseJson) {
                        mRootView.hideLoading();
                        List<MyUser> users = listBaseJson.getData();
                        for (MyUser user : users) {
                            String username1 = user.getUsername().trim();
                            String password1 = user.getPassword().trim();
                            if (username != null && password != null) {
                                if (username1.equals(username.trim()) && password1.equals(password.trim())) {
                                    if (mRootView.judgeCheck()) {
                                        mSpUtils.putBoolean("remember_password", true);
                                        mSpUtils.putString("username", username1);
                                        mSpUtils.putString("password", password1);
                                    } else {
                                        mSpUtils.clear();
                                    }
                                    mRootView.loginSuccess();
                                } else {
                                    mRootView.showMessage("用户不存在，请先注册！");
                                }
                            } else {
                                mRootView.showMessage("用户名,密码不能为空");
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRootView.hideLoading();
                        ToastUtils.showLongToastSafe("服务器连接异常~~");
                    }
                });

    }

    public void getUseFromDb(final String name, final String password) {
        Observable.create(new Observable.OnSubscribe<List<User>>() {
            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                List<User> users = DataSupport
                        .findAll(User.class);
                subscriber.onNext(users);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        for (User user : users) {
                            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                                Intent intent = new Intent(mApplication, BottomViewActivity.class);
                                intent.putExtra(NAME, user.getName());
                                EventBus.getDefault().postSticky(user);
                                mAppManager.startActivity(intent);
                            } else {
                                ToastUtils.showLongToastSafe("用户不存在，请先注册~~");
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRootView.hideLoading();
                        ToastUtils.showLongToastSafe("登录超时~~");
                    }
                });
    }

    public void setEditText() {
        mSpUtils = new SPUtils("user");
        boolean isRemember = mSpUtils.getBoolean("remember_password", false);
        if (isRemember) {
            //将账号密码设置到文本框中
            String username = mSpUtils.getString("username", "");
            String password = mSpUtils.getString("password", "");
            mRootView.setUsername(username);
            mRootView.setPassword(password);
            mRootView.setChecked();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        this.mSpUtils = null;
    }

}
