package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import java.util.List;

import common.WEApplication;
import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankItemBean;

/**
 * 作者: 张少林 on 2017/4/21 0021.
 * 邮箱:1083065285@qq.com
 */

public class MeizhiAdapter extends BaseQuickAdapter<GankItemBean.ResultsBean, BaseViewHolder> {


    public MeizhiAdapter(int layoutResId, List<GankItemBean.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GankItemBean.ResultsBean resultsBean) {
        ImageView imageView = baseViewHolder.getView(R.id.iv_girl);
        ((WEApplication) BaseApplication.getInstance())
                .getAppComponent()
                .imageLoader()
                .loadImage(mContext, GlideImageConfig.builder()
                        .url(resultsBean.getUrl())
                        .placeholder(R.drawable.beauty)
                        .errorPic(R.drawable.beauty)
                        .imageView(imageView)
                        .build());
    }
}
