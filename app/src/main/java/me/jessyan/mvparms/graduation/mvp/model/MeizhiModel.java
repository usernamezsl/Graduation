package me.jessyan.mvparms.graduation.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.MeizhiContract;
import me.jessyan.mvparms.graduation.mvp.model.api.cache.CacheManager;
import me.jessyan.mvparms.graduation.mvp.model.api.service.ServiceManager;
import me.jessyan.mvparms.graduation.mvp.model.entity.GanHuoDataBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankHttpResponse;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankItemBean;
import rx.Observable;


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
public class MeizhiModel extends BaseModel<ServiceManager, CacheManager> implements MeizhiContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MeizhiModel(ServiceManager serviceManager, CacheManager cacheManager, Gson gson, Application application) {
        super(serviceManager, cacheManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestory() {
        super.onDestory();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GankItemBean> getMeizhi(int num) {
        return mServiceManager.getCommonService()
                .getMeizhi(num);
//        return mCacheManager.getCommonCache()
//                .getMeizhiData(meizhi)
//                .flatMap(new Func1<Reply<GankItemBean>, Observable<GankItemBean>>() {
//                    @Override
//                    public Observable<GankItemBean> call(Reply<GankItemBean> gankItemBeanReply) {
//                        return Observable.just(gankItemBeanReply.getData());
//                    }
//                });
    }

    @Override
    public Observable<GankHttpResponse<List<GanHuoDataBean>>> getMeizhiDesc(int page) {
        return mServiceManager.getGankService()
                .getData(page);
    }
}