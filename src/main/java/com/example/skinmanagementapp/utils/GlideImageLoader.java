package com.example.skinmanagementapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.skinmanagementapp.R;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.error_img)
                .error(R.drawable.error_img);

        Glide.with(context)
                .load(path).apply(options)
                .into(imageView);

    }


}
