package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/4/21 0021.
 * 邮箱:1083065285@qq.com
 */

public class MyUser {

    /**
     * username : 张少林
     * password : 940219qq
     * avatar : http://img1.imgtn.bdimg.com/it/u=2636483132,949730981&fm=23&gp=0.jpg
     */

    private String username;
    private String password;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
