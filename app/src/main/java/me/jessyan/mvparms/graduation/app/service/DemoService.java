package me.jessyan.mvparms.graduation.app.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jess.arms.base.BaseService;

import org.simple.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import me.jessyan.mvparms.graduation.app.wechat.ChatMsgEntity;
import me.jessyan.mvparms.graduation.app.wechat.GsonUntil;
import me.jessyan.mvparms.graduation.app.wechat.S;

/**
 * Created by jess on 9/7/16 16:59
 * Contact with jess.yan.effort@gmail.com
 */
public class DemoService extends BaseService {


    public static BufferedReader chatmReader = null;//用于接收信息
    public static BufferedWriter chatmWriter = null;//用于推送消息
    public static Socket socket = null;
    boolean isThreadEnd = true;
    String recChatData = new String();
    ChatMsgEntity chatPackage = new ChatMsgEntity();
    MyBinder mMyBinder = new MyBinder();

    @Override
    public void init() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return  mMyBinder;
    }

    public class MyBinder extends Binder {


        public void chatSocketThread() {
            //开启链接聊天服务线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = socketConnect();
                        chatmWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
                        chatmReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        while (isThreadEnd) {
                            if (chatmReader.ready()) {
                                recChatData = chatmReader.readLine();
                                chatPackage = new ChatMsgEntity();
                                // 收到上线包，解析包，如果是自己忽略，不是更新ui,
                                Log.e("rcv", "run: " +recChatData);
                                chatPackage = GsonUntil.recChatGson(recChatData);
//                                发送handler消息到Chat Activity
//                               SendHandlerMsg.FragmentHandlerMsg("CHAT", chatPackage);
                                EventBus.getDefault().postSticky(chatPackage);
                            }
                            Thread.sleep(100);
                        }
                        chatmWriter.close();
                        chatmReader.close();
                        socket.close();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        public Socket socketConnect() {
            if (socket == null) {
                int overtime = 0;
                while (socket == null) {
                    try {
                        Thread.sleep(100);
                        socket = new Socket(S.ipAddress, 1220);
                        overtime++;
//                        Log.d(CustomActivity.LOGCAT_KEY, "socket  connect :" + overtime + "...");
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (overtime == 50) {
                        String socketError = "11-12:连接服务器超時，服务器异常！";
                        ChatMsgEntity sError = new ChatMsgEntity();
                        sError.setTyp(44);
                        sError.setText(socketError);
                        //发送消息到Chat Activity
//                        SendHandlerMsg.ActivityHandlerMsg("CHAT", sError);
                    }
                }
            }
            return socket;
        }

        /******************************************************************/
        /**
         * 消息发送函数
         *
         * @param chatMsgEntity
         */
        public void sendSocketChatMessage(ChatMsgEntity chatMsgEntity) {
            try {
                String sendData = GsonUntil.sendChatGson(chatMsgEntity);
                Log.d("MY_FRAGMENT", "消息发送函数");

                chatmWriter.write(sendData);
                chatmWriter.newLine();
                chatmWriter.flush();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("其他异常，objectOutputStream error!");
            }
        }
    }
}
