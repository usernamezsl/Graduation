package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import java.util.List;

import common.WEApplication;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.ZhihuHotBean;

/**
 * 作者: 张少林 on 2017/3/22 0022.
 * 邮箱:1083065285@qq.com
 */

public class ZhihuHotAdapter extends BaseQuickAdapter<ZhihuHotBean.RecentBean, BaseViewHolder> {


    public ZhihuHotAdapter(int layoutResId, List<ZhihuHotBean.RecentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ZhihuHotBean.RecentBean recentBean) {
        baseViewHolder.setText(R.id.tv_zhihu_hot_title, recentBean.getTitle());
        ((WEApplication) BaseApplication.getInstance())
                .getAppComponent()
                .imageLoader()
                .loadImage(mContext, GlideImageConfig.builder()
                        .url(recentBean.getThumbnail())
                        .imageView(((ImageView) baseViewHolder.getView(R.id.iv_zhihu_hot_item)))
                        .placeholder(R.drawable.android)
                        .errorPic(R.drawable.android)
                        .build());
    }
}
