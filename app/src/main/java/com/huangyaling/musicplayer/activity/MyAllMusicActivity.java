package com.huangyaling.musicplayer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.adapter.AllMusicListViewAdapter;
import com.huangyaling.musicplayer.bean.SongBean;
import com.huangyaling.musicplayer.utils.LocalMusicUtil;

import java.util.List;

/**
 * Created by huangyaling on 2017/5/31.
 */
public class MyAllMusicActivity extends Activity implements AdapterView.OnItemClickListener{
    private ListView musicListView;
    private AllMusicListViewAdapter allMusicListViewAdapter;
    private List<SongBean> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allmusic_listview);
        Log.d("huangyaling", "onCreate myallMusicActivity");
        init();
        requestPermissions();
    }
    public void init(){
        musicListView = (ListView) findViewById(R.id.allmusic_listview);
        musicListView.setOnItemClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    list = LocalMusicUtil.getAllSongs(this);
                    Log.d("huangyaling","list:"+list);
                    allMusicListViewAdapter = new AllMusicListViewAdapter(this,list);
                    musicListView.setAdapter(allMusicListViewAdapter);
                }else{
                    Log.d("huangyaling","reject permission");
                }
                break;
            default:
                break;
        }
    }

    public void requestPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            list = LocalMusicUtil.getAllSongs(this);
            Log.d("huangyaling","list:"+list);
            allMusicListViewAdapter = new AllMusicListViewAdapter(MyAllMusicActivity.this,list);
            Log.d("huangyaling","allMusicListViewAdapter = "+allMusicListViewAdapter);
            musicListView.setAdapter(allMusicListViewAdapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,CurrentMusicActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("displayName",list.get(position).getDisplayName());
        intent.putExtra("artist",list.get(position).getArtist());
        Log.d("huangyaling", "position = " + position);
        startActivity(intent);
    }
}
