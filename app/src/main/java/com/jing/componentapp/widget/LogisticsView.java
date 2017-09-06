package com.jing.componentapp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jing.componentapp.R;
import com.jing.library.utils.Helper;
import com.jing.library.utils.LogUtil;

/**
 * Created by liujing on 2017/9/5.
 * 物流的时间轴
 */

public class LogisticsView extends View {
    private static final String TAG = "LogisticsView";
    private int width;
    private int height;

    private Paint showPaint;
    private Paint defaultPaint;

    private Bitmap bitmap;
    private boolean isDrawLast;
    private int drawX;
    private int drawY;
    private float startX;
    private float startY;
    private int bitmapWidth;
    private int bitmapHeight;

    public void setBitmap(int resId) {
        setBitmap(resId, true);
    }

    public void setBitmap(int resId, boolean isDrawLast) {
        this.bitmap = BitmapFactory.decodeResource(getResources(), resId);
        this.isDrawLast = isDrawLast;
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        LogUtil.i(TAG, "bitmapWidth: " + bitmapWidth + "bitmapHeight: " + bitmapHeight);
        invalidate();
    }

    public LogisticsView(Context context) {
        this(context, null, 0);
    }

    public LogisticsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogisticsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtil.i(TAG, "LogUtilisticsView: ");
        showPaint = new Paint();
        showPaint.setAntiAlias(true);
        defaultPaint = new Paint();
        defaultPaint.setAntiAlias(true);
        defaultPaint.setStrokeWidth(5);
        defaultPaint.setColor(0xFFBBBBBB);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spot_green);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(TAG, "onMeasure: ");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            //wrap_content和不确定时指定值
            width = Helper.dp2px(15);
        } else if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            height = Helper.dp2px(15);
        } else if (widthMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.i(TAG, "onLayout: " + width + " height: " + height);
        if (height < width) {
            throw new IllegalArgumentException("width must be more than height");
        }
        startX = (float) width / 2;
        startY = bitmapHeight;
        if (bitmapWidth >= width) {
            drawX = drawY = 0;
        } else {
            drawX = (width - bitmapWidth) / 2;
            drawY = 0;
        }
        LogUtil.i(TAG, "drawX: " + drawX + " drawY: " + drawY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i(TAG, "onDraw: ");
        canvas.drawBitmap(bitmap, drawX, drawY, showPaint);
        if (isDrawLast) {
            canvas.drawLine(startX, startY, startX, height, defaultPaint);
        }
    }
}
