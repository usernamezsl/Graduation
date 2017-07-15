package me.jessyan.mvparms.graduation.app.wechat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 作者: 张少林 on 2017/4/16 0016.
 * 邮箱:1083065285@qq.com
 */
public class GsonUntil {
    /**
     * 将聊天信息转换成Gson数据
     * 将ChatMsgEntity类数据转换成功Gson 数据，便于发送？
     */
    public static String sendChatGson(ChatMsgEntity chatMsg) {
        return new Gson().toJson(chatMsg);
    }

    /**
     * 将聊天信息转换成Gson数据
     * 将Gson 数据转换成ChatMsgEntity类数据，便于操作
     */
    public static ChatMsgEntity recChatGson(String gsonChat) {
        Gson gson = new Gson();
        ChatMsgEntity chatMsgDaata = new ChatMsgEntity();
        chatMsgDaata = gson.fromJson(gsonChat,
                new TypeToken<ChatMsgEntity>() {
                }.getType());
        return chatMsgDaata;
    }
}
