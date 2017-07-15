package me.jessyan.mvparms.graduation.mvp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 作者: 张少林 on 2017/2/23 0023.
 * 邮箱:1083065285@qq.com
 */

public class MoreMultipleItem implements MultiItemEntity {
    public static final int ITEM_DOCORATION = 1;
    public static final int ITEM_MORE_LIST = 2;
    public static final int ITEM_DOCORATION_TWO = 3;
    private int itemType;
    private List<MoreEntity> mMoreEntities;

    public MoreMultipleItem() {
    }

    public MoreMultipleItem(int itemType) {
        this.itemType = itemType;
    }

    public MoreMultipleItem(int itemType, List<MoreEntity> moreEntities) {
        this.itemType = itemType;
        mMoreEntities = moreEntities;
    }

    public List<MoreEntity> getMoreEntities() {
        return mMoreEntities;
    }

    public void setMoreEntities(List<MoreEntity> moreEntities) {
        mMoreEntities = moreEntities;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
