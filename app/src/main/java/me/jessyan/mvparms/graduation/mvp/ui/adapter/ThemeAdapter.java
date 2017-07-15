package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Theme;

/**
 * 作者: 张少林 on 2017/2/22 0022.
 * 邮箱:1083065285@qq.com
 */

public class ThemeAdapter extends BaseQuickAdapter<Theme, BaseViewHolder> {
    public ThemeAdapter(int layoutResId, List<Theme> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Theme theme) {
        baseViewHolder.setText(R.id.tv_theme_item, theme.getThemeTitle())
                .setImageResource(R.id.img_theme_item, theme.getImageId());
    }
}
