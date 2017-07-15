package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.jess.arms.utils.UiUtils;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.di.component.DaggerAskComponent;
import me.jessyan.mvparms.graduation.di.module.AskModule;
import me.jessyan.mvparms.graduation.mvp.contract.AskContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.Draught;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import me.jessyan.mvparms.graduation.mvp.presenter.AskPresenter;

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

public class AskFragment extends WEFragment<AskPresenter> implements AskContract.View {


    @BindView(R.id.edt_title)
    EditText mEdtTitle;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.nav_right_text_button)
    TextView mNavRightTextButton;
    @BindView(R.id.bottom_view)
    LinearLayout mBottomView;
    @BindView(R.id.first)
    ScrollView mFirst;
    @BindView(R.id.edt_content)
    EditText mEdtContent;

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_CONTENT = "arg_content";
    private String title;
    private String content;

    public static AskFragment newInstance() {
        AskFragment fragment = new AskFragment();
        return fragment;
    }

    public static AskFragment newInstance(String title, String content) {
        AskFragment fragment = new AskFragment();
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
        DaggerAskComponent
                .builder()
                .appComponent(appComponent)
                .askModule(new AskModule(this))//请将AskModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ask_view, null, false);
    }

    @Override
    protected void initData() {
        showSoftInput(mEdtTitle);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("提问");
        mCenterTitle.setVisibility(View.GONE);
        mNavRightTextButton.setVisibility(View.VISIBLE);
        mNavRightTextButton.setText("发布");
        mEdtTitle.setText(title);
        mEdtContent.setText(content);
        sendQution();
    }

    /**
     * 发布问题逻辑处理
     */
    private void sendQution() {
        mNavRightTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(_mActivity);
                dialog.setTitle(" 发布问题 ？ ？");
                dialog.setMessage("是否直接发布 ? ?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("存为草稿", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Draught draught = new Draught();
                        String title = mEdtTitle.getText().toString();
                        String content = mEdtContent.getText().toString();
                        if (title != null && content != null) {
                            draught.setTitle(title);
                            draught.setContent(content);
                            draught.saveThrows();
                            if (draught.isSaved()) {
                                ToastUtils.showLongToastSafe("保存成功~~");
                                pop();
                            }
                        }
                    }
                });
                dialog.setNegativeButton("直接发布", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeData homeData = new HomeData();
                        String title = mEdtTitle.getText().toString();
                        String content = mEdtContent.getText().toString();
                        if (title != null && content != null) {
                            homeData.setTitle(title);
                            homeData.setContent(content);
                            List<HomeData> list = DataSupport
                                    .where("title = ?", title)
                                    .find(HomeData.class);
                            if (list.isEmpty()) {
                                homeData.saveThrows();
                            }
                            if (homeData.isSaved()) {
                                EventBus.getDefault().post(new me.jessyan.mvparms.graduation.app.EventBus.NotifyDataHome());
                                EventBus.getDefault().post(new me.jessyan.mvparms.graduation.app.EventBus.NotifyDataHot());
                                pop();
                            }
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
//        mEdtTitle.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    private int heightDifference;
//
//                    @Override
//                    public void onGlobalLayout() {
//                        Rect r = new Rect();
//                        mLayout.getWindowVisibleDisplayFrame(r);
//                        int screenHeight = mLayout.getRootView().getHeight();
//                        heightDifference = screenHeight - (r.bottom - r.top);
//                        if (heightDifference != 0) {
////                            mBottomView.setLayoutParams(new AbsoluteLayout.LayoutParams());
//                        }
//                    }
//                });
    }


    @Override
    public void showLoading() {

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

}