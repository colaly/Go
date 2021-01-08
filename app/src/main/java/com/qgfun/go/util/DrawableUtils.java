package com.qgfun.go.util;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @author LLY
 */
public class DrawableUtils {

    /**
     * TextView四周drawable的序号。
     * 0 left,  1 top, 2 right, 3 bottom
     */
    private final int LEFT = 0;
    private final int RIGHT = 2;

    private OnDrawableListener listener;
    private TextView mTextView;

    public DrawableUtils(TextView textView, OnDrawableListener l) {
        mTextView = textView;
        mTextView.setOnTouchListener(mOnTouchListener);
        listener = l;
    }

    public interface OnDrawableListener {
        void onLeft(View v, Drawable left);

        void onRight(View v, Drawable right);
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int i = event.getAction();
            if (i == MotionEvent.ACTION_UP) {
                if (listener != null) {
                    Drawable drawableLeft = mTextView.getCompoundDrawables()[LEFT];
                    if (drawableLeft != null && event.getRawX() <= (mTextView.getLeft() + drawableLeft.getBounds().width())) {
                        listener.onLeft(v, drawableLeft);
                        return true;
                    }

                    Drawable drawableRight = mTextView.getCompoundDrawables()[RIGHT];
                    if (drawableRight != null && event.getRawX() >= (mTextView.getRight() - drawableRight.getBounds().width())) {
                        listener.onRight(v, drawableRight);
                        return true;
                    }
                }

            }

            return false;
        }

    };
}