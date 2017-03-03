package com.jing.library.imageloader;

import android.widget.ImageView;

import com.library.R;

public class ImageLoader {
    private int type;  //类型 (id，file)
    private int transform; //图片所需转化的类型
    private String url; //需要解析的url
    private int placeHolder; //当没有成功加载的时候显示的图片
    private int errorHolder; //当加载失败的时候显示的图片
    private ImageView imgView; //ImageView的实例
    private int wifiStrategy;//加载策略，是否在wifi下才加载

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.transform = builder.transform;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.errorHolder = builder.errorHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
    }
    public int getType() {
        return type;
    }

    public int getTransform(){ return transform; }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getErrorHolder() {
        return errorHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public static class Builder {
        private int type;
        private int transform;
        private String url;
        private int placeHolder;
        private int errorHolder;
        private ImageView imgView;
        private int wifiStrategy;

        public Builder() {
            this.type = ImageLoaderUtil.PIC_ID;
            this.transform = ImageLoaderUtil.TRANSFORM_NORMAL;
            this.url = "";
            this.placeHolder = R.drawable.image_loading;
            this.errorHolder = R.drawable.image_error;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder transform(int transform){
            this.transform = transform;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder errorHolder(int errorHolder) {
            this.errorHolder = errorHolder;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }

    }
}
