package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Column;

/**
 * 作者: 张少林 on 2017/2/22 0022.
 * 邮箱:1083065285@qq.com
 */

public class ColumnAdapter extends BaseQuickAdapter<Column, BaseViewHolder> {
    public ColumnAdapter(int layoutResId, List<Column> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Column column) {
        baseViewHolder.setText(R.id.tv_column, column.getTitle())
                .setImageResource(R.id.itemImg, column.getImgResourse());
    }
}
