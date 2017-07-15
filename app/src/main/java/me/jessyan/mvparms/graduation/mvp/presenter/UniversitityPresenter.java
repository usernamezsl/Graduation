package me.jessyan.mvparms.graduation.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.UniversitityContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.SectionChildListBean;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
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
 * 作者: 张少林 on 2017/3/23 0023.
 * 邮箱:1083065285@qq.com
 */

@ActivityScope
public class UniversitityPresenter extends BasePresenter<UniversitityContract.Model, UniversitityContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public UniversitityPresenter(UniversitityContract.Model model, UniversitityContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getUniversityData(int id) {
        mModel.getUnivertityData(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .compose(RxUtils.<SectionChildListBean>bindToLifecycle(mRootView))
                .map(new Func1<SectionChildListBean, SectionChildListBean>() {
                    @Override
                    public SectionChildListBean call(SectionChildListBean sectionChildListBean) {
                        List<SectionChildListBean.StoriesBean> stories = sectionChildListBean.getStories();
                        return sectionChildListBean;
                    }
                })
                .subscribe(new Action1<SectionChildListBean>() {
                    @Override
                    public void call(SectionChildListBean sectionChildListBean) {
                        mRootView.showContent(sectionChildListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                      mRootView.showMessage("数据加载失败~~");
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
