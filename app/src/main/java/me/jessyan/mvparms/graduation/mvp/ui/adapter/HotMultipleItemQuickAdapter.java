package me.jessyan.mvparms.graduation.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotMultipleItem;

/**
 * 作者: 张少林 on 2017/2/22 0022.
 * 邮箱:1083065285@qq.com
 */

public class HotMultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<HotMultipleItem, BaseViewHolder> {
    private static final String TAG = "HotMultipleItemQuickAda";
    private RecyclerView mBaseViewHolderView;

    public HotMultipleItemQuickAdapter(Context context, List<HotMultipleItem> data) {
        super(data);
        addItemType(HotMultipleItem.POPULAR_CONTEN_LIST, R.layout.popular_content_list);
        addItemType(HotMultipleItem.THEME_LIST, R.layout.theme_list_view);
        addItemType(HotMultipleItem.COLUMN_LIST, R.layout.column_list_view);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, HotMultipleItem hotMultipleItem) {
        switch (baseViewHolder.getItemViewType()) {
            case HotMultipleItem.COLUMN_LIST:
                RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.rv_column);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                ColumnAdapter columnAdapter = new ColumnAdapter(R.layout.item_column_view, hotMultipleItem.getColumns());
                recyclerView.setAdapter(columnAdapter);
                break;
            case HotMultipleItem.THEME_LIST:
                RecyclerView view = (RecyclerView) baseViewHolder.getView(R.id.rv_theme);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
                layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                view.setLayoutManager(layoutManager1);
                ThemeAdapter themeAdapter = new ThemeAdapter(R.layout.item_theme_view, hotMultipleItem.getThemes());
                view.setAdapter(themeAdapter);
                break;
            case HotMultipleItem.POPULAR_CONTEN_LIST:
                RecyclerView recyclerView1 = (RecyclerView) baseViewHolder.getView(R.id.rv_popular_content);
                recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
                PopularCotentAdapter popularCotentAdapter = new PopularCotentAdapter(R.layout.item_list_view, hotMultipleItem.getPopularContents());
                recyclerView1.setAdapter(popularCotentAdapter);
                break;
        }
    }
}
