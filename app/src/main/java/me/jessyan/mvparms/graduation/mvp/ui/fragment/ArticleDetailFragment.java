package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;

/**
 * 作者: 张少林 on 2017/4/11 0011.
 * 邮箱:1083065285@qq.com
 */

public class ArticleDetailFragment extends WEFragment {


    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_detail_bottom_like)
    TextView mTvDetailBottomLike;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView mTvDetailBottomComment;
    @BindView(R.id.tv_detail_bottom_share)
    TextView mTvDetailBottomShare;

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_CONTENT = "arg_content";

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout mLlDetailBottom;

    private String title;
    private String content;

    public static ArticleDetailFragment newInstance(String title, String content) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        bundle.putString(ARG_CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        title = arguments.getString(ARG_TITLE);
        content = arguments.getString(ARG_CONTENT);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.fragment_article_detail, null);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("独创文章");
        mCenterTitle.setVisibility(View.GONE);
        mTvTitle.setText(title);
        mTvContent.setText(content);
        mTvDetailBottomLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLongToastSafe("点赞成功~");
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
}
