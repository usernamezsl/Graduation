package me.jessyan.mvparms.graduation.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.youth.banner.loader.ImageLoader;

import common.WEApplication;
import me.jessyan.mvparms.graduation.R;

/**
 * 作者: 张少林 on 2017/2/24 0024.
 * 邮箱:1083065285@qq.com
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        WEApplication applicationContext = (WEApplication) context.getApplicationContext();
        applicationContext.getAppComponent().imageLoader()
                .loadImage(context, GlideImageConfig
                        .builder()
                        .placeholder(R.drawable.beauty)
                        .errorPic(R.drawable.beauty)
                        .url(path.toString())
                        .imageView(imageView)
                        .build());
    }
}
