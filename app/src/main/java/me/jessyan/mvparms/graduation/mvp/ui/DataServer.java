package me.jessyan.mvparms.graduation.mvp.ui;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.mvparms.graduation.R;
import me.jessyan.mvparms.graduation.mvp.model.entity.ChatEntity;
import me.jessyan.mvparms.graduation.mvp.model.entity.Column;
import me.jessyan.mvparms.graduation.mvp.model.entity.HomeEntity;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotEntity;
import me.jessyan.mvparms.graduation.mvp.model.entity.HotMultipleItem;
import me.jessyan.mvparms.graduation.mvp.model.entity.MoreEntity;
import me.jessyan.mvparms.graduation.mvp.model.entity.PopularContent;
import me.jessyan.mvparms.graduation.mvp.model.entity.SetupEntity;
import me.jessyan.mvparms.graduation.mvp.model.entity.Theme;

/**
 * 作者: 张少林 on 2017/2/22 0022.
 * 邮箱:1083065285@qq.com
 */

public class DataServer {
    public static List<HotMultipleItem> getMultipleItemData() {
        List<HotMultipleItem> list = new ArrayList<>();
        List<Column> columnList = new ArrayList<>();
        List<Theme> themeList = new ArrayList<>();
        List<PopularContent> popularContentList = new ArrayList<>();

        final int[] images = {R.drawable.android,
                R.drawable.ios, R.drawable.web,
                R.drawable.tool, R.drawable.design};
        columnList.add(new Column("android", images[0]));
        columnList.add(new Column("ios", images[1]));
        columnList.add(new Column("前端", images[2]));
        columnList.add(new Column("工具资源", images[3]));
        columnList.add(new Column("设计", images[4]));

        final int[] themeImages = {R.drawable.midnight,
                R.drawable.first, R.drawable.five
                , R.drawable.second, R.drawable.third, R.drawable.serven, R.drawable.timo};
        themeList.add(new Theme("深夜惊奇", themeImages[0]));
        themeList.add(new Theme("瞎扯", themeImages[1]));
        themeList.add(new Theme("这里是广告", themeImages[2]));
        themeList.add(new Theme("读读日报推荐", themeImages[3]));
        themeList.add(new Theme("职人介绍所", themeImages[4]));
        themeList.add(new Theme("读读日报24小时热门", themeImages[5]));
        themeList.add(new Theme("选个好专业", themeImages[6]));

        for (int i = 0; i < 100; i++) {
            PopularContent popularContent = new PopularContent(R.drawable.profile,
                    "张少林",
                    "如何找到女朋友", "假如生活欺骗了你 假如生活欺骗了你，不要悲伤，不要心急！" +
                    "忧郁的日子里须要镇静：相信吧，快乐的日子将会来临！心儿永远向往着未来；" +
                    "现在却常是忧郁。一切都是瞬息，一切都将会过去；而那过去了的，就会成为亲切的怀恋。", "225 赞", "23 评论");
            popularContentList.add(popularContent);
        }

        list.add(new HotMultipleItem(HotMultipleItem.COLUMN_LIST, columnList, themeList, popularContentList));
        list.add(new HotMultipleItem(HotMultipleItem.THEME_LIST, columnList, themeList, popularContentList));
        list.add(new HotMultipleItem(HotMultipleItem.POPULAR_CONTEN_LIST, columnList, themeList, popularContentList));

        return list;
    }

    public static List<Column> getColumnData() {
        List<Column> columnList = new ArrayList<>();
        final int[] images = {R.drawable.android,
                R.drawable.ios, R.drawable.web,
                R.drawable.tool, R.drawable.design};
        columnList.add(new Column("android", images[0]));
        columnList.add(new Column("ios", images[1]));
        columnList.add(new Column("前端", images[2]));
        columnList.add(new Column("工具资源", images[3]));
        columnList.add(new Column("设计", images[4]));
        return columnList;
    }

    public static List<Theme> getThemeData() {
        List<Theme> themeList = new ArrayList<>();
        final int[] themeImages = {R.drawable.midnight,
                R.drawable.first, R.drawable.five
                , R.drawable.second, R.drawable.third, R.drawable.serven, R.drawable.timo};
        themeList.add(new Theme("深夜惊奇", themeImages[0]));
        themeList.add(new Theme("瞎扯", themeImages[1]));
        themeList.add(new Theme("这里是广告", themeImages[2]));
        themeList.add(new Theme("读读日报推荐", themeImages[3]));
        themeList.add(new Theme("职人介绍所", themeImages[4]));
        themeList.add(new Theme("读读日报24小时热门", themeImages[5]));
        themeList.add(new Theme("选个好专业", themeImages[6]));
        return themeList;
    }

    public static List<HotEntity> getHotData() {
        List<HotEntity> hotEntityList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HotEntity hotEntity = new HotEntity(R.drawable.profile,
                    "张少林",
                    "如何找到女朋友", "假如生活欺骗了你 假如生活欺骗了你，不要悲伤，不要心急！" +
                    "忧郁的日子里须要镇静：相信吧，快乐的日子将会来临！心儿永远向往着未来；" +
                    "现在却常是忧郁。一切都是瞬息，一切都将会过去；而那过去了的，就会成为亲切的怀恋。", "225 赞", "23 评论");
            hotEntityList.add(hotEntity);
        }
        return hotEntityList;
    }

    public static List<MoreEntity> getMoreFragmentData() {
        final int[] images = {R.drawable.focus, R.drawable.collection, R.drawable.caogao, R.drawable.ic_browing, R.drawable.shujia};
        List<MoreEntity> moreEntities = new ArrayList<>();

        moreEntities.add(new MoreEntity("我的关注", images[0]));
        moreEntities.add(new MoreEntity("我的收藏", images[1]));
        moreEntities.add(new MoreEntity("我的草稿", images[2]));
        moreEntities.add(new MoreEntity("浏览记录", images[3]));
        moreEntities.add(new MoreEntity("我的文章", images[4]));

        return moreEntities;
    }

    public static List<SetupEntity> getSetupData() {
        final int[] images = {R.drawable.moshi, R.drawable.share, R.drawable.fankui, R.drawable.about};
        List<SetupEntity> list = new ArrayList<>();
        list.add(new SetupEntity("省流量模式", images[0]));
//        list.add(new SetupEntity("分享", images[1]));
        list.add(new SetupEntity("反馈", images[2]));
        list.add(new SetupEntity("关于", images[3]));
        return list;
    }

    public static List<ChatEntity> getChatData() {
        List<ChatEntity> list = new ArrayList<>();
        String[] name = new String[]{"小黄", "小李", "小张", "老王", "小马"};
        String[] chats = new String[]{"小黄的消息", "小李的消息", "小张的消息", "老王的消息", "小马的消息"};
        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 5);
            list.add(new ChatEntity(name[index], chats[index]));
        }
        return list;
    }

    public static List<String> getImages() {
        List<String> images = new ArrayList<>();
        images.add("http://a.hiphotos.baidu.com/image/h%3D360/sign=78400e9533adcbef1e3478009cae2e0e/cdbf6c81800a19d814dcb35431fa828ba71e4687.jpg");
        images.add("http://e.hiphotos.baidu.com/image/h%3D360/sign=2c790c7ff9f2b211fb2e8348fa806511/bd315c6034a85edfb1f0942f4b540923dd5475b9.jpg");
        images.add("http://c.hiphotos.baidu.com/image/h%3D360/sign=ee04d4cee4cd7b89f66c3c853f244291/1e30e924b899a901760b8f321f950a7b0208f5fc.jpg");
        images.add("http://c.hiphotos.baidu.com/image/h%3D360/sign=2bf4c65457fbb2fb2b2b5e147f4a2043/a044ad345982b2b7a2b8f7cd33adcbef76099b90.jpg");
        images.add("http://e.hiphotos.baidu.com/image/h%3D360/sign=9a66dab99c510fb367197191e932c893/b999a9014c086e06613eab4b00087bf40ad1cb18.jpg");
        return images;
    }

    public static List<HomeEntity> getHomeData() {
        List<HomeEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HomeEntity homeEntity = new HomeEntity(R.drawable.profile,
                    "来自话题,社会", R.drawable.cardpic,
                    "如何找到女朋友", "假如生活欺骗了你 假如生活欺骗了你，不要悲伤，不要心急！" +
                    "忧郁的日子里须要镇静：相信吧，快乐的日子将会来临！心儿永远向往着未来；" +
                    "现在却常是忧郁。一切都是瞬息，一切都将会过去；而那过去了的，就会成为亲切的怀恋。", "225 赞", "23 评论");
            list.add(homeEntity);
        }
        return list;
    }

    public static List<Integer> getImageList() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.cardpic);
        list.add(R.drawable.pic1);
        list.add(R.drawable.pic2);
        return list;
    }
}
