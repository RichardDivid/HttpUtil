package com.dongnao.proxyframework.image.glide;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dongnao.proxyframework.image.ImageLoader;

/**
 * @author Lance
 * @date 2018/4/8
 */

public class GlideImageLoader implements ImageLoader {
    public GlideImageLoader() {
//        new GlideBuilder().setBitmapPool()
//        Glide.init(context, new GlideBuilder());
    }

    @Override
    public void displayImage(Activity activity, String imageUrl, ImageView view) {
        Glide.with(activity).load(imageUrl).into(view);
    }
}
