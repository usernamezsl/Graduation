package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.MyAnswers;

/**
 * 作者: 张少林 on 2017/4/18 0018.
 * 邮箱:1083065285@qq.com
 */

public class MyAnswerAdapter extends BaseQuickAdapter<MyAnswers, BaseViewHolder> {
    public MyAnswerAdapter(int layoutResId, List<MyAnswers> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyAnswers item) {
        helper.setText(R.id.tv_name_poplar, item.getName())
                .setText(R.id.tv_title_popular, item.getTitle())
                .setText(R.id.tv_content_popular, item.getContent());
    }
}
