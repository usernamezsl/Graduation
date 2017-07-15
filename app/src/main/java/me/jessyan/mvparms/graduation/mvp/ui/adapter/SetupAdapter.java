package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.SetupEntity;

/**
 * 作者: 张少林 on 2017/2/27 0027.
 * 邮箱:1083065285@qq.com
 */

public class SetupAdapter extends BaseQuickAdapter<SetupEntity, BaseViewHolder> {
    public SetupAdapter(int layoutResId, List<SetupEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SetupEntity setupEntity) {
        baseViewHolder.setText(R.id.tv_item_setup, setupEntity.getTitle())
                .setImageResource(R.id.img_setup, setupEntity.getImgResourceId());
    }
}
