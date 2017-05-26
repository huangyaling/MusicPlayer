package com.huangyaling.musicplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by huangyaling on 2017/5/25.
 */
public class ScreenUtils {

    //obtain windows width
    public static int getScreenWidth(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    //obtain windows height
    public static int getScreenHeight(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    //obtain statbar height
    public static int getStatusHeight(Context context){
        int statusHeight = 1;
        try{
            Class<?> mClass = Class.forName("com.android.internal.R$dimen");
            Object object = mClass.newInstance();
            int height = Integer.parseInt(mClass.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        }catch (Exception e){
            e.printStackTrace();
        }
        return statusHeight;
    }

    //obtain screen shortcut include status bar
    public static Bitmap snapShortWithStatusBar(Activity activity){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bitmap,0,0,width,height);
        view.destroyDrawingCache();
        return bp;
    }

    //obtain screen shortcut without statusbar
    public static Bitmap snapShortWithoutStatusBar(Activity activity){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int StatusBarHeight = frame.top;
        int width = getScreenWidth(activity);
        int height = getStatusHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bitmap,0,StatusBarHeight,width,height-StatusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }




}
