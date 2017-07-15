package me.jessyan.mvparms.graduation.mvp.model.entity;

import java.util.List;

/**
 * 作者: 张少林 on 2017/4/21 0021.
 * 邮箱:1083065285@qq.com
 */

public class GankItemBean {

    /**
     * error : false
     * results : [{"_id":"581a838a421aa90e799ec261","createdAt":"2016-11-03T08:23:38.560Z","desc":"11-3","publishedAt":"2016-11-03T11:48:43.342Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f9em0sj3yvj20u00w4acj.jpg","used":true,"who":"daimajia"},{"_id":"56cc6d1d421aa95caa7076b4","createdAt":"2015-06-23T02:00:00.619Z","desc":"6.23","publishedAt":"2016-05-03T12:13:53.904Z","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1etdsksgctqj20hs0qowgy.jpg","used":true,"who":"张涵宇"},{"_id":"575640bf421aa9759750aee4","createdAt":"2016-06-07T11:34:23.596Z","desc":"隐藏福利","publishedAt":"2016-06-07T11:43:18.947Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f4mi70ns1bj20i20vedkh.jpg","used":true,"who":"代码家"},{"_id":"56cc6d1d421aa95caa7078d2","createdAt":"2015-09-22T03:43:31.996Z","desc":"9.22-可爱型！！！","publishedAt":"2015-09-22T03:53:01.583Z","type":"福利","url":"http://ww3.sinaimg.cn/large/7a8aed7bgw1ewb2ytx5okj20go0p0jva.jpg","used":true,"who":"张涵宇"},{"_id":"585212b4421aa97240ef9ed7","createdAt":"2016-12-15T11:49:08.132Z","desc":"12-15","publishedAt":"2016-12-15T11:54:38.900Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034gw1farbzjliclj20u00u076w.jpg","used":true,"who":"daimajia"},{"_id":"5729794967765974fca830e7","createdAt":"2016-05-04T12:23:37.334Z","desc":"5.4","publishedAt":"2016-05-04T12:26:03.894Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1f3j8jt6qn8j20vr15owvk.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d1d421aa95caa7076b4","createdAt":"2015-06-23T02:00:00.619Z","desc":"6.23","publishedAt":"2016-05-03T12:13:53.904Z","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1etdsksgctqj20hs0qowgy.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d29421aa95caa708205","createdAt":"2016-01-25T09:14:17.609Z","desc":"1.26","publishedAt":"2016-01-26T04:02:34.316Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bjw1f0buzmnacoj20f00liwi2.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d1d421aa95caa70774e","createdAt":"2015-07-03T03:40:15.215Z","desc":"7.3","publishedAt":"2015-07-03T04:12:02.419Z","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1etpfol394kj20qo0hsdiw.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d26421aa95caa707fb4","createdAt":"2015-12-11T03:41:14.579Z","desc":"12.11 补","publishedAt":"2015-12-11T03:48:37.390Z","type":"福利","url":"http://ww3.sinaimg.cn/large/7a8aed7bgw1eyvkh5wnbsj20qo0hsn0e.jpg","used":true,"who":"张涵宇"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 581a838a421aa90e799ec261
         * createdAt : 2016-11-03T08:23:38.560Z
         * desc : 11-3
         * publishedAt : 2016-11-03T11:48:43.342Z
         * source : chrome
         * type : 福利
         * url : http://ww3.sinaimg.cn/large/610dc034jw1f9em0sj3yvj20u00w4acj.jpg
         * used : true
         * who : daimajia
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
}
