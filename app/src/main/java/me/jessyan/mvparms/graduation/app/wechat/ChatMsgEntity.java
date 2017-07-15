package me.jessyan.mvparms.graduation.app.wechat;

import java.io.Serializable;

/**
 * 作者: 张少林 on 2017/4/16 0016.
 * 邮箱:1083065285@qq.com
 */
public class ChatMsgEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5939082237185769195L;
    private int typ;
    private int id;
    private String from;
    private String to;
    private String name;
    private String date;
    private String text;
    private String time;
    private int chatNum;

    private String chatType;

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }


    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private boolean isComMeg = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
        isComMeg = isComMsg;
    }


    public int getChatNum() {
        return chatNum;
    }

    public void setChatNum(int chatNum) {
        this.chatNum = chatNum;
    }

    public ChatMsgEntity() {
    }


    public ChatMsgEntity(int typ, String from, String date) {
        super();
        this.typ = typ;
        this.from = from;
        this.date = date;
    }

    public ChatMsgEntity(String from, String to, String name, String date,
                         String text, int chatNum) {
        super();
        this.from = from;
        this.to = to;
        this.name = name;
        this.date = date;
        this.text = text;
        this.chatNum = chatNum;
    }

    public ChatMsgEntity(int typ, String from, String to, String name, String date,
                         String text) {
        super();
        this.typ = typ;
        this.from = from;
        this.to = to;
        this.name = name;
        this.date = date;
        this.text = text;
    }

    public ChatMsgEntity(int typ, String name, String date, String text, boolean isComMsg) {
        super();
        this.typ = typ;
        this.name = name;
        this.date = date;
        this.text = text;
        this.isComMeg = isComMsg;
    }

    @Override
    public String toString() {
        return "ChatMsgEntity [typ=" + typ + ", from=" + from + ", to=" + to
                + ", name=" + name + ", date=" + date + ", text=" + text
                + ", time=" + time + ", isComMeg=" + isComMeg + "]";
    }
}
