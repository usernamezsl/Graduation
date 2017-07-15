package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Comment;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.CommentAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: 张少林 on 2017/3/29 0029.
 * 邮箱:1083065285@qq.com
 */

public class CommentFragment extends WEFragment {

    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.comment_content)
    RecyclerView mCommentContent;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.iv_send)
    ImageView mIvSend;

    private static final String ARG_ID = "arg_id";
    private static final String ARG_TITLE = "arg_title";

    private String title;

    private CommentAdapter mAdapter;

    public static CommentFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        title = arguments.getString(ARG_TITLE);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_comment_view, null);
    }

    @Override
    protected void initData() {
        KeyboardUtils.hideSoftInput(_mActivity);//隐藏软键盘

        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("评论");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        showContent();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showContent();
            }
        });
        mIvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                String commentString = mEtComment.getText().toString();
                comment.setTitle(title);
                comment.setComment(commentString);
                comment.saveThrows();
                if (comment.isSaved()) {
                    KeyboardUtils.hideSoftInput(_mActivity);
                    ToastUtils.showLongToastSafe("评论成功，请刷新显示~~");
                }
            }
        });
    }

    private void showContent() {
        Observable.create(new Observable.OnSubscribe<List<Comment>>() {
            @Override
            public void call(Subscriber<? super List<Comment>> subscriber) {
                List<Comment> list = DataSupport
                        .where("title = ?", title)
                        .find(Comment.class);
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRefresh.setRefreshing(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Comment>>() {
                    @Override
                    public void call(List<Comment> comments) {
                        if (mRefresh.isRefreshing()) {
                            mRefresh.setRefreshing(false);
                        }
                        initAdapter(comments);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mRefresh.isRefreshing()) {
                            mRefresh.setRefreshing(false);
                        }
                    }
                });
    }

    private void initAdapter(List<Comment> comments) {
        mAdapter = new CommentAdapter(R.layout.item_list_view, comments);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mAdapter.setEmptyView(getEmptyView());
        mCommentContent.setLayoutManager(new LinearLayoutManager(_mActivity));
        mCommentContent.setHasFixedSize(true);
        mCommentContent.setAdapter(mAdapter);
    }

    private View getEmptyView() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.empty_answer_view, null);
        return view;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

}
