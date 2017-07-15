package me.jessyan.mvparms.graduation.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.rx_cache.Reply;
import me.jessyan.mvparms.graduation.mvp.contract.MajorContract;
import me.jessyan.mvparms.graduation.mvp.model.api.cache.CacheManager;
import me.jessyan.mvparms.graduation.mvp.model.api.service.ServiceManager;
import me.jessyan.mvparms.graduation.mvp.model.entity.SectionChildListBean;
import rx.Observable;
import rx.functions.Func1;


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
public class MajorModel extends BaseModel<ServiceManager, CacheManager> implements MajorContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MajorModel(ServiceManager serviceManager, CacheManager cacheManager, Gson gson, Application application) {
        super(serviceManager, cacheManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<SectionChildListBean> getMajorData(int id) {
        final Observable<SectionChildListBean> sectionChildList = mServiceManager.getZhihuService()
                .getSectionChildList(id);
        return mCacheManager.getZhihuCache()
                .getMajor(sectionChildList)
                .flatMap(new Func1<Reply<SectionChildListBean>, Observable<SectionChildListBean>>() {
                    @Override
                    public Observable<SectionChildListBean> call(Reply<SectionChildListBean> sectionChildListBeanReply) {
                        return Observable.just(sectionChildListBeanReply.getData());
                    }
                });
    }

    @Override
    public void onDestory() {
        super.onDestory();
        this.mGson = null;
        this.mApplication = null;
    }

}