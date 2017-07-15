package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotEntity;

/**
 * 作者: 张少林 on 2017/3/7 0007.
 * 邮箱:1083065285@qq.com
 */

public class HotAdapter extends BaseQuickAdapter<HotEntity,BaseViewHolder> {
    public HotAdapter(int layoutResId, List<HotEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HotEntity hotEntity) {
        baseViewHolder.setImageResource(R.id.img_popular_item, hotEntity.getPhpotoId())
                .setText(R.id.tv_name_poplar,hotEntity.getFromTitle())
                .setText(R.id.tv_title_popular, hotEntity.getTitle())
                .setText(R.id.tv_comment_popular, hotEntity.getContent())
                .setText(R.id.tv_praise_popular, hotEntity.getPraise())
                .setText(R.id.tv_comment_popular, hotEntity.getComment());
    }
}
