package com.huangyaling.musicplayer.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.huangyaling.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyaling on 2017/5/31.
 */
public class PermissionCheckUtils {
    public final static int PERMISSIONCODE = 999;
    public final static int STROAGE = 0;
    //权限集合
    public static String[] pers = {Manifest.permission.READ_EXTERNAL_STORAGE};
    //当前需要申请的权限集合
    public static int[] permissions;

    public static void setPermission(int... permission){
        permissions = permission;
    }

    public static void checkPermission(Activity activity,int... permission){
        Log.d("huangyaling","checkPermission");
        setPermission(permission);
        List<String> list = new ArrayList<>();
        for(int i = 0;i<permissions.length;i++){
            if(permissions[i] >= pers.length){
                Toast.makeText(activity,activity.getString(R.string.permissons_toast),Toast.LENGTH_SHORT).show();
                return;
            }
            if(ContextCompat.checkSelfPermission(activity,pers[permissions[i]]) != PackageManager.PERMISSION_GRANTED){
                list.add(pers[permissions[i]]);
            }
        }
        if(list.size()>0){
            String[] a = new String[list.size()];
            list.toArray(a);
            ActivityCompat.requestPermissions(activity,a,PERMISSIONCODE);
        }
    }

}
