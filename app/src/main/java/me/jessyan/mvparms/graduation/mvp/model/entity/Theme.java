package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/2/22 0022.
 * 邮箱:1083065285@qq.com
 */

public class Theme {
    private String themeTitle;
    private int imageId;

    public Theme(String themeTitle, int imageId) {
        this.themeTitle = themeTitle;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getThemeTitle() {
        return themeTitle;
    }

    public void setThemeTitle(String themeTitle) {
        this.themeTitle = themeTitle;
    }
}
