package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Draught;

/**
 * 作者: 张少林 on 2017/4/11 0011.
 * 邮箱:1083065285@qq.com
 */

public class DraughtAdapter extends BaseQuickAdapter<Draught, BaseViewHolder> {
    public DraughtAdapter(int layoutResId, List<Draught> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Draught item) {
        helper.setText(R.id.tv_title_popular, item.getTitle())
                .setText(R.id.tv_content_popular, item.getContent());
    }
}
