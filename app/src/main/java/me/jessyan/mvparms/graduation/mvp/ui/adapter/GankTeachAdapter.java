package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.GankTechBean;

/**
 * 作者: 张少林 on 2017/4/5 0005.
 * 邮箱:1083065285@qq.com
 */

public class GankTeachAdapter extends BaseQuickAdapter<GankTechBean.ResultsBean, BaseViewHolder> {


    public GankTeachAdapter(int layoutResId, List<GankTechBean.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankTechBean.ResultsBean item) {
//        String data = item.getPublishedAt();
//        int idx = data.indexOf(".");
//        data = data.substring(0, idx).replace("T", " ");
//        String dateTime = DateUtil.formatDateTime(data, true);
//        helper.setText(R.id.tv_tech_title, item.getDesc())
////                .setText(R.id.tv_tech_author,item.getWho())
//                .setText(R.id.tv_tech_time, dateTime)
//                .setImageResource(R.id.iv_tech_icon, R.mipmap.ic_android);
        helper.setText(R.id.tv_zhihu_hot_title, item.getDesc())
                .setImageResource(R.id.iv_zhihu_hot_item, R.drawable.android)
                .addOnClickListener(R.id.iv_like);
    }
}
