package me.jessyan.mvparms.graduation.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.utils.ToastUtils;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.graduation.mvp.contract.SignupContract;
import me.jessyan.mvparms.graduation.mvp.model.api.User;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


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
public class SignupPresenter extends BasePresenter<SignupContract.Model, SignupContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public SignupPresenter(SignupContract.Model model, SignupContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void signup(String name, String password) {
        if (name != null && password != null) {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            List<User> users = DataSupport
                    .where("name = ?", user.getName())
                    .find(User.class);
            if (users.isEmpty()) {
                user.saveThrows();
                if (user.isSaved()) {
                    ToastUtils.showLongToastSafe("注册成功，请登录~~");
                }
            } else {
                ToastUtils.showLongToastSafe("用户已存在，请更改用户名~~");
            }
        }

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
