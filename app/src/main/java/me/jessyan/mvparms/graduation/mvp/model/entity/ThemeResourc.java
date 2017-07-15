package me.jessyan.mvparms.graduation.mvp.model.entity;

import org.litepal.crud.DataSupport;

/**
 * 作者: 张少林 on 2017/4/9 0009.
 * 邮箱:1083065285@qq.com
 */

public class ThemeResourc extends DataSupport {
    private int id;
    private String image;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
