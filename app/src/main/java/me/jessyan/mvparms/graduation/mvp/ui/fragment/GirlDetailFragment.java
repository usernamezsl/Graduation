package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import butterknife.BindView;
import common.AppComponent;
import common.WEApplication;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;

/**
 * 作者: 张少林 on 2017/3/24 0024.
 * 邮箱:1083065285@qq.com
 */

public class GirlDetailFragment extends WEFragment {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.iv_girl_detail)
    ImageView mIvGirlDetail;
    private String id;
    private String url;
    private static final String ARG_URL = "arg_url";
    private static final String ARG_ID = "arg_id";

    public static GirlDetailFragment newInstance(String id, String url) {
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putString(ARG_URL, url);
        GirlDetailFragment girlDetailFragment = new GirlDetailFragment();
        girlDetailFragment.setArguments(args);
        return girlDetailFragment;
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.fragment_girl_detail, null);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolBar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getString(ARG_ID);
            url = arguments.getString(ARG_URL);
        }
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        if (url != null) {
            ((WEApplication) _mActivity.getApplication())
                    .getAppComponent()
                    .imageLoader()
                    .loadImage(_mActivity, GlideImageConfig.builder()
                            .url(url)
                            .errorPic(R.drawable.beauty)
                            .placeholder(R.drawable.beauty)
                            .imageView(mIvGirlDetail)
                            .build());
        }
    }


    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }
}
