package me.jessyan.mvparms.graduation.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.utils.ToastUtils;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.HotContract;
import me.jessyan.mvparms.graduation.mvp.model.api.ApiException;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankItemBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotContentBean;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
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
 * 作者: 张少林 on 2017/2/21 0021.
 * 邮箱:1083065285@qq.com
 */

@ActivityScope
public class HotPresenter extends BasePresenter<HotContract.Model, HotContract.View> {
    private static final String TAG = "HotPresenter";
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public HotPresenter(HotContract.Model model, HotContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    /**
     * 获取头部轮播数据
     *
     * @param num
     */
    public void getMeizhiData(int num) {
        mModel.getMeizhiData(num)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<GankItemBean>bindToLifecycle(mRootView))
                .map(new Func1<GankItemBean, GankItemBean>() {
                    @Override
                    public GankItemBean call(GankItemBean gankItemBean) {
                        List<GankItemBean.ResultsBean> results = gankItemBean.getResults();
                        return gankItemBean;
                    }
                })
                .subscribe(new Action1<GankItemBean>() {
                    @Override
                    public void call(GankItemBean gankItemBean) {
                        mRootView.showBanner(gankItemBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRootView.showMessage("数据加载失败");
                    }
                });

    }

    /**
     * 获取发现页主要数据
     */
    public void getHotContent() {
        mModel.getHotContent()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HotContentBean, Observable<List<HotContentBean.DataBean>>>() {
                    @Override
                    public Observable<List<HotContentBean.DataBean>> call(HotContentBean hotContentBean) {
                        if (!hotContentBean.isStatus()) {
                            try {
                                throw new ApiException(hotContentBean.getMessage());
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }
                        }
                        return Observable.just(hotContentBean.getData());
                    }
                })
                .compose(RxUtils.<List<HotContentBean.DataBean>>bindToLifecycle(mRootView))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HotContentBean.DataBean>>() {
                    @Override
                    public void call(List<HotContentBean.DataBean> dataBeen) {
                        mRootView.showContent(dataBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRootView.hideLoading();
                        mRootView.showMessage(throwable.getMessage());
                        ToastUtils.showLongToastSafe("网络异常，请检查网络连接~~");
                    }
                });
    }

    public void getHotDataFromDb() {
        Observable.create(new Observable.OnSubscribe<List<HomeData>>() {
            @Override
            public void call(Subscriber<? super List<HomeData>> subscriber) {
                List<HomeData> list = DataSupport
                        .findAll(HomeData.class);
                subscriber.onNext(list);
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
                .subscribe(new Action1<List<HomeData>>() {
                    @Override
                    public void call(List<HomeData> homeDatas) {
                        mRootView.showContentFromDb(homeDatas);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRootView.hideLoading();
                        Log.i(TAG, "throwable:-----> " + throwable.getMessage());
                        ToastUtils.showLongToastSafe(throwable.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
