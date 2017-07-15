package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/2/23 0023.
 * 邮箱:1083065285@qq.com
 */

public class MoreEntity {
    private String title;
    private int imageResourceId;

    public MoreEntity(String title) {
        this.title = title;
    }

    public MoreEntity(String title, int imageResourceId) {
        this.title = title;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
