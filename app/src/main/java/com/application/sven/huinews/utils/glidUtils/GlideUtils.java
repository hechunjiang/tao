package com.application.sven.huinews.utils.glidUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by sfy. on 2018/5/8 0008.
 * Glide 封装类
 */

public class GlideUtils {
    /**
     * 默认加载
     *
     * @param mContext
     * @param path
     * @param mImageView
     */
    public static void loadImage(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).into(mImageView);
    }

    /**
     * 加载资源图片
     *
     * @param mContext
     * @param res
     * @param iv
     */
    public static void loadResImage(Context mContext, int res, ImageView iv) {
        Glide.with(mContext).load(res).into(iv);
    }

    /**
     * 加载圆形图
     *
     * @param mContext
     * @param path
     * @param iv
     */
    public static void loadCircleImage(Context mContext, String path, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.def_avatar)
                .error(R.mipmap.def_avatar)
                .dontAnimate();
        Glide.with(mContext)
                .load(path)
                .apply(bitmapTransform(new GlideCircleTransform()))
                .apply(options)

                .into(iv);
    }

    public static void loadImgUrl(Context context, final String url, final ImageView imageView) {

        if (imageView.getTag() != null) {
            if (imageView.getTag().equals(url)) {
                return;
            }
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        imageView.setTag(url);
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {

            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (imageView.getTag().equals(url)) {
                    imageView.setImageDrawable(resource);
                }
            }
        };
        Glide.with(context)
                .load(url)
                .into(simpleTarget);
    }

    public static void loadResRoundImage(Context mContext, int res, ImageView iv) {
        Glide.with(mContext).load(res).apply(bitmapTransform(new GlideRoundTransform(mContext, 1)))
                .into(iv);
    }

    public static void loadVideoThumbImage(Context context, String url, ImageView imgeview) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_load)
                .error(R.drawable.img_load)
                .dontAnimate();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imgeview);
    }

    public static void loadImgGetResult(Context context, String url, SimpleTarget<Drawable> simpleTarget) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(context).load(url).apply(bitmapTransform(new GlideRoundTransform(context, 5)))
                .into(simpleTarget);
    }

    public static void loadAdImg(Context context, String url, SimpleTarget<Drawable> simpleTarget) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(context).load(url)
               .into(simpleTarget);
    }

}
