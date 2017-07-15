package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotContentBean;

/**
 * 作者: 张少林 on 2017/3/2 0002.
 * 邮箱:1083065285@qq.com
 */

public class HomeAdapter extends BaseQuickAdapter<HotContentBean.DataBean, BaseViewHolder> {

    public HomeAdapter(int layoutResId, List<HotContentBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotContentBean.DataBean item) {
        helper.setText(R.id.title, item.getTitle())
                .setText(R.id.content, item.getContent());
    }
}
