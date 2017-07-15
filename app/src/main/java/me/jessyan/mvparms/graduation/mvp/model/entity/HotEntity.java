package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/3/7 0007.
 * 邮箱:1083065285@qq.com
 */

public class HotEntity {
    private int phpotoId;
    private String fromTitle;
    private String title;
    private String content;
    private String praise;
    private String comment;

    public HotEntity(int phpotoId, String fromTitle, String title, String content, String praise, String comment) {
        this.phpotoId = phpotoId;
        this.fromTitle = fromTitle;
        this.title = title;
        this.content = content;
        this.praise = praise;
        this.comment = comment;
    }

    public int getPhpotoId() {
        return phpotoId;
    }

    public void setPhpotoId(int phpotoId) {
        this.phpotoId = phpotoId;
    }

    public String getFromTitle() {
        return fromTitle;
    }

    public void setFromTitle(String fromTitle) {
        this.fromTitle = fromTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "PopularContent{" +
                "phpotoId=" + phpotoId +
                ", fromTitle='" + fromTitle + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", praise='" + praise + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
