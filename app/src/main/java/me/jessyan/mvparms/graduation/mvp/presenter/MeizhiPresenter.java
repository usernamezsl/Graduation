package me.jessyan.mvparms.graduation.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.MeizhiContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.GanHuoDataBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankHttpResponse;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankItemBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.MeizhiWithVideo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
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
 * 作者: 张少林 on 2017/4/21 0021.
 * 邮箱:1083065285@qq.com
 */

@ActivityScope
public class MeizhiPresenter extends BasePresenter<MeizhiContract.Model, MeizhiContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public MeizhiPresenter(MeizhiContract.Model model, MeizhiContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    /**
     * 获取随机妹纸图
     */
    public void requestMeizhi(int num) {
        mModel.getMeizhi(num)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .compose(RxUtils.<GankItemBean>bindToLifecycle(mRootView))
                .map(new Func1<GankItemBean, GankItemBean>() {
                    @Override
                    public GankItemBean call(GankItemBean gankItemBean) {
                        List<GankItemBean.ResultsBean> results = gankItemBean.getResults();
                        return gankItemBean;
                    }
                })
                .subscribe(new Subscriber<GankItemBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        mRootView.showMessage("数据请求失败~~");
                    }

                    @Override
                    public void onNext(GankItemBean gankItemBean) {
                        mRootView.showContent(gankItemBean);
                    }
                });
    }

    public Observable<List<GankItemBean.ResultsBean>> getMeizhiImage(int num) {
        return mModel.getMeizhi(num)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<GankItemBean, Observable<List<GankItemBean.ResultsBean>>>() {
                    @Override
                    public Observable<List<GankItemBean.ResultsBean>> call(GankItemBean gankItemBean) {
                        return Observable.just(gankItemBean.getResults());
                    }
                });
    }

    public Observable<List<GanHuoDataBean>> getMeizhiDesc(int page) {
        return mModel.getMeizhiDesc(page)
                .flatMap(new Func1<GankHttpResponse<List<GanHuoDataBean>>, Observable<List<GanHuoDataBean>>>() {
                    @Override
                    public Observable<List<GanHuoDataBean>> call(GankHttpResponse<List<GanHuoDataBean>> listGankHttpResponse) {
                        return Observable.just(listGankHttpResponse.getResults());
                    }
                });
    }

    public void getMeizhi() {
        Observable.zip(getMeizhiImage(20), getMeizhiDesc(20), new Func2<List<GankItemBean.ResultsBean>, List<GanHuoDataBean>, List<MeizhiWithVideo>>() {
            @Override
            public List<MeizhiWithVideo> call(List<GankItemBean.ResultsBean> resultsBeen, List<GanHuoDataBean> ganHuoDataBeen) {
                return null;
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
                .subscribe(new Action1<List<MeizhiWithVideo>>() {
                    @Override
                    public void call(List<MeizhiWithVideo> meizhiWithVideos) {
                        mRootView.hideLoading();
                        mRootView.showMeizhiWithDesc(meizhiWithVideos);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRootView.hideLoading();
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
