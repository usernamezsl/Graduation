package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import me.jessyan.mvparms.graduation.di.component.DaggerWriteArticleComponent;
import me.jessyan.mvparms.graduation.di.module.WriteArticleModule;
import me.jessyan.mvparms.graduation.mvp.contract.WriteArticleContract;
import me.jessyan.mvparms.graduation.mvp.model.entity.Draught;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import me.jessyan.mvparms.graduation.mvp.presenter.WriteArticlePresenter;

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

public class WriteArticleFragment extends WEFragment<WriteArticlePresenter> implements WriteArticleContract.View {


    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.nav_right_text_button)
    TextView mNavRightTextButton;
    @BindView(R.id.edt_title)
    EditText mEdtTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static WriteArticleFragment newInstance() {
        WriteArticleFragment fragment = new WriteArticleFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerWriteArticleComponent
                .builder()
                .appComponent(appComponent)
                .writeArticleModule(new WriteArticleModule(this))//请将WriteArticleModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_write_article, null, false);
    }

    @Override
    protected void initData() {
        showSoftInput(mEdtTitle);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("分享");
        mCenterTitle.setVisibility(View.GONE);
        mNavRightTextButton.setVisibility(View.VISIBLE);
        mNavRightTextButton.setText("发布文章");
        mNavRightTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(_mActivity);
                dialog.setTitle(" 发布文章 ？ ？");
                dialog.setMessage("是否直接发布 ? ?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("存为草稿", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Draught draught = new Draught();
                        String content = mEdtTitle.getText().toString();
                        String title = content.substring(0, 10);
                        if (content != null) {
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
                        String content = mEdtTitle.getText().toString();
                        if (content != null) {
                            homeData.setTitle(content);
                            homeData.setContent(content);
                            List<HomeData> list = DataSupport
                                    .where("title = ?", homeData.getTitle())
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