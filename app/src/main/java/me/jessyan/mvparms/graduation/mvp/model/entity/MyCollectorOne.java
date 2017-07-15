package me.jessyan.mvparms.graduation.mvp.model.entity;

import org.litepal.crud.DataSupport;

/**
 * 作者: 张少林 on 2017/4/11 0011.
 * 邮箱:1083065285@qq.com
 */

public class MyCollectorOne extends DataSupport {
    private int id;
    private String title;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
