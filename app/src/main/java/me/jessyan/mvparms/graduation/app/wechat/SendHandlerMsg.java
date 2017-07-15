package me.jessyan.mvparms.graduation.app.wechat;

import android.os.Bundle;
import android.os.Message;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/16.
 */

public class SendHandlerMsg {

    private static Message message;

    /**
     * 建立bundle 向 fragment 中发送消息
     *
     * @param SerializableKey 系列化键值
     * @param obj 消息内容，传入时必须先系列化对象，不然数据发送不出去，会导致堵塞异常
     */
    public static void FragmentHandlerMsg(final String SerializableKey, final Object obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 *fragmentHandler 向MyFragment中发送消息
                 * fragmentHandler必须放在每个线程内部，如果放在外面会导致getInstance()为空，Handler线程异常
                 */
                FragmentHandler fragmentHandler = FragmentHandler.getInstance();
                message = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable(SerializableKey, (Serializable) obj);
                message.setData(bundle);
                fragmentHandler.sendMessage(message);
            }
        }).start();

    }
}
