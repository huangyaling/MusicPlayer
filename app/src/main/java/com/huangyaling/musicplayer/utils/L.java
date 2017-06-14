package com.huangyaling.musicplayer.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by huangyaling on 2017/6/12.
 */
public class L {
    public static void logd(Context context,String str){
        Log.d(context.getPackageName().getClass().getName(),str);
    }
    public static void loge(Context context,String str){
        Log.e(context.getPackageName().getClass().getName(), str);
    }
    public static void logi(Context context,String str){
        Log.i(context.getPackageName().getClass().getName(), str);
    }
    public static void logv(Context context,String str){
        Log.v(context.getPackageName().getClass().getName(), str);
    }
    public static void logw(Context context,String str){
        Log.w(context.getPackageName().getClass().getName(), str);
    }
}
