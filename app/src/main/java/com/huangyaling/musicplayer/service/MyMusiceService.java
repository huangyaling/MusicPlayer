package com.huangyaling.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.huangyaling.musicplayer.R;

import java.net.URI;


/**
 * Created by huangyaling on 2017/6/9.
 */
public class MyMusiceService extends Service {

    private final String TAG = "MyMusiceService";
    private MediaPlayer mediaPlayer;
    private int startId;
    private Uri uri;
    public enum Control{
        PLAY,PAUSE,STOP
    }
    public MyMusiceService(){
    }

    @Override
    public void onCreate() {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.player);
            mediaPlayer.setLooping(false);
        }
        Log.d(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Control control = (Control) bundle.getSerializable("Key");
            switch (control){
                case PLAY:
                    play();
                    break;
                case PAUSE:
                    pause();
                    break;
                case STOP:
                    stop();
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    private void play(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    private void pause(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void stop(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
        stopSelf(startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();
        uri = Uri.parse(bundle.getString("path"));
        return null;
    }
}
