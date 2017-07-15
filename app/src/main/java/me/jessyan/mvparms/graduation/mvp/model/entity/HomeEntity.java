package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/3/2 0002.
 * 邮箱:1083065285@qq.com
 */

public class HomeEntity {
    private int phpotoId;
    private String fromTitle;
    private int imgId;
    private String title;
    private String content;
    private String praise;
    private String comment;

    public HomeEntity(int phpotoId, String fromTitle, int imgId, String title, String content, String praise, String comment) {
        this.phpotoId = phpotoId;
        this.fromTitle = fromTitle;
        this.imgId = imgId;
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

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
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
        return "HomeEntity{" +
                "phpotoId=" + phpotoId +
                ", fromTitle='" + fromTitle + '\'' +
                ", imgId=" + imgId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", praise='" + praise + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
