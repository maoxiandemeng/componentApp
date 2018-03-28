package com.jing.componentapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jing.componentapp.R;
import com.jing.library.http.GlideApp;

/**
 * Created by liujing on 2018/3/28.
 */

public class ReloadImageView extends FrameLayout implements View.OnClickListener {
    private static final String TAG = ReloadImageView.class.getName();
    private Context context;
    private ImageView iv;
    private TextView tv;
    private String url;
    private boolean isSuccess = false;

    public ReloadImageView(@NonNull Context context) {
        this(context, null, 0);
    }

    public ReloadImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReloadImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(iv);
        tv = new TextView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tv.setText("点击此处\n重新加载");
        tv.setTextSize(12);
        params.gravity = Gravity.CENTER;
        addView(tv, params);
    }

    public void setImageUrl(String url){
        tv.setVisibility(GONE);
        if (DrawableCache.getInstance().hasKey(url)) {
            iv.setImageDrawable(DrawableCache.getInstance().get(url));
            tv.setOnClickListener(null);
        } else {
            this.url = url;
            tv.setOnClickListener(null);
            loadImage();
        }
    }

    @Override
    public void onClick(View view) {
        loadImage();
    }

    private void loadImage(){
        GlideApp.with(context).load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        tv.setVisibility(VISIBLE);
                        tv.setOnClickListener(ReloadImageView.this);
                        isSuccess = false;
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        tv.setVisibility(GONE);
                        tv.setOnClickListener(null);
                        isSuccess = true;
                        if (!DrawableCache.getInstance().hasKey(url)) {
                            DrawableCache.getInstance().set(url, resource);
                        }
                        return false;
                    }
                }).into(iv);
    }

}
