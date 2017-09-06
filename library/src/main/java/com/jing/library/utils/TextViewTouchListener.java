package com.jing.library.utils;

import android.text.Layout;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by liujing on 2017/9/6.
 */

public class TextViewTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean ret = false;
        TextView widget = (TextView) view;
        CharSequence text = widget.getText();
        Spannable spanText = Spannable.Factory.getInstance().newSpannable(text);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();
            x += widget.getScrollX();
            y += widget.getScrollY();
            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);
            ClickableSpan[] link = spanText
                    .getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                }
                ret = true;
            }
        }
        return ret;
    }
}
