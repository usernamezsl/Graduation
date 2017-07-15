package me.jessyan.mvparms.graduation.mvp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 作者: 张少林 on 2017/2/22 0022.
 * 邮箱:1083065285@qq.com
 */
public class HotMultipleItem implements MultiItemEntity {
    public static final int COLUMN_LIST = 1;
    public static final int THEME_LIST = 3;
    public static final int POPULAR_CONTEN_LIST = 5;
    private int itemType;
    private List<Column> mColumns;
    private List<Theme> mThemes;
    private List<PopularContent> mPopularContents;

    public HotMultipleItem(int itemType) {
        this.itemType = itemType;
    }

    public HotMultipleItem(int itemType, List<Column> columns, List<Theme> themes, List<PopularContent> popularContents) {
        this.itemType = itemType;
        mColumns = columns;
        mThemes = themes;
        mPopularContents = popularContents;
    }

    public HotMultipleItem(int itemType,List<Theme> themes){

    }
    public List<Column> getColumns() {
        return mColumns;
    }

    public void setColumns(List<Column> columns) {
        mColumns = columns;
    }

    public List<Theme> getThemes() {
        return mThemes;
    }

    public void setThemes(List<Theme> themes) {
        mThemes = themes;
    }

    public List<PopularContent> getPopularContents() {
        return mPopularContents;
    }

    public void setPopularContents(List<PopularContent> popularContents) {
        mPopularContents = popularContents;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
