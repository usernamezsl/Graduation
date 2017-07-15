package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyStudyCollection;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.MyStudyCollectionAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartNextFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: 张少林 on 2017/4/10 0010.
 * 邮箱:1083065285@qq.com
 */

public class StudyCollectionsFragment extends WEFragment {


    @BindView(R.id.rv_study_collection_list)
    RecyclerView mRvStudyCollectionList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private MyStudyCollectionAdapter mAdapter;

    public static StudyCollectionsFragment newInstance() {
        StudyCollectionsFragment studyCollectionsFragment = new StudyCollectionsFragment();
        return studyCollectionsFragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.fragment_study_collection, null);
    }

    @Override
    protected void initData() {
        showContent();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showContent();
            }
        });
    }

    private void showContent() {
        Observable.create(new Observable.OnSubscribe<List<MyStudyCollection>>() {
            @Override
            public void call(Subscriber<? super List<MyStudyCollection>> subscriber) {
                List<MyStudyCollection> all = DataSupport
                        .findAll(MyStudyCollection.class);
                subscriber.onNext(all);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MyStudyCollection>>() {
                    @Override
                    public void call(List<MyStudyCollection> myStudyCollections) {
                        mRefresh.setRefreshing(false);
                        initAdapter(myStudyCollections);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRefresh.setRefreshing(false);
                        ToastUtils.showLongToastSafe(throwable.getMessage());
                    }
                });
    }

    private void initAdapter(List<MyStudyCollection> list) {
        mAdapter = new MyStudyCollectionAdapter(R.layout.item_zhihu_hot, list);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRvStudyCollectionList.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvStudyCollectionList.setHasFixedSize(true);
        mRvStudyCollectionList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MyStudyCollection> subjectList = adapter.getData();
                String url = subjectList.get(position).getUrl();
                String desc = subjectList.get(position).getDesc();
                EventBus.getDefault().post(new StartNextFragment(StudyDetailFragment.newInstance(desc, url)));
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<MyStudyCollection> myStudyCollections = adapter.getData();
                String desc = myStudyCollections.get(position).getDesc();
                DataSupport
                        .deleteAll(MyStudyCollection.class, "desc = ?", desc);
                ToastUtils.showLongToastSafe("取消收藏成功，请刷新显示~~");
            }
        });
    }
}
