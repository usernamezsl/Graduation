package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.api.User;
import me.jessyan.mvparms.graduation.mvp.model.entity.Comment;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: 张少林 on 2017/4/12 0012.
 * 邮箱:1083065285@qq.com
 */

public class HotDbAdapter extends BaseQuickAdapter<HomeData, BaseViewHolder> {

    private TextView mName;

    public HotDbAdapter(int layoutResId, List<HomeData> data) {
        super(layoutResId, data);
    }

    private static final String TAG = "HotDbAdapter";

    @Override
    protected void convert(BaseViewHolder helper, HomeData item) {
        EventBus.getDefault().registerSticky(this);
        helper.setText(R.id.tv_title_popular, item.getTitle())
                .setText(R.id.tv_content_popular, item.getContent())
                .addOnClickListener(R.id.tv_praise_popular)
                .addOnClickListener(R.id.tv_comment_popular)
                .addOnClickListener(R.id.focus_tab_layout);
        getCommentSize(helper, item);
        mName = helper.getView(R.id.tv_name_poplar);
    }

    private void getCommentSize(final BaseViewHolder helper, final HomeData item) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                List<Comment> comments = DataSupport
                        .where("title = ?", item.getTitle())
                        .find(Comment.class);
                int size = comments.size();
                subscriber.onNext(size);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        TextView textView = helper.getView(R.id.tv_comment_popular);
                        textView.setText(integer + " " + "评论");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showLongToastSafe(throwable.getMessage());
                    }
                });
    }

    @org.simple.eventbus.Subscriber
    public void getUsers(User user) {
        mName.setText(user.getName());
    }
}
