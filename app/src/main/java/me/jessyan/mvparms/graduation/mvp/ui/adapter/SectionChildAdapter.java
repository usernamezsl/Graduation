package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import java.util.List;

import common.WEApplication;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.SectionChildListBean;

/**
 * 作者: 张少林 on 2017/3/23 0023.
 * 邮箱:1083065285@qq.com
 */

public class SectionChildAdapter extends BaseQuickAdapter<SectionChildListBean.StoriesBean,BaseViewHolder> {
    public SectionChildAdapter(int layoutResId, List<SectionChildListBean.StoriesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SectionChildListBean.StoriesBean storiesBean) {
        baseViewHolder.setText(R.id.tv_zhihu_hot_title, storiesBean.getTitle())
                .addOnClickListener(R.id.iv_like);
        ((WEApplication) BaseApplication.getInstance())
                .getAppComponent()
                .imageLoader()
                .loadImage(mContext, GlideImageConfig.builder()
                        .url(storiesBean.getImages().get(0))
                        .imageView(((ImageView) baseViewHolder.getView(R.id.iv_zhihu_hot_item)))
                        .placeholder(R.drawable.android)
                        .errorPic(R.drawable.android)
                        .build());
    }
}
