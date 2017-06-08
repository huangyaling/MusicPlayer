package com.huangyaling.musicplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.huangyaling.musicplayer.bean.MusicInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyaling on 2017/5/31.
 */
public class MusicUtils {
    public static List<MusicInfoBean> getMusicData(Context context){
        List<MusicInfoBean> list = new ArrayList<MusicInfoBean>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null
        ,null,null,MediaStore.Audio.AudioColumns.IS_MUSIC);
        if(cursor != null){
            while(cursor.moveToNext()){
                MusicInfoBean musicInfoBean = new MusicInfoBean();
                musicInfoBean.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                musicInfoBean.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                musicInfoBean.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                musicInfoBean.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                musicInfoBean.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                if(musicInfoBean.size>1000*800){
                    if(musicInfoBean.song.contains("-")){
                        String[] str = musicInfoBean.song.split("-");
                        musicInfoBean.song = str[0];
                        musicInfoBean.singer = str[1];
                    }
                    Log.d("huangyaling","musicInfoBean.song : "+musicInfoBean.song);
                    Log.d("huangyaling","musicInfoBean.singer : "+musicInfoBean.singer);
                    Log.d("huangyaling","musicInfoBean.path : "+musicInfoBean.path);
                    Log.d("huangyaling","musicInfoBean.duration : "+musicInfoBean.duration);
                    Log.d("huangyaling","musicInfoBean.size : "+musicInfoBean.size);
                    list.add(musicInfoBean);
                }
            }
            Log.d("huangyaling","MusicUtils list:"+list);
            cursor.close();
        }
        return list;
    }

    public static String formatTime(int time){
        if(time/1000%60<10){
            return time/1000/60+":0"+time/1000%60;
        }else{
            return time/1000/60+":"+time/1000%60;
        }
    }
}
