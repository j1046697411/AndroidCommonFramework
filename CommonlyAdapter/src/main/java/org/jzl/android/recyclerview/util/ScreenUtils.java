package org.jzl.android.recyclerview.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import org.jzl.lang.util.ObjectUtils;

public class ScreenUtils {

    public static int getScreenHeight(Context context) {
        if (ObjectUtils.isNull(context)) {
            return 0;
        }
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }


    public static int getScreenWidth(Context context) {
        if (ObjectUtils.isNull(context)) {
            return 0;
        }
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 4      * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * 5
     */
    public static int dip2px(Context context, float dpValue) {
        if (ObjectUtils.isNull(context)) {
            return (int) dpValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 12      * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * 13
     */
    public static int px2dip(Context context, float pxValue) {
        if (ObjectUtils.isNull(context)) {
            return (int) pxValue;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
