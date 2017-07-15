package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.mvp.model.entity.MoreEntity;

/**
 * 作者: 张少林 on 2017/2/23 0023.
 * 邮箱:1083065285@qq.com
 */

public class ItemMoreAdapter extends BaseQuickAdapter<MoreEntity, BaseViewHolder> {
    public ItemMoreAdapter(int layoutResId, List<MoreEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MoreEntity moreEntity) {
//        baseViewHolder.setText(R.id., moreEntity.getTitle());
    }
}
