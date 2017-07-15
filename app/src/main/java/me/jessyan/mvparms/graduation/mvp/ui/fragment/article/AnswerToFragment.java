package me.jessyan.mvparms.graduation.mvp.ui.fragment.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.app.EventBus;
import me.jessyan.mvparms.graduation.mvp.model.entity.Answer;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.AnswerAdapter;
import me.jessyan.mvparms.graduation.mvp.ui.fragment.WriteAnserFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * 作者: 张少林 on 2017/4/18 0018.
 * 邮箱:1083065285@qq.com
 */

public class AnswerToFragment extends WEFragment {

    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_CONTENT = "arg_content";
    public static final String ARG_NAME = "arg_name";
    public String title;
    public String content;
    public String name;

    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.nav_right_text_button)
    TextView mNavRightTextButton;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.menu)
    ImageView mMenu;
    @BindView(R.id.img_popular_item)
    CircleImageView mImgPopularItem;
    @BindView(R.id.tv_name_poplar)
    TextView mTvNamePoplar;
    @BindView(R.id.tv_title_popular)
    TextView mTvTitlePopular;
    @BindView(R.id.tv_content_popular)
    TextView mTvContentPopular;
    @BindView(R.id.tv_praise_popular)
    TextView mTvPraisePopular;
    @BindView(R.id.tv_comment_popular)
    TextView mTvCommentPopular;
    @BindView(R.id.focus_tab_layout)
    TextView mFocusTabLayout;
    @BindView(R.id.tv_ask_answer)
    TextView mTvAskAnswer;
    @BindView(R.id.tv_answer)
    TextView mTvAnswer;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    public AnswerAdapter mAdapter;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    public static AnswerToFragment newInstance(String title, String content) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        bundle.putString(ARG_CONTENT, content);
        AnswerToFragment fragment = new AnswerToFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AnswerToFragment newInstance(String name, String title, String content) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        bundle.putString(ARG_CONTENT, content);
        bundle.putString(ARG_NAME, name);
        AnswerToFragment fragment = new AnswerToFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        title = arguments.getString(ARG_TITLE);
        content = arguments.getString(ARG_CONTENT);
        name = arguments.getString(ARG_NAME);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.frg_answer_view, null, false);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("问题回答");
        mCenterTitle.setVisibility(View.GONE);
        mTvTitlePopular.setText(title);
        mTvContentPopular.setText(content);
        mTvNamePoplar.setText(name);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {

        mTvAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(WriteAnserFragment.newInstance(title));
            }
        });
        getDataFromDb();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromDb();
            }
        });
    }

    public void getDataFromDb() {
        Observable.create(new Observable.OnSubscribe<List<Answer>>() {
            @Override
            public void call(Subscriber<? super List<Answer>> subscriber) {
                List<Answer> answerList = DataSupport
                        .where("title = ?", title)
                        .find(Answer.class);
                subscriber.onNext(answerList);
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
                .subscribe(new Action1<List<Answer>>() {
                    @Override
                    public void call(List<Answer> answers) {
                        mRefresh.setRefreshing(false);
                        initAdapter(answers);
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRefresh.setRefreshing(false);
                    }
                });
    }

    private void initAdapter(List<Answer> answers) {
        View emptyView = LayoutInflater.from(_mActivity).inflate(R.layout.empty_answer_view, null);
        mAdapter = new AnswerAdapter(R.layout.item_answer_list, answers);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mAdapter.setEmptyView(emptyView);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mAdapter);

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @org.simple.eventbus.Subscriber
    public void setRefresh(EventBus.RefreshData refreshData) {
        getDataFromDb();
        mAdapter.notifyDataSetChanged();
    }

}