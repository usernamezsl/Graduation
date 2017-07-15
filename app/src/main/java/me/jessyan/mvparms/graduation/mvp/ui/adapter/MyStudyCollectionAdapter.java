package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyStudyCollection;

/**
 * 作者: 张少林 on 2017/4/14 0014.
 * 邮箱:1083065285@qq.com
 */

public class MyStudyCollectionAdapter extends BaseQuickAdapter<MyStudyCollection, BaseViewHolder> {
    public MyStudyCollectionAdapter(int layoutResId, List<MyStudyCollection> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyStudyCollection item) {
        helper.setText(R.id.tv_zhihu_hot_title, item.getDesc())
                .setImageResource(R.id.iv_zhihu_hot_item, R.drawable.android)
                .addOnClickListener(R.id.iv_like);
    }
}
