package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.UiUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerAnswerComponent;
import me.jessyan.mvparms.graduation.di.module.AnswerModule;
import me.jessyan.mvparms.graduation.mvp.contract.AnswerContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyAnswers;
import me.jessyan.mvparms.graduation.mvp.presenter.AnswerPresenter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.HotDbAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.MyAnswerAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartBrotherEvent;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.article.AnswerToFragment;
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
 * 作者: 张少林 on 2017/3/2 0002.
 * 邮箱:1083065285@qq.com
 */

public class AnswerFragment extends WEFragment<AnswerPresenter> implements AnswerContract.View {


    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private HotDbAdapter mHotDbAdapter;
    private List<HomeData> mDatas;
    private MyAnswerAdapter mAdapter;

    public static AnswerFragment newInstance() {
        AnswerFragment fragment = new AnswerFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAnswerComponent
                .builder()
                .appComponent(appComponent)
                .answerModule(new AnswerModule(this))//请将AnswerModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_answer_view, null, false);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("问题列表");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {

//        mPresenter.getHotDataFromDb();
        mPresenter.getData();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mPresenter.getHotDataFromDb();
                mPresenter.getData();
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
                        mSwipeRefresh.setRefreshing(true);
                    }
                });
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
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
    public void showContentFromDb(List<HomeData> homeDatas) {
        hideLoading();
        mDatas = new ArrayList<>();
        mRecyclerView.setVisibility(View.INVISIBLE);
        mHotDbAdapter = new HotDbAdapter(R.layout.item_list_view, mDatas);
        mHotDbAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mHotDbAdapter.isFirstOnly(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mHotDbAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
        mDatas.clear();
        mDatas.addAll(homeDatas);
        mHotDbAdapter.notifyDataSetChanged();
        mHotDbAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<HomeData> list = adapter.getData();
                HomeData homeData = list.get(position);
                String title = homeData.getTitle();
                String content = homeData.getContent();
                start(AnswerToFragment.newInstance(title, content));
            }
        });
        mHotDbAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<HomeData> list = adapter.getData();
                HomeData homeData = list.get(position);
                final String title = homeData.getTitle();
                final String content = homeData.getContent();
                switch (view.getId()) {
                    case R.id.tv_praise_popular:
                        ToastUtils.showLongToastSafe("点赞成功~~");
                        break;
                    case R.id.tv_comment_popular:
                        EventBus.getDefault().post(new StartBrotherEvent(CommentFragment.newInstance(title)));
                        break;
                    case R.id.focus_tab_layout:
//                        showDialog(title, content);
                        break;
                }
            }
        });
    }

    @Override
    public void showData(List<MyAnswers> myAnswerses) {
        mAdapter = new MyAnswerAdapter(R.layout.item_list_view, myAnswerses);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MyAnswers> list = adapter.getData();
                MyAnswers myAnswers = list.get(position);
                String name = myAnswers.getName();
                String title = myAnswers.getTitle();
                String content = myAnswers.getContent();
                start(AnswerToFragment.newInstance(name, title, content));
            }
        });
    }

}