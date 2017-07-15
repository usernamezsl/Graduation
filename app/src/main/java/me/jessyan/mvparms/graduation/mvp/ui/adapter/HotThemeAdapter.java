package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Subject;

/**
 * 作者: 张少林 on 2017/4/9 0009.
 * 邮箱:1083065285@qq.com
 */

public class HotThemeAdapter extends BaseQuickAdapter<Subject, BaseViewHolder> {

    public HotThemeAdapter(int layoutResId, List<Subject> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Subject item) {
        helper.setText(R.id.tv_zhihu_hot_title, item.getDesc())
                .setImageResource(R.id.iv_zhihu_hot_item, R.drawable.android);
//        ((WEApplication) BaseApplication.getInstance())
//                .getAppComponent()
//                .imageLoader()
//                .loadImage(mContext, GlideImageConfig.builder()
//                        .imageView(((ImageView) helper.getView(R.id.iv_zhihu_hot_item)))
//                        .placeholder(R.drawable.android)
//                        .errorPic(R.drawable.android)
//                        .build());
    }
}
