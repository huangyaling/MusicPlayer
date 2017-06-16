package com.huangyaling.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.huangyaling.musicplayer.bean.SongBean;
import com.huangyaling.musicplayer.utils.AppControlUtils;
import com.huangyaling.musicplayer.utils.LocalMusicUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by huangyaling on 2017/6/15.
 */
public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    private String path;
    private int msg;
    private boolean isPause;
    private int mCurrentPosition;
    private int currentTime;
    private int duration;
    private ArrayList<SongBean> mp3infos;

    public static final String UPDATE_ACTION = "com.huangyaling.musicplayer.UPDATE_ACTION";
    public static final String CTL_ACTION = "com.huangyaling.musicplayer.CTL_ACTION";
    public static final String MUSIC_CURRENT = "com.huangyaling.musicplayer.MUSIC_CURRENT";
    public static final String MUSIC_DURATION = "com.huangyaling.musicplayer.MUSIC_DURATION";

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == 1){
                if(mediaPlayer != null){
                    currentTime = mediaPlayer.getCurrentPosition();
                    Intent intent = new Intent();
                    intent.setAction(MUSIC_CURRENT);
                    intent.putExtra("currentTime", currentTime);
                    sendBroadcast(intent);
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            }
        };
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mp3infos = LocalMusicUtil.getAllSongs(PlayerService.this);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCurrentPosition++;
                if(mCurrentPosition < mp3infos.size()){
                    Intent sentIntent = new Intent(UPDATE_ACTION);
                    sentIntent.putExtra("current",mCurrentPosition);
                    sendBroadcast(sentIntent);
                    path = mp3infos.get(mCurrentPosition).getUrl();
                    play(0);
                }else{
                    mediaPlayer.seekTo(0);
                    mCurrentPosition = 0;
                    Intent sendIntent = new Intent(UPDATE_ACTION);
                    sendIntent.putExtra("current",mCurrentPosition);
                    sendBroadcast(sendIntent);
                }
            }
        });
        Log.d("huangyaling", "mCurrentPosition = " + mCurrentPosition);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if(intent == null){
            stopSelf();
        }else{
            path = intent.getStringExtra("url");
            msg = intent.getIntExtra("MSG",0);
            mCurrentPosition = intent.getIntExtra("position",-1);
            if(msg == AppControlUtils.PLAY_MSG){
                play(0);
            }else if(msg == AppControlUtils.PAUSE_MSG){
                pause();
            }else if(msg == AppControlUtils.RESUME_MSG){
                resume();
            }
        }
        super.onStart(intent, startId);
    }

    private void play(int currentTime){
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));
            handler.sendEmptyMessage(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pause(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            isPause = true;
        }
    }

    private void resume(){
        if(isPause){
            mediaPlayer.start();
            isPause = false;
        }
    }

    private void stop(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private final class PreparedListener implements MediaPlayer.OnPreparedListener{

        private int currentTime;
        public PreparedListener(int currentTime){
            this.currentTime = currentTime;
        }
        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();
            if(currentTime > 0){
                mediaPlayer.seekTo(currentTime);
            }
            Intent intent = new Intent();
            intent.setAction(MUSIC_DURATION);
            duration = mediaPlayer.getDuration();
            intent.putExtra("duration",duration);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onDestroy() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
