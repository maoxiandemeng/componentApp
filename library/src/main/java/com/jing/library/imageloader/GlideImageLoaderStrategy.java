package com.jing.library.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.io.InputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {
    @Override
    public void loadImage(Context ctx, ImageLoader img) {
        switch (img.getType()) {
            case ImageLoaderUtil.PIC_ID:
                int transform = img.getTransform();
                if (transform == ImageLoaderUtil.TRANSFORM_CIRCLE) {
                    loadCircleNormal(ctx, img);
                    return;
                }
                loadNormal(ctx, img);
                break;
            case ImageLoaderUtil.PIC_STRING:
                loadStringCircle(ctx, img);
                break;
        }
//        int transform = img.getTransform();
//        if (transform == ImageLoaderUtil.TRANSFORM_CIRCLE) {
//            loadCircleNormal(ctx, img);
//            return;
//        }
//        loadNormal(ctx, img);
//        boolean flag= SettingUtil.getOnlyWifiLoadImg(ctx);
//        boolean flag= false;
        //如果不是在wifi下加载图片，直接加载
//        if(!flag){
//            loadNormal(ctx,img);
//            return;
//        }

//        int strategy =img.getWifiStrategy();
//        if(strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI){
//            int netType = NetUtils.getNetWorkType(ctx);
//            //如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
//            if(netType == NetUtils.NETWORKTYPE_WIFI) {
//                loadNormal(ctx, img);
//            } else {
//                //如果是在wifi下才加载图片，并且当前网络不是wifi，加载缓存
//                loadCache(ctx, img);
//            }
//        }else{
//            //如果不是在wifi下才加载图片
//            loadNormal(ctx,img);
//        }

    }

    /**
     * load image with Glide
     */
    private void loadNormal(Context ctx, ImageLoader img) {
        Glide.with(ctx).load(img.getUrl()).placeholder(img.getPlaceHolder()).error(img.getErrorHolder()).crossFade(200).into(img.getImgView());
    }

    private void loadCircleNormal(Context ctx, ImageLoader img) {
        Glide.with(ctx).load(img.getUrl()).placeholder(img.getPlaceHolder()).error(img.getErrorHolder())
                .bitmapTransform(new CropCircleTransformation(ctx)).into(img.getImgView());
    }

    private void loadStringCircle(Context ctx, ImageLoader img) {
        Glide.with(ctx).fromString().load(img.getUrl())
                .bitmapTransform(new CropCircleTransformation(ctx)).into(img.getImgView());
    }

    //通过此监听器可以查看加载图片失败的原因
    RequestListener<String, GlideDrawable> listener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //加载异常的回调
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            //加载成功的回调
            return false;
        }
    };


    /**
     * load cache image with Glide
     */
    private void loadCache(Context ctx, ImageLoader img) {
        Glide.with(ctx).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return model;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(img.getUrl()).placeholder(img.getPlaceHolder()).diskCacheStrategy(DiskCacheStrategy.ALL).into(img.getImgView());
    }
}
