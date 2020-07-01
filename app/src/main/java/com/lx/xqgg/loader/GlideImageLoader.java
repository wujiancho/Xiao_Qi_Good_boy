package com.lx.xqgg.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lx.xqgg.R;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.UnicornImageLoader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GlideImageLoader implements UnicornImageLoader {
    private Context context;

    public GlideImageLoader(Context context) {
        this.context = context.getApplicationContext();
    }


    @Nullable
    @Override
    public Bitmap loadImageSync(String uri, int width, int height) {
        return null;
    }

    @Override
    public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .error(R.drawable.ic_default);
        Glide.with(context).asBitmap().load(uri).apply(options)
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (listener != null) {
                            listener.onLoadComplete(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Throwable t = new Throwable("加载异常");
                        listener.onLoadFailed(t);
                    }
                });
    }
}
