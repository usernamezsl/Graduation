package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/2/27 0027.
 * 邮箱:1083065285@qq.com
 */

public class SetupEntity {
    private String title;
    private int imgResourceId;

    public SetupEntity(String title, int imgResourceId) {
        this.title = title;
        this.imgResourceId = imgResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public void setImgResourceId(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }
}
