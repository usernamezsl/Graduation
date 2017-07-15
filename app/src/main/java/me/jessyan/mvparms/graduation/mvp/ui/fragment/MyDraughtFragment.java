package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Draught;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.DraughtAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: 张少林 on 2017/4/11 0011.
 * 邮箱:1083065285@qq.com
 */

public class MyDraughtFragment extends WEFragment {

    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_my_draught)
    RecyclerView mRvMyDraught;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private DraughtAdapter mAdapter;

    public static MyDraughtFragment newInstance() {
        MyDraughtFragment myDraughtFragment = new MyDraughtFragment();
        return myDraughtFragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.fragment_mydraught_view, null);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("我的草稿");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        showContent();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showContent();
            }
        });
    }

    private void showContent() {
        Observable.create(new Observable.OnSubscribe<List<Draught>>() {
            @Override
            public void call(Subscriber<? super List<Draught>> subscriber) {
                List<Draught> list = DataSupport.findAll(Draught.class);
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mSwipeRefresh.setRefreshing(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Draught>>() {
                    @Override
                    public void call(List<Draught> draughts) {
                        if (mSwipeRefresh.isRefreshing()) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                        initAdapter(draughts);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mSwipeRefresh.isRefreshing()) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                        ToastUtils.showLongToastSafe(throwable.getMessage());
                    }
                });
    }

    private void initAdapter(List<Draught> draughts) {
        mAdapter = new DraughtAdapter(R.layout.item_list_view, draughts);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRvMyDraught.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvMyDraught.setHasFixedSize(true);
        mRvMyDraught.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<Draught> list = adapter.getData();
                Draught draught = list.get(position);
                final String title = draught.getTitle();
                final String content = draught.getContent();
                AlertDialog.Builder dialog = new AlertDialog.Builder(_mActivity);
                dialog.setTitle(" 继续编辑问题 ？ ？");
                dialog.setMessage("是否继续编辑 ? ?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        start(AskFragment.newInstance(title,content));
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
    }
}
