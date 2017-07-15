package me.jessyan.mvparms.graduation.mvp.model.entity;

/**
 * 作者: 张少林 on 2017/4/5 0005.
 * 邮箱:1083065285@qq.com
 */

public class GanHuoDataBean {

    /**
     * _id : 58d0a613421aa95810795ca4
     * createdAt : 2017-03-21T12:03:31.46Z
     * desc : 原来婚礼可以这么幸福
     * publishedAt : 2017-04-05T11:50:18.748Z
     * source : chrome
     * type : 休息视频
     * url : http://www.miaopai.com/show/GtDTWG~8HqAmUpIOAji3FA32YDOTtkcC.htm
     * used : true
     * who : lxxself
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
