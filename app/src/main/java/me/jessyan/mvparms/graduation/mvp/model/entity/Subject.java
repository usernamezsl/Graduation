package me.jessyan.mvparms.graduation.mvp.model.entity;

import org.litepal.crud.DataSupport;

/**
 * 作者: 张少林 on 2017/4/11 0011.
 * 邮箱:1083065285@qq.com
 */

public class Subject extends DataSupport {
    private String desc;
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
