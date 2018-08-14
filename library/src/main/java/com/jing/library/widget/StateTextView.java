package com.jing.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.jing.library.R;
import com.jing.library.utils.LogUtil;

/**
 * Created by liujing on 2018/7/23.
 */
public class StateTextView extends AppCompatTextView {
    private static final String TAG = "StateTextView";

    public static final int ICON_DIR_LEFT = 1, ICON_DIR_RIGHT = 2, ICON_DIR_TOP = 3, ICON_DIR_BOTTOM = 4;

    private static final int NORMAL = 1;
    private static final int PRESS = 2;

    private Context context;

    //背景图片
    private GradientDrawable backgroundNormal;
    private GradientDrawable backgroundPress;

    private int iconDirection;
    private Drawable iconNormal;
    private Drawable iconPress;

    //背景颜色
    private int backgroundColorNormal;
    private int backgroundColorPress;

    //系统规定的 否则报错 java.lang.ArrayIndexOutOfBoundsException: radii[] needs 8 values
    private float[] connerRadii = new float[8];
    //当connerRadius值大于0时，表是四个圆角的大小相同，不考虑其它字段的值
    private float connerRadius;
    private float leftTopRadius;
    private float leftBottomRadius;
    private float rightTopRadius;
    private float rightBottomRadius;

    //边线的大小和颜色
    private int borderWidth;
    private int borderColorNormal;
    private int borderColorPress;
    //虚线的边长和间距
    private float dashWidth;
    private float dashGap;

    //文字颜色
    private int textColorNormal;
    private int textColorPress;

    private int touchSlop;
    private int mState = 0;

    public StateTextView(Context context) {
        this(context, null, 0);
    }

    public StateTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StateTextView);
        backgroundColorNormal = array.getColor(R.styleable.StateTextView_background_normal, Color.TRANSPARENT);
        backgroundColorPress = array.getColor(R.styleable.StateTextView_background_press, Color.TRANSPARENT);

        iconDirection = array.getInt(R.styleable.StateTextView_icon_direction, 0);
        iconNormal = array.getDrawable(R.styleable.StateTextView_icon_normal);
        iconPress = array.getDrawable(R.styleable.StateTextView_icon_press);

        connerRadius = array.getDimension(R.styleable.StateTextView_conner_radius, 0);
        leftTopRadius = array.getDimension(R.styleable.StateTextView_left_top_radius, 0);
        leftBottomRadius = array.getDimension(R.styleable.StateTextView_left_bottom_radius, 0);
        rightTopRadius = array.getDimension(R.styleable.StateTextView_right_top_radius, 0);
        rightBottomRadius = array.getDimension(R.styleable.StateTextView_right_bottom_radius, 0);

        borderWidth = array.getDimensionPixelSize(R.styleable.StateTextView_border_width, 0);
        borderColorNormal = array.getColor(R.styleable.StateTextView_border_color_normal, Color.TRANSPARENT);
        borderColorPress = array.getColor(R.styleable.StateTextView_border_color_press, Color.TRANSPARENT);
        dashWidth = array.getDimensionPixelSize(R.styleable.StateTextView_border_width, 0);
        dashGap = array.getDimensionPixelSize(R.styleable.StateTextView_border_width, 0);

        textColorNormal = array.getColor(R.styleable.StateTextView_text_color_normal, Color.TRANSPARENT);
        textColorPress = array.getColor(R.styleable.StateTextView_text_color_press, Color.TRANSPARENT);

        array.recycle();

        backgroundNormal = new GradientDrawable();
        backgroundPress = new GradientDrawable();

        set(NORMAL);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(TAG, "ACTION_DOWN");
                set(PRESS);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(TAG, "ACTION_MOVE");
                float x = event.getX();
                float y = event.getY();
                set(isOutXY(x, y) ? NORMAL : PRESS);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                LogUtil.d(TAG, "ACTION_UP");
                set(NORMAL);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            set(PRESS);
        } else {
            set(NORMAL);
        }
        super.setSelected(selected);
    }

    private boolean isOutXY(float x, float y) {
        if (x < -touchSlop || x > getWidth() + touchSlop
                || y < -touchSlop || y > getHeight() + touchSlop) {
            return true;
        }
        return false;
    }

    private void set(int state) {
        if (mState == state) return;
        if (state == NORMAL) {
            //设置文字颜色
//            int[][] states = new int[2][];
//            states[0] = new int[]{android.R.attr.state_pressed};
//            states[1] = new int[]{android.R.attr.state_enabled};
//            int[] colors = new int[]{Color.BLUE, Color.RED};
//            ColorStateList colorStateList = new ColorStateList(states, colors);
//            setTextColor(colorStateList);
            //设置背景
            backgroundNormal.setColor(backgroundColorNormal);
            //设置4个边圆角
            setRadii();
            backgroundNormal.setCornerRadii(connerRadii);
            //设置边线（实线）的大小，颜色
            backgroundNormal.setStroke(borderWidth, borderColorNormal);
            //设置边线（虚线）的大小，颜色, 虚线的大小，虚线的间距
//            backgroundNormal.setStroke(borderWidth, borderColorNormal, dashWidth, dashGap);
            setBackground(backgroundNormal);
            setTextColor(textColorNormal);
            setIcon(iconNormal, iconDirection);
        } else if (state == PRESS) {
            if (backgroundColorPress != 0) {
                backgroundPress.setColor(backgroundColorPress);
                //设置4个边圆角
                setRadii();
                backgroundPress.setCornerRadii(connerRadii);
                backgroundPress.setStroke(borderWidth, borderColorPress, dashWidth, dashGap);
                setBackground(backgroundPress);
            }
            setTextColor(textColorPress);
            setIcon(iconPress, iconDirection);
        }
        mState = state;
    }

    private void setRadii() {
        if (connerRadius > 0) {
            //左上角
            connerRadii[0] = connerRadius;
            connerRadii[1] = connerRadius;
            //右上角
            connerRadii[2] = connerRadius;
            connerRadii[3] = connerRadius;
            //右下角
            connerRadii[4] = connerRadius;
            connerRadii[5] = connerRadius;
            //左下角
            connerRadii[6] = connerRadius;
            connerRadii[7] = connerRadius;
        } else {
            //左上角
            connerRadii[0] = leftTopRadius;
            connerRadii[1] = leftTopRadius;
            //右上角
            connerRadii[2] = rightTopRadius;
            connerRadii[3] = rightTopRadius;
            //右下角
            connerRadii[4] = rightBottomRadius;
            connerRadii[5] = rightBottomRadius;
            //左下角
            connerRadii[6] = leftBottomRadius;
            connerRadii[7] = leftBottomRadius;
        }
    }

    private void setIcon(Drawable drawable, int direction) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            switch (direction) {
                case ICON_DIR_LEFT:
                    setCompoundDrawables(drawable, null, null, null);
                    break;
                case ICON_DIR_RIGHT:
                    setCompoundDrawables(null, null, drawable, null);
                    break;
                case ICON_DIR_TOP:
                    setCompoundDrawables(null, drawable, null, null);
                    break;
                case ICON_DIR_BOTTOM:
                    setCompoundDrawables(null, null, null, drawable);
                    break;
            }
        }
    }

    public int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
