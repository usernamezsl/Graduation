package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;

/**
 * 作者: 张少林 on 2017/2/27 0027.
 * 邮箱:1083065285@qq.com
 */

public class MainPageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MainPageAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv, s);
    }
}
