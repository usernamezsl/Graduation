package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jess.arms.utils.UiUtils;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerHomeComponent;
import me.jessyan.mvparms.graduation.di.module.HomeModule;
import me.jessyan.mvparms.graduation.mvp.contract.HomeContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.Focus;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotContentBean;
import me.jessyan.mvparms.graduation.mvp.presenter.HomePresenter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.HomeAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.HomeDbAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartBrotherEvent;
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
 * 作者: 张少林 on 2017/2/24 0024.
 * 邮箱:1083065285@qq.com
 */

public class HomeFragment extends WEFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.menu_yellow)
    FloatingActionMenu mMenuYellow;
    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.coordinator)
    FrameLayout mCoordinator;
    private FloatingActionButton mFabWrite;
    private FloatingActionButton mFabAnswer;
    private FloatingActionButton mFabAsk;

    private HomeAdapter mAdapter;
    private HomeDbAdapter mHomeDbAdapter;
    private List<HotContentBean.DataBean> mList;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))//请将HomeModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_view, null, false);
    }








    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("主页");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mAdapter = new HomeAdapter(R.layout.item_home_view, mList);
        mRecyclerview.setVisibility(View.INVISIBLE);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mAdapter);
        mPresenter.getHomeDataFromDb();
        mPresenter.getHomeData();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHomeData();
                mPresenter.getHomeDataFromDb();
            }
        });
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > 5) {
                    if (dy > 0) {
                        mMenuYellow.hideMenu(true);
//                        EventBus.getDefault().post(new HideBottomBar("主页隐藏"));
                    } else {
                        mMenuYellow.showMenu(true);
//                        EventBus.getDefault().post(new ShowBottomBar("主页显示"));
                    }
                }
            }
        });
        initFloatButton();
    }

    private void initFloatButton() {
        mFabWrite = (FloatingActionButton) mMenuYellow.findViewById(R.id.fab_write);
        mFabAnswer = (FloatingActionButton) mMenuYellow.findViewById(R.id.fab_answer);
        mFabAsk = (FloatingActionButton) mMenuYellow.findViewById(R.id.fab_ask);
        mFabWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(WriteArticleFragment.newInstance()));
            }
        });
        mFabAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(AnswerFragment.newInstance()));
            }
        });
        mFabAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(AskFragment.newInstance()));
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
    public void showContent(List<HotContentBean.DataBean> dataBeen) {
        hideLoading();
        mRecyclerview.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(dataBeen);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<HotContentBean.DataBean> data = adapter.getData();
                HotContentBean.DataBean dataBean = data.get(position);
                String title = dataBean.getTitle();
                String content = dataBean.getContent();
                EventBus.getDefault().post(new StartBrotherEvent(ArticleDetailFragment.newInstance(title, content)));
            }
        });
    }

    @Override
    public void showContentFromDb(List<HomeData> homeDatas) {
        hideLoading();
        mRecyclerview.setVisibility(View.VISIBLE);
        mHomeDbAdapter = new HomeDbAdapter(R.layout.item_home_view, homeDatas);
        mHomeDbAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mHomeDbAdapter.isFirstOnly(false);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mHomeDbAdapter);
        mHomeDbAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<HomeData> list = adapter.getData();
                HomeData homeData = list.get(position);
                String title = homeData.getTitle();
                String content = homeData.getContent();
                EventBus.getDefault().post(new StartBrotherEvent(ArticleDetailFragment.newInstance(title, content)));
            }
        });
        mHomeDbAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<HomeData> list = adapter.getData();
                final String title = list.get(position).getTitle();
                final String content = list.get(position).getContent();
                switch (view.getId()) {
                    case R.id.comment:
                        EventBus.getDefault().post(new StartBrotherEvent(CommentFragment.newInstance(title)));
                        break;
                    case R.id.praise:
                        ToastUtils.showLongToastSafe("点赞成功~~");
                        break;
                    case R.id.focus_tab_layout:
                        showDialog(title, content);
                        break;
                }
            }
        });
    }

    private void showDialog(final String title, final String content) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(_mActivity);
        dialog.setTitle(" 关注问题之后可以随时了解该动态 T T");
        dialog.setMessage("是否关注 ? ?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Focus focus = new Focus();
                focus.setTitle(title);
                focus.setContent(content);
                List<Focus> focusList = DataSupport
                        .where("title = ?", focus.getTitle())
                        .find(Focus.class);
                if (focusList.isEmpty()) {
                    focus.saveThrows();
                }
                if (focus.isSaved()) {
                    ToastUtils.showLongToastSafe("关注成功~~");
                }
            }
        });
        dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    @Override
    public void showCommentSize(Integer integer) {

    }

    @Subscriber
    public void noyifyData(me.jessyan.mvparms.graduation.app.EventBus.NotifyDataHome notifyDataHome) {
        mPresenter.getHomeDataFromDb();
        mHomeDbAdapter.notifyDataSetChanged();
    }
}