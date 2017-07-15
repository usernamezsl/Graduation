package common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.mvparms.graduation.R;
import me.yokeyword.fragmentation.SupportFragment;

import static android.content.ContentValues.TAG;

/**
 * 作者: 张少林 on 2017/2/21 0021.
 * 邮箱:1083065285@qq.com
 */

public abstract class SimpleBaseFragment extends SupportFragment {
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);//绑定到butterKnife
        if (useEventBus()) {
            EventBus.getDefault().register(this);//注册事件总线
        }
        initDta();
        return view;
    }

    protected abstract void initDta();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);//取消订阅
        }
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();//解除绑定
        }
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

    /**
     * 选择是否使用eventBus
     *
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }


    protected abstract int getFragmentId();




}
