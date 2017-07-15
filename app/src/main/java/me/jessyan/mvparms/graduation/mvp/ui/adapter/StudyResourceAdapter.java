package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import java.util.List;

import common.WEApplication;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.ThemeResourc;

/**
 * 作者: 张少林 on 2017/4/11 0011.
 * 邮箱:1083065285@qq.com
 */

public class StudyResourceAdapter extends BaseQuickAdapter<ThemeResourc, BaseViewHolder> {
    public StudyResourceAdapter(int layoutResId, List<ThemeResourc> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeResourc item) {
        helper.setText(R.id.tv_zhihu_hot_title, item.getTitle())
                .addOnClickListener(R.id.iv_like);
        ((WEApplication) BaseApplication.getInstance())
                .getAppComponent()
                .imageLoader()
                .loadImage(mContext, GlideImageConfig.builder()
                        .url(item.getImage())
                        .imageView(((ImageView) helper.getView(R.id.iv_zhihu_hot_item)))
                        .placeholder(R.drawable.android)
                        .errorPic(R.drawable.android)
                        .build());
    }
}
