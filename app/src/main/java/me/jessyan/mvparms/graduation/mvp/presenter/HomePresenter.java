package me.jessyan.mvparms.graduation.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.utils.ToastUtils;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.HomeContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.Comment;
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
 * 作者: 张少林 on 2017/2/24 0024.
 * 邮箱:1083065285@qq.com
 */

@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getHomeData() {
        mModel.getHomeData()
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
                        return Observable.just(hotContentBean.getData());
                    }
                })
                .map(new Func1<List<HotContentBean.DataBean>, List<HotContentBean.DataBean>>() {
                    @Override
                    public List<HotContentBean.DataBean> call(List<HotContentBean.DataBean> dataBeen) {
                        for (int i = 0; i < dataBeen.size(); i++) {
                            HotContentBean.DataBean dataBean = dataBeen.get(i);
                            String title = dataBean.getTitle();
                            String content = dataBean.getContent();
                            HomeData homeData = new HomeData();
                            homeData.setTitle(title);
                            homeData.setContent(content);
                            List<HomeData> list = DataSupport
                                    .where("title = ?", title)
                                    .find(HomeData.class);
                            if (list.isEmpty()) {
                                homeData.saveThrows();
                            }
                        }
                        return dataBeen;
                    }
                })
                .subscribeOn(Schedulers.io())
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

    public void getHomeDataFromDb() {
        Observable.create(new Observable.OnSubscribe<List<HomeData>>() {
            @Override
            public void call(Subscriber<? super List<HomeData>> subscriber) {
                List<HomeData> list = DataSupport
                        .findAll(HomeData.class);
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).filter(new Func1<List<HomeData>, Boolean>() {
            @Override
            public Boolean call(List<HomeData> homeDatas) {
                return !homeDatas.isEmpty();
            }
        }).subscribeOn(Schedulers.io())
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
                        mRootView.showMessage(throwable.getMessage());
                    }
                });
    }

    public void getCommentSize(final String title) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                List<Comment> comments = DataSupport
                        .where("title = ?", title)
                        .find(Comment.class);
                int size = comments.size();
                subscriber.onNext(size);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mRootView.showCommentSize(integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRootView.hideLoading();
                        mRootView.showMessage(throwable.getMessage());
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
