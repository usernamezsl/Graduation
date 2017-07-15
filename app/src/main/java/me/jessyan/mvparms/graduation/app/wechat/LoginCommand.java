package me.jessyan.mvparms.graduation.app.wechat;

/**
 * 作者: 张少林 on 2017/4/15 0015.
 * 邮箱:1083065285@qq.com
 * 登录消息
 */

public class LoginCommand extends BaseCommand {
    private String mUsername;
    private String mPassword;

    public LoginCommand(String username, String password) {
        mFlag = "login";//登录命令
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
}
