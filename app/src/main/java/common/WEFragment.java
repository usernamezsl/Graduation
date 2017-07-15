package common;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.Presenter;
import com.squareup.leakcanary.RefWatcher;

import me.jessyan.mvparms.graduation.R;

/**
 * Created by jess on 8/5/16 14:11
 * contact with jess.yan.effort@gmail.com
 */
public abstract class WEFragment<P extends Presenter> extends BaseFragment<P> {
    private static final String TAG = "WEFragment";
    protected WEApplication mWeApplication;

    @Override
    protected void ComponentInject() {
        mWeApplication = (WEApplication) mActivity.getApplication();
        setupFragmentComponent(mWeApplication.getAppComponent());
    }

    /**
     * toolbar封装展示fragment栈视图
     *
     * @param toolbar
     */
    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.hierarchy);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_hierarchy:
                        _mActivity.showFragmentStackHierarchyView();
                        _mActivity.logFragmentStackHierarchy(TAG);
                        break;
                }
                return true;
            }
        });
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);


    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher watcher = WEApplication.getRefWatcher(getActivity());//使用leakCanary检测fragment的内存泄漏
        if (watcher != null) {
            watcher.watch(this);
        }
        this.mWeApplication = null;
    }
}
