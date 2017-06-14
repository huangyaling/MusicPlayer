package com.huangyaling.musicplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.service.MyMusiceService;

import org.w3c.dom.Text;

/**
 * Created by huangyaling on 2017/6/9.
 */
public class CurrentMusicActivity extends Activity implements View.OnClickListener{
    private Intent intent;
    private Bundle bundle;
    private int musicIndex = -1;

    private ImageView playMode;
    private ImageView playPre;
    private ImageView playCurrent;
    private ImageView playNext;
    private ImageView playMenu;

    private TextView song;
    private TextView singer;

    private boolean isPlaying = true;
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

        song = (TextView) findViewById(R.id.current_song);
        singer = (TextView) findViewById(R.id.current_singer);

        playMode.setOnClickListener(this);
        playPre.setOnClickListener(this);
        playCurrent.setOnClickListener(this);
        playNext.setOnClickListener(this);
        playMenu.setOnClickListener(this);

        intent = new Intent(this,MyMusiceService.class);
        bundle = new Bundle();

        song.setText(getIntent().getExtras().getString("song"));
        singer.setText(getIntent().getExtras().getString("singer"));

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
                if(isPlaying){
                    playCurrent.setImageResource(R.drawable.current_pause);
                    pauseMusic();
                    isPlaying = false;
                }else{
                    playCurrent.setImageResource(R.drawable.current_play);
                    playMusic();
                    isPlaying = true;
                }
                break;
            case R.id.play_next:
                break;
            case R.id.play_menu:
                break;
        }

    }
}
