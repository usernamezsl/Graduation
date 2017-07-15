package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;

/**
 * 作者: 张少林 on 2017/4/17 0017.
 * 邮箱:1083065285@qq.com
 */

public class AboutFragment extends WEFragment {
    public static AboutFragment newInstance() {
        AboutFragment aboutFragment = new AboutFragment();
        return aboutFragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.fragment_about_view, null);
    }

    @Override
    protected void initData() {

    }
}
