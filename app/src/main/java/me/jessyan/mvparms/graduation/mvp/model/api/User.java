package me.jessyan.mvparms.graduation.mvp.model.api;

import org.litepal.crud.DataSupport;

/**
 * 作者: 张少林 on 2017/4/15 0015.
 * 邮箱:1083065285@qq.com
 */

public class User extends DataSupport {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
