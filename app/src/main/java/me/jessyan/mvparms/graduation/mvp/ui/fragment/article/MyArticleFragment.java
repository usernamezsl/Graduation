package me.jessyan.mvparms.graduation.mvp.ui.fragment.article;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerMyArticleComponent;
import me.jessyan.mvparms.graduation.di.module.MyArticleModule;
import me.jessyan.mvparms.graduation.mvp.contract.MyArticleContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotContentBean;
import me.jessyan.mvparms.graduation.mvp.presenter.MyArticlePresenter;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.HotDbAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.ArticleDetailFragment;
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
 * 作者: 张少林 on 2017/4/10 0010.
 * 邮箱:1083065285@qq.com
 */

public class MyArticleFragment extends WEFragment<MyArticlePresenter> implements MyArticleContract.View {


    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_my_article)
    RecyclerView mRvMyArticle;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private List<HomeData> mList;
    private HotDbAdapter mAdapter;

    public static MyArticleFragment newInstance() {
        MyArticleFragment fragment = new MyArticleFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMyArticleComponent
                .builder()
                .appComponent(appComponent)
                .myArticleModule(new MyArticleModule(this))//请将MyArticleModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_myarticle_view, null, false);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("我的文章");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mAdapter = new HotDbAdapter(R.layout.item_list_view, mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mRvMyArticle.setVisibility(View.INVISIBLE);
        mRvMyArticle.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRvMyArticle.setHasFixedSize(true);
        mRvMyArticle.setAdapter(mAdapter);
        mPresenter.getMyArticleFromDb();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMyArticleFromDb();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<HomeData> list = adapter.getData();
                HomeData homeData = list.get(position);
                String title = homeData.getTitle();
                String content = homeData.getContent();
                start(ArticleDetailFragment.newInstance(title, content));
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
                        mRefresh.setRefreshing(true);
                    }
                });
    }

    @Override
    public void hideLoading() {
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
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

    }

    @Override
    public void showContentFromDb(List<HomeData> homeDatas) {
        hideLoading();
        mRvMyArticle.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(homeDatas);
        mAdapter.notifyDataSetChanged();
    }

}