package com.jing.library.imageloader;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jing.library.http.GlideApp;

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
        GlideApp.with(ctx).load(img.getUrl()).placeholder(img.getPlaceHolder()).error(img.getErrorHolder()).into(img.getImgView());
    }

    private void loadCircleNormal(Context ctx, ImageLoader img) {
        GlideApp.with(ctx).load(img.getUrl()).placeholder(img.getPlaceHolder()).error(img.getErrorHolder())
                .circleCrop().into(img.getImgView());
    }

    private void loadStringCircle(Context ctx, ImageLoader img) {
        GlideApp.with(ctx).load(img.getUrl())
                .circleCrop().into(img.getImgView());
    }

    //通过此监听器可以查看加载图片失败的原因
    RequestListener<Request> listener = new RequestListener<Request>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Request> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Request resource, Object model, Target<Request> target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };

}
