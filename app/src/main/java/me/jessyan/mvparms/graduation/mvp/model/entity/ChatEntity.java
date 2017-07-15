package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/2/28 0028.
 * 邮箱:1083065285@qq.com
 */

public class ChatEntity {
    private String name;
    private String describe;

    public ChatEntity(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
