package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.PopularContent;

/**
 * 作者: 张少林 on 2017/2/22 0022.
 * 邮箱:1083065285@qq.com
 */

public class PopularCotentAdapter extends BaseQuickAdapter<PopularContent, BaseViewHolder> {
    public PopularCotentAdapter(int layoutResId, List<PopularContent> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PopularContent popularContent) {
        baseViewHolder.setImageResource(R.id.img_popular_item, popularContent.getPhpotoId())
                .setText(R.id.tv_name_poplar, popularContent.getFromTitle())
                .setText(R.id.tv_title_popular, popularContent.getTitle())
                .setText(R.id.tv_comment_popular, popularContent.getContent())
                .setText(R.id.tv_praise_popular, popularContent.getPraise())
                .setText(R.id.tv_comment_popular, popularContent.getComment());
    }
}
