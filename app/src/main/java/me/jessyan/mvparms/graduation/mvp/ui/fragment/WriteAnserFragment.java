package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.WEFragment;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Answer;

/**
 * 作者: 张少林 on 2017/4/18 0018.
 * 邮箱:1083065285@qq.com
 */

public class WriteAnserFragment extends WEFragment {

    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_title)
    EditText mEdtTitle;
    @BindView(R.id.bottom_view)
    LinearLayout mBottomView;
    @BindView(R.id.nav_right_text_button)
    TextView mNavRightTextButton;
    public static final String ARG_TITLE = "arg_title";
    public String title = null;

    public static WriteAnserFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        WriteAnserFragment writeAnserFragment = new WriteAnserFragment();
        writeAnserFragment.setArguments(bundle);
        return writeAnserFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        title = arguments.getString(ARG_TITLE);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.frg_write_answer_view, null);
    }

    @Override
    protected void initData() {
        showSoftInput(mEdtTitle);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("撰写回答");
        mCenterTitle.setVisibility(View.GONE);
        mNavRightTextButton.setVisibility(View.VISIBLE);
        mNavRightTextButton.setText("发送");
        mNavRightTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer answer = new Answer();
                String data = mEdtTitle.getText().toString();
                answer.setAnswer(data);
                answer.setTitle(title);
                List<Answer> answerList = DataSupport
                        .where("title = ?", title)
                        .find(Answer.class);
                if (answerList.isEmpty()) {
                    answer.saveThrows();
                }
                if (answer.isSaved()) {
                    EventBus.getDefault().post(new me.jessyan.mvparms.graduation.app.EventBus.RefreshData("刷新数据"));
                    pop();
                }
            }
        });
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {

    }

}
