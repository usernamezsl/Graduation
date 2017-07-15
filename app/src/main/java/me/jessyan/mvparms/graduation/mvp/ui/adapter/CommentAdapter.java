package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.Comment;

/**
 * 作者: 张少林 on 2017/3/29 0029.
 * 邮箱:1083065285@qq.com
 */

public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {
    public CommentAdapter(int layoutResId, List<Comment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.tv_title_popular, item.getTitle())
                .setText(R.id.tv_content_popular, item.getComment());
    }
}
