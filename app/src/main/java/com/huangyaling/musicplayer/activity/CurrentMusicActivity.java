package com.huangyaling.musicplayer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.bean.SongBean;
import com.huangyaling.musicplayer.service.PlayerService;
import com.huangyaling.musicplayer.utils.AppControlUtils;
import com.huangyaling.musicplayer.utils.LocalMusicUtil;

import java.util.ArrayList;

/**
 * Created by huangyaling on 2017/6/9.
 */
public class CurrentMusicActivity extends Activity implements View.OnClickListener{
    private Intent intent;
    private Bundle bundle;

    private SeekBar musicSeekBar;

    private ImageView playMode;
    private ImageView playPre;
    private ImageView playCurrent;
    private ImageView playNext;
    private ImageView playMenu;

    private TextView song;
    private TextView singer;
    private TextView seekbatTime;
    private TextView countTime;

    private static int mCurrentPosition = -1;
    private String url;
    private SongBean mSongBean;
    private ArrayList<SongBean> musicInfos;
    private int currentTime;
    private int duration;
    private PlayerService mPlayService;
    private Boolean flag;
    private boolean isPlaying;
    private boolean isPause;
    private PlayReceiver mPlayReceiver;


    public static final String UPDATE_ACTION = "com.huangyaling.musicplayer.UPDATE_ACTION";
    public static final String CTL_ACTION = "com.huangyaling.musicplayer.CTL_ACTION";
    public static final String MUSIC_CURRENT = "com.huangyaling.musicplayer.MUSIC_CURRENT";
    public static final String MUSIC_DURATION = "com.huangyaling.musicplayer.MUSIC_DURATION";
    public static final String MUSIC_PALYING = "com.huangyaling.musicplayer.MUSIC_PLAYING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_music);
        init();
    }

    public void init(){
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",-1);
        if(position != mCurrentPosition){
            mCurrentPosition = position;
            flag = true;
            isPlaying = true;
            isPause = false;
        }else{
            flag = false;
            isPause = false;
            isPlaying = true;
        }
        musicInfos = LocalMusicUtil.getAllSongs(this);
        mSongBean = musicInfos.get(mCurrentPosition);
        mPlayService = new PlayerService();
        mPlayReceiver = new PlayReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);
        filter.addAction(MUSIC_CURRENT);
        filter.addAction(MUSIC_DURATION);
        registerReceiver(mPlayReceiver, filter);

        if(flag){
            play();
            isPlaying = true;
            isPause = false;
        }else{
            Intent intent1 = new Intent(this,PlayerService.class);
            intent1.putExtra("MSG",1);
            intent1.putExtra("position",mCurrentPosition);
            intent1.setAction("com.huangyaling.musicplayer.MUSIC_SERVICE");
            startService(intent1);
            isPlaying = true;
            isPause = false;
        }

        playMode = (ImageView) findViewById(R.id.play_mode);
        playPre = (ImageView) findViewById(R.id.play_pre);
        playCurrent = (ImageView) findViewById(R.id.play_current);
        playNext = (ImageView) findViewById(R.id.play_next);
        playMenu = (ImageView) findViewById(R.id.play_menu);

        song = (TextView) findViewById(R.id.current_song);
        singer = (TextView) findViewById(R.id.current_singer);
        seekbatTime = (TextView) findViewById(R.id.current_time);
        countTime = (TextView) findViewById(R.id.count_time);

        musicSeekBar = (SeekBar) findViewById(R.id.music_seekbar);

        song.setText(intent.getStringExtra("displayName"));
        singer.setText(intent.getStringExtra("artist"));
        countTime.setText(LocalMusicUtil.formatTime(mSongBean.getDuration()));

        playMode.setOnClickListener(this);
        playPre.setOnClickListener(this);
        playCurrent.setOnClickListener(this);
        playNext.setOnClickListener(this);
        playMenu.setOnClickListener(this);
    }

    public void play(){
        Intent intent = new Intent();
        intent.setAction("com.huangyaling.musicplayer.MUSIC_SERVICE");
        intent.setClass(this, PlayerService.class);
        intent.putExtra("url", mSongBean.getUrl());
        intent.putExtra("position", mCurrentPosition);
        intent.putExtra("MSG", AppControlUtils.PLAY_MSG);
        startService(intent);
        isPlaying = true;
        isPause = false;
    }

    public void pause(){
        Intent intent = new Intent();
        intent.setClass(this, PlayerService.class);
        intent.setAction("com.huangyaling.musicplayer.MUSIC_SERVICE");
        intent.putExtra("MSG", AppControlUtils.PAUSE_MSG);
        startService(intent);
        playCurrent.setImageResource(R.drawable.current_pause);
        isPlaying = false;
        isPause = true;
    }

    public void resume(){
        Intent intent = new Intent();
        intent.setAction("com.huangyaling.musicplayer.MUSIC_SERVICE");
        intent.setClass(this, PlayerService.class);
        intent.putExtra("MSG", AppControlUtils.RESUME_MSG);
        startService(intent);
        isPause = false;
        isPlaying = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_mode:
                break;
            case R.id.play_pre:
                mCurrentPosition--;
                if(mCurrentPosition >= 0){
                    mSongBean = musicInfos.get(mCurrentPosition);
                    song.setText(mSongBean.getDisplayName());
                    singer.setText(mSongBean.getArtist());
                    musicSeekBar.setProgress(0);
                }else{
                    mCurrentPosition = 0;
                    Toast.makeText(this,getResources().getString(R.string.no_pre_music),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.play_current:
                if(isPlaying){
                    playCurrent.setImageResource(R.drawable.current_pause);
                    pause();
                }else if(isPause){
                    playCurrent.setImageResource(R.drawable.current_play);
                    resume();
                }else{
                    playCurrent.setImageResource(R.drawable.current_play);
                    play();
                }
                Log.d("huangyaling","isPlaying = "+isPlaying+":isPause = "+isPause);
                break;
            case R.id.play_next:
                mCurrentPosition++;
                if(mCurrentPosition<musicInfos.size()){
                    mSongBean = musicInfos.get(mCurrentPosition);
                    song.setText(mSongBean.getDisplayName());
                    singer.setText(mSongBean.getArtist());
                }else{
                    mCurrentPosition = musicInfos.size()-1;
                    Toast.makeText(this,getResources().getString(R.string.no_pre_music),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.play_menu:
                break;
        }
    }

    public class PlayReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(MUSIC_CURRENT)){
                currentTime = intent.getIntExtra("currentTime",-1);
                seekbatTime.setText(LocalMusicUtil.formatTime(currentTime));
                musicSeekBar.setProgress(currentTime);
            }else if(action.equals(MUSIC_DURATION)){
                duration = intent.getIntExtra("duration",-1);
                musicSeekBar.setMax(duration);
            }else if(action.equals(UPDATE_ACTION)){
                mCurrentPosition = intent.getIntExtra("currenr",-1);
                mSongBean = musicInfos.get(mCurrentPosition);
                url = mSongBean.getUrl();
                if(mCurrentPosition>0){

                }
            }
        }
    }
}
