package com.jing.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.jing.library.R;

/**
 * Created by liujing on 2018/7/25.
 */
public class SelectImageView extends AppCompatImageView {
    private Drawable unableDrawable;
    private Drawable normalDrawable;
    private Drawable selectDrawable;

    public SelectImageView(Context context) {
        this(context, null, 0);
    }

    public SelectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SelectImageView);
        unableDrawable = array.getDrawable(R.styleable.SelectImageView_unable_img);
        normalDrawable = array.getDrawable(R.styleable.SelectImageView_normal_img);
        selectDrawable = array.getDrawable(R.styleable.SelectImageView_selected_img);

        setBackground(normalDrawable);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            setBackground(normalDrawable);
        } else {
            setBackground(unableDrawable);
        }
        super.setEnabled(enabled);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            setBackground(selectDrawable);
        } else {
            setBackground(normalDrawable);
        }
         super.setSelected(selected);
    }
}
