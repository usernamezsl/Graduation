package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/2/22 0022.
 * 邮箱:1083065285@qq.com
 */

public class Column {
    private String title;
    private int imgResourse;

    public Column(String title, int imgResourse) {
        this.title = title;
        this.imgResourse = imgResourse;
    }

    public int getImgResourse() {
        return imgResourse;
    }

    public void setImgResourse(int imgResourse) {
        this.imgResourse = imgResourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
