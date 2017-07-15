package me.jessyan.mvparms.graduation.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import common.AppComponent;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.app.wechat.ChatMsgEntity;
import me.jessyan.mvparms.graduation.app.wechat.MyFragment;
import me.jessyan.mvparms.graduation.mvp.model.entity.Chat;
import me.jessyan.mvparms.graduation.mvp.model.entity.Msg;
import me.jessyan.mvparms.graduation.mvp.ui.activity.BottomViewActivity;
import me.jessyan.mvparms.graduation.mvp.ui.adapter.MsgAdapter;

/**
 * 作者: 张少林 on 2017/4/13 0013.
 * 邮箱:1083065285@qq.com
 */

public class MsgFragment extends MyFragment {
    private static final String ARG_MSG = "arg_msg";

    @BindView(R.id.nav_left_text)
    TextView mNavLeftText;
    @BindView(R.id.center_title)
    TextView mCenterTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recy)
    RecyclerView mRecy;
    @BindView(R.id.et_send)
    EditText mEtSend;
    @BindView(R.id.btn_send)
    Button mBtnSend;

    private Chat mChat;
    private MsgAdapter mAdapter;

    public static MsgFragment newInstance(Chat msg) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_MSG, msg);
        MsgFragment fragment = new MsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChat = getArguments().getParcelable(ARG_MSG);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(_mActivity).inflate(R.layout.wechat_fragment_tab_first_msg, null);
    }

    @Override
    protected void initData() {
        initToolbarMenu(mToolbar);
        mNavLeftText.setVisibility(View.VISIBLE);
        mNavLeftText.setText("聊天");
        mCenterTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        // 入场动画结束后执行  优化,防动画卡顿

        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecy.setHasFixedSize(true);
        mAdapter = new MsgAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEtSend.getText().toString().trim();
                if (TextUtils.isEmpty(str)) return;

                mAdapter.addMsg(new Msg(str));
                mEtSend.setText("");
                mRecy.scrollToPosition(mAdapter.getItemCount() - 1);
                ChatMsgEntity chatdata = new ChatMsgEntity();
                chatdata.setFrom("me");
                chatdata.setTyp(8);
                chatdata.setText(str);
                chatdata.setTo("校长");
                BottomViewActivity.myBinder.sendSocketChatMessage(chatdata);
            }
        });

        mAdapter.addMsg(new Msg(mChat.message));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mRecy.setAdapter(null);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }

    @Override
    public void recvMsgFormHandler(Bundle bundle) {

        ChatMsgEntity data = (ChatMsgEntity) bundle.getSerializable("CHAT");
        int type = data.getTyp();
        switch (type){
//            好友列表
            case 4:break;
            case 8:{
                //聊天

            }break;
            case 3://好友上线

                break;
        }
    }

}
