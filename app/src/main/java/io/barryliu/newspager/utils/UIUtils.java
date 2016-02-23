package io.barryliu.newspager.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by zhangshixin on 2015/11/26.
 * Blog : http://blog.csdn.net/u011240877
 *
 * @description Codes there always can be better.
 */
public class UIUtils {



    public static Toast toast;

    public static final boolean isEmail(String value) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    public static String getVersion(Context context)// 获取版本号
    {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pi.versionName;
    }



    /**
     * dip转换px
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }







    public static View inflate(Context context, int resId) {
        return LayoutInflater.from(context).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(Context context, int resId) {
        return getResources(context).getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(Context context, int resId) {
        return getResources(context).getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(Context context, int resId) {
        return getResources(context).getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(Context context, int resId) {
        return getResources(context).getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(Context context, int resId) {
        return getResources(context).getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(Context context, int resId) {
        return getResources(context).getColorStateList(resId);
    }

    private static void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }



}


