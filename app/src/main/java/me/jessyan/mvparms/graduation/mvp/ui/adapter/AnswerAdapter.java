package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Answer;

/**
 * 作者: 张少林 on 2017/4/18 0018.
 * 邮箱:1083065285@qq.com
 */

public class AnswerAdapter extends BaseQuickAdapter<Answer, BaseViewHolder> {

    public AnswerAdapter(int layoutResId, List<Answer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Answer item) {
        helper.setText(R.id.tv_content_popular, item.getAnswer());
    }
}
