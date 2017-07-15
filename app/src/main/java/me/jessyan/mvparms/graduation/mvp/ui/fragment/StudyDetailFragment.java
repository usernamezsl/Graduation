package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.jess.arms.base.BaseApplication;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;

/**
 * 作者: 张少林 on 2017/4/9 0009.
 * 邮箱:1083065285@qq.com
 */

public class StudyDetailFragment extends WEFragment {

    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_detail_bottom_like)
    TextView mTvDetailBottomLike;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView mTvDetailBottomComment;
    @BindView(R.id.tv_detail_bottom_share)
    TextView mTvDetailBottomShare;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout mLlDetailBottom;
    @BindView(R.id.webview_content)
    FrameLayout mWebviewContent;
    @BindView(R.id.nsv_scroller)
    NestedScrollView mNsvScroller;

    private String url;
    private String title;

    private static final String ARG_URL = "arg_url";
    private static final String ARG_TITLE = "arg_title";
    private WebView mMWvDetailContent;

    public static StudyDetailFragment newInstance(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, url);
        bundle.putString(ARG_TITLE, title);
        StudyDetailFragment studyDetailFragment = new StudyDetailFragment();
        studyDetailFragment.setArguments(bundle);
        return studyDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        url = arguments.getString(ARG_URL);
        title = arguments.getString(ARG_TITLE);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.fragment_study_detail, null);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("文章");
        mCenterTitle.setVisibility(View.GONE);
        mMWvDetailContent = new WebView(BaseApplication.getInstance());
        mWebviewContent.addView(mMWvDetailContent);
        WebSettings settings = mMWvDetailContent.getSettings();
        //支持获取手势焦点，输入用户名、密码或其他
        mMWvDetailContent.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);//支持js

//        设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        settings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。
//若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        settings.setTextZoom(2);//设置文本的缩放倍数，默认为 100
        mMWvDetailContent.loadUrl(url);
        mMWvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mMWvDetailContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                } else {

                }
            }
        });
        mTvDetailBottomLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLongToastSafe("点赞成功~~");
            }
        });
        mTvDetailBottomComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(CommentFragment.newInstance(title));
            }
        });
        mTvDetailBottomShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLongToastSafe("分享");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMWvDetailContent.removeAllViews();
        mMWvDetailContent.destroy();
    }
}
