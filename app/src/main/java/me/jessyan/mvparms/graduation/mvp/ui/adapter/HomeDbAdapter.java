package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.litepal.crud.DataSupport;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Comment;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeData;
import me.jessyan.mvparms.graduation.mvp.ui.DataServer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: 张少林 on 2017/4/12 0012.
 * 邮箱:1083065285@qq.com
 */

public class HomeDbAdapter extends BaseQuickAdapter<HomeData, BaseViewHolder> {

    public HomeDbAdapter(int layoutResId, List<HomeData> data) {
        super(layoutResId, data);
    }

    public final int[] images = new int[]{R.drawable.cardpic, R.drawable.pic1, R.drawable.pic2};


    @Override
    protected void convert(final BaseViewHolder helper, final HomeData item) {
        helper.setText(R.id.title, item.getTitle())
                .setText(R.id.content, item.getContent())
                .addOnClickListener(R.id.comment)
                .addOnClickListener(R.id.praise)
                .addOnClickListener(R.id.focus_tab_layout);

        getCommentSize(helper, item);
        final ImageView imageView = helper.getView(R.id.pic);
//        showImage(imageView);
    }

    private void showImage(final ImageView imageView) {
        Observable.from(DataServer.getImageList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        imageView.setImageResource(integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showLongToastSafe(throwable.getMessage());
                    }
                });
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
                        TextView textView = helper.getView(R.id.comment);
                        textView.setText(integer + " " + "评论");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showLongToastSafe(throwable.getMessage());
                    }
                });
    }


}
