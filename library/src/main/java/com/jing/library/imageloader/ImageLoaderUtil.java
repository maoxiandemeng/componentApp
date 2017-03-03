package com.jing.library.imageloader;

import android.content.Context;

public class ImageLoaderUtil {
    public static final int TRANSFORM_NORMAL = 0;
    public static final int TRANSFORM_CIRCLE = 1;

    public static final int PIC_ID = 0;
    public static final int PIC_STRING= 1;

    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private static ImageLoaderUtil mInstance;
    private BaseImageLoaderStrategy mStrategy;

    public ImageLoaderUtil(){
        mStrategy =new GlideImageLoaderStrategy();
    }

    public static ImageLoaderUtil getInstance(){
        if(mInstance ==null){
            synchronized (ImageLoaderUtil.class){
                if(mInstance == null){
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }


    public void loadImage(Context context, ImageLoader img){
        mStrategy.loadImage(context,img);
    }

    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy){
        mStrategy =strategy;
    }
}
