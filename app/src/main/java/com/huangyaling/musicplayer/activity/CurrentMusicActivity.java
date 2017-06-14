package com.huangyaling.musicplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.service.MyMusiceService;

/**
 * Created by huangyaling on 2017/6/9.
 */
public class CurrentMusicActivity extends Activity implements View.OnClickListener{
    private Intent intent;
    private Bundle bundle;
    private int musicIndex = -1;
    ImageView playMode;
    ImageView playPre;
    ImageView playCurrent;
    ImageView playNext;
    ImageView playMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_music);
        init();
    }

    public void init(){
        playMode = (ImageView) findViewById(R.id.play_mode);
        playPre = (ImageView) findViewById(R.id.play_pre);
        playCurrent = (ImageView) findViewById(R.id.play_current);
        playNext = (ImageView) findViewById(R.id.play_next);
        playMenu = (ImageView) findViewById(R.id.play_menu);

        playMode.setOnClickListener(this);
        playPre.setOnClickListener(this);
        playCurrent.setOnClickListener(this);
        playNext.setOnClickListener(this);
        playMenu.setOnClickListener(this);

        intent = new Intent(this,MyMusiceService.class);
        bundle = new Bundle();

        playMusic();
    }

    public void playMusic(){
        bundle.putSerializable("Key",MyMusiceService.Control.PLAY);
        intent.putExtras(bundle);
        startService(intent);
    }

    public void pauseMusic(){
        bundle.putSerializable("Key",MyMusiceService.Control.PAUSE);
        intent.putExtras(bundle);
        startService(intent);
    }

    public void stopMusic(){
        bundle.putSerializable("Key",MyMusiceService.Control.STOP);
        intent.putExtras(bundle);
        startService(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_mode:
                break;
            case R.id.play_pre:
                break;
            case R.id.play_current:
                pauseMusic();
                break;
            case R.id.play_next:
                break;
            case R.id.play_menu:
                break;
        }

    }
}
