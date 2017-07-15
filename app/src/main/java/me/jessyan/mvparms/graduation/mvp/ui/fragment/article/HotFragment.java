package me.jessyan.mvparms.graduation.mvp.ui.fragment.article;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.github.clans.fab.FloatingActionButton;
import com.jess.arms.utils.UiUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.app.utils.GlideImageLoader;
import me.jessyan.mvparms.graduation.di.component.DaggerHotComponent;
import me.jessyan.mvparms.graduation.di.module.HotModule;
import me.jessyan.mvparms.graduation.mvp.contract.HotContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.Column;
import me.jessyan.mvparms.graduation.mvp.model.entity.Focus;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankItemBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotContentBean;
import me.jessyan.mvparms.graduation.mvp.model.entity.Theme;
import me.jessyan.mvparms.graduation.mvp.presenter.HotPresenter;
import me.jessyan.mvparms.graduation.mvp.ui.DataServer;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.ColumnAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.HotContentAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.HotDbAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.ThemeAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.event.StartBrotherEvent;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.AdwareFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.AloneFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.AndroidFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.ArticleDetailFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.CommentFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.DesignFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.IosFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.JobFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.MajorFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.MeizhiFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.MidNightFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.ToolResourceFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.UniversitityFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.WebFragment;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.ZhihuHotFragment;
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
 * 作者: 张少林 on 2017/2/21 0021.
 * 邮箱:1083065285@qq.com
 */

public class HotFragment extends WEFragment<HotPresenter> implements HotContract.View {

    private static final String TAG = "HotFragment";
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.sub_fab)
    FloatingActionButton mSubFab;
    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.nav_right_text_button)
    TextView mNavRightTextButton;


    private Banner mBanner;
    private View mHeaderView;
    private RecyclerView mRvColumn;
    private RecyclerView mReTheme;
    private GankItemBean mGankItemBean;

    private List<HotContentBean.DataBean> mList;
    private HotContentAdapter mAdapter;
    private HotDbAdapter mHotDbAdapter;
    private List<HomeData> mDatas;

    public static HotFragment newInstance() {
        HotFragment fragment = new HotFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerHotComponent
                .builder()
                .appComponent(appComponent)
                .hotModule(new HotModule(this))//请将HotModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hot, null, false);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("发现");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        mHeaderView = getHeaderView();
        mPresenter.getMeizhiData(100);
        initColumnView();
        initThemeView();
        initRecyclerView();
        mPresenter.getHotDataFromDb();
//        mPresenter.getHotContent();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mPresenter.getHotContent();
                mPresenter.getHotDataFromDb();
            }
        });
    }

    private void initRecyclerView() {
//        mList = new ArrayList<>();
//        mAdapter = new HotContentAdapter(R.layout.item_list_view, mList);
//        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        mAdapter.isFirstOnly(false);
//        mRecyclerView.setVisibility(View.INVISIBLE);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
//        mRecyclerView.setHasFixedSize(true);
//
//        mAdapter.addHeaderView(mHeaderView);
//        mRecyclerView.setAdapter(mAdapter);
//
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (Math.abs(dy) > 5) {
//                    if (dy > 0) {
//                        mSubFab.hide(true);
////                        EventBus.getDefault().post(new HideBottomBar("发现页隐藏"));
//                    } else {
//                        mSubFab.show(true);
////                        EventBus.getDefault().post(new ShowBottomBar("发现页显示"));
//                        Context context = Utils.getContext();
//                    }
//                }
//            }
//        });
        mDatas = new ArrayList<>();
        mRecyclerView.setVisibility(View.INVISIBLE);
        mHotDbAdapter = new HotDbAdapter(R.layout.item_list_view, mDatas);
        mHotDbAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mHotDbAdapter.isFirstOnly(false);
        mHotDbAdapter.addHeaderView(mHeaderView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mHotDbAdapter);
    }

    private void initThemeView() {
        mReTheme = (RecyclerView) mHeaderView.findViewById(R.id.rv_theme);
        ThemeAdapter themeAdapter = new ThemeAdapter(R.layout.item_theme_view, DataServer.getThemeData());
        LinearLayoutManager themeLayoutManager = new LinearLayoutManager(_mActivity);
        themeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mReTheme.setLayoutManager(themeLayoutManager);
        mReTheme.setAdapter(themeAdapter);
        mReTheme.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Theme theme = (Theme) baseQuickAdapter.getData().get(i);
                switch (theme.getThemeTitle()) {
                    case "瞎扯":
                        EventBus.getDefault().post(new StartBrotherEvent(ZhihuHotFragment.newInstance()));
                        break;
                    case "选个好专业":
                        EventBus.getDefault().post(new StartBrotherEvent(MajorFragment.newInstance()));
                        break;
                    case "读读日报24小时热门":
                        EventBus.getDefault().post(new StartBrotherEvent(AloneFragment.newInstance()));
                        break;
                    case "职人介绍所":
                        EventBus.getDefault().post(new StartBrotherEvent(JobFragment.newInstance()));
                        break;
                    case "读读日报推荐":
                        EventBus.getDefault().post(new StartBrotherEvent(UniversitityFragment.newInstance()));
                        break;
                    case "这里是广告":
                        EventBus.getDefault().post(new StartBrotherEvent(AdwareFragment.newInstance()));
                        break;
                    case "深夜惊奇":
                        EventBus.getDefault().post(new StartBrotherEvent(MidNightFragment.newInstance()));
                        break;
                }
            }
        });
    }

    private void initColumnView() {
        mRvColumn = (RecyclerView) mHeaderView.findViewById(R.id.rv_column);
        LinearLayoutManager columnLayoutManager = new LinearLayoutManager(_mActivity);
        columnLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvColumn.setLayoutManager(columnLayoutManager);
        ColumnAdapter columnAdapter = new ColumnAdapter(R.layout.item_column_view, DataServer.getColumnData());
        mRvColumn.setAdapter(columnAdapter);
        columnAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<Column> data = adapter.getData();
                String title = data.get(position).getTitle();
                switch (title) {
                    case "android":
                        EventBus.getDefault().post(new StartBrotherEvent(AndroidFragment.newInstance()));
                        break;
                    case "ios":
                        EventBus.getDefault().post(new StartBrotherEvent(IosFragment.newInstance()));
                        break;
                    case "前端":
                        EventBus.getDefault().post(new StartBrotherEvent(WebFragment.newInstance()));
                        break;
                    case "工具资源":
                        EventBus.getDefault().post(new StartBrotherEvent(ToolResourceFragment.newInstance()));
                        break;
                    case "设计":
                        EventBus.getDefault().post(new StartBrotherEvent(DesignFragment.newInstance()));
                        break;
                }
            }
        });
    }

    /**
     * 加载头布局
     *
     * @return
     */
    private View getHeaderView() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.hot_header_view, null);
        return headView;
    }


    @Override
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
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
    public void showBanner(GankItemBean gankItemBean) {
        mBanner = (Banner) mHeaderView.findViewById(R.id.banner);
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String url = gankItemBean.getResults().get(i).getUrl();
            urlList.add(url);
        }
        mBanner.setImages(urlList)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        EventBus.getDefault().post(new StartBrotherEvent(MeizhiFragment.newInstance()));
                    }
                })
                .start();
    }

    @Override
    public void showContent(List<HotContentBean.DataBean> dataBeen) {
        hideLoading();
        mRecyclerView.setVisibility(View.VISIBLE);
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
                EventBus.getDefault().post(new StartBrotherEvent(ArticleDetailFragment.newInstance(title, content)));
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
                        showDialog(title, content);
                        break;
                }
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > 5) {
                    if (dy > 0) {
                        mSubFab.hide(true);
//                        EventBus.getDefault().post(new HideBottomBar("发现页隐藏"));
                    } else {
                        mSubFab.show(true);
//                        EventBus.getDefault().post(new ShowBottomBar("发现页显示"));
                        Context context = Utils.getContext();
                    }
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

    @Subscriber
    public void notifyData(me.jessyan.mvparms.graduation.app.EventBus.NotifyDataHot notifyDataHot) {
        mPresenter.getHotDataFromDb();
        mHotDbAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.nav_right_text_button)
    public void onClick() {
    }
}