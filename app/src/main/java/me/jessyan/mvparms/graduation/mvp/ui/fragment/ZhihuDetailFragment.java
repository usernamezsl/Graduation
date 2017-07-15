package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerZhihuDetailComponent;
import me.jessyan.mvparms.graduation.di.module.ZhihuDetailModule;
import me.jessyan.mvparms.graduation.mvp.contract.ZhihuDetailContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.ZhihuDetailBean;
import me.jessyan.mvparms.graduation.mvp.presenter.ZhihuDetailPresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * 作者: 张少林 on 2017/3/24 0024.
 * 邮箱:1083065285@qq.com
 */

public class ZhihuDetailFragment extends WEFragment<ZhihuDetailPresenter> implements ZhihuDetailContract.View {


    @BindView(R.id.tv_detail_bottom_like)
    TextView mTvDetailBottomLike;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView mTvDetailBottomComment;
    @BindView(R.id.tv_detail_bottom_share)
    TextView mTvDetailBottomShare;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout mLlDetailBottom;
    @BindView(R.id.nsv_scroller)
    NestedScrollView mNsvScroller;
    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webcontent)
    FrameLayout mWebcontent;

    private int mId;
    private String title;
    private static final String ARG_ID = "arg_id";
    private WebView mMWvDetailContent;

    public static ZhihuDetailFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_ID, id);

        ZhihuDetailFragment fragment = new ZhihuDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mId = arguments.getInt(ARG_ID);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerZhihuDetailComponent
                .builder()
                .appComponent(appComponent)
                .zhihuDetailModule(new ZhihuDetailModule(this))//请将ZhihuDetailModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zhihudetail_view, null, false);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("文章");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        mPresenter.getZhihuDetail(mId);
    }

    @Override
    public void showContent(final ZhihuDetailBean zhihuDetailBean) {
        String title = zhihuDetailBean.getTitle();
        String share_url = zhihuDetailBean.getShare_url();
        String image = zhihuDetailBean.getImage();
        mMWvDetailContent = new WebView(BaseApplication.getInstance());
        mWebcontent.addView(mMWvDetailContent);
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
        mMWvDetailContent.loadUrl(share_url);
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
                ToastUtils.showLongToastSafe("点赞成功~");
            }
        });
        mTvDetailBottomComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(CommentFragment.newInstance(zhihuDetailBean.getTitle()));
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
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {

                    }
                });
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMWvDetailContent.removeAllViews();
        mMWvDetailContent.destroy();
    }
}