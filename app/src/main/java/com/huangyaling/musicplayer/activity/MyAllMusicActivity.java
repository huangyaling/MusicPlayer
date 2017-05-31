package com.huangyaling.musicplayer.activity;

import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.adapter.AllMusicListViewAdapter;
import com.huangyaling.musicplayer.bean.MusicInfoBean;
import com.huangyaling.musicplayer.utils.MusicUtils;

import java.util.List;

/**
 * Created by huangyaling on 2017/5/31.
 */
public class MyAllMusicActivity extends Activity {
    private ListView musicListView;
    private AllMusicListViewAdapter allMusicListViewAdapter;
    private List<MusicInfoBean> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allmusic_listview);
        init();
    }
    public void init(){
        musicListView = (ListView) findViewById(R.id.allmusic_listview);
        try{
            list = MusicUtils.getMusicData(this);
        }catch(Exception e){

        }

        allMusicListViewAdapter = new AllMusicListViewAdapter(this,list);
        musicListView.setAdapter(allMusicListViewAdapter);

    }
}
