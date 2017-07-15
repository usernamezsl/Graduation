package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Focus;

/**
 * 作者: 张少林 on 2017/4/14 0014.
 * 邮箱:1083065285@qq.com
 */

public class FocusProblemAdapter extends BaseQuickAdapter<Focus, BaseViewHolder> {
    public FocusProblemAdapter(int layoutResId, List<Focus> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Focus item) {
        helper.setText(R.id.tv_title_popular, item.getTitle())
                .setText(R.id.tv_content_popular, item.getContent());
    }
}
