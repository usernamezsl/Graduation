package me.jessyan.mvparms.graduation.mvp.model.entity;

import org.litepal.crud.DataSupport;

/**
 * 作者: 张少林 on 2017/3/29 0029.
 * 邮箱:1083065285@qq.com
 */

public class Comment extends DataSupport {

    private String title;
    private String comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
