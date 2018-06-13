package com.dongnao.proxyframework.image.picasso;

import android.app.Activity;
import android.widget.ImageView;

import com.dongnao.proxyframework.image.ImageLoader;
import com.squareup.picasso.Picasso;

/**
 * @author Lance
 * @date 2018/4/8
 */

public class PicassoImageLoader implements ImageLoader {


    @Override
    public void displayImage(Activity activity, String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).into(imageView);
    }
}
