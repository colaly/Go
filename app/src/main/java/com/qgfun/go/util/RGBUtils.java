package com.qgfun.go.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.qgfun.go.R;

/**
 * @author : Cola
 * @date : 2019/5/8 21:30
 * description : TODO
 */
public class RGBUtils {
    /**
     * 获取view的bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        } else {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    public static int getColor(View v) {
        Bitmap bitmap = getBitmapFromView(v);
        if (null != bitmap) {
            int pixel = bitmap.getPixel(200, 5);
            //获取颜色
            int redValue = Color.red(pixel);
            int greenValue = Color.green(pixel);
            int blueValue = Color.blue(pixel);
            Log.i("View 颜色值", Integer.toHexString(pixel).toUpperCase());
            bitmap.recycle();
            return pixel;
        }
        return R.color.main;
    }
}
