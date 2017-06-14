package com.huangyaling.musicplayer.activity;

import android.Manifest;
import android.animation.PropertyValuesHolder;
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
import android.widget.Toast;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.adapter.AllMusicListViewAdapter;
import com.huangyaling.musicplayer.bean.MusicInfoBean;
import com.huangyaling.musicplayer.utils.MusicUtils;

import java.util.List;

/**
 * Created by huangyaling on 2017/5/31.
 */
public class MyAllMusicActivity extends Activity implements AdapterView.OnItemClickListener{
    private ListView musicListView;
    private AllMusicListViewAdapter allMusicListViewAdapter;
    private List<MusicInfoBean> list = null;
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
                    list = MusicUtils.getMusicData(this);
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
            list = MusicUtils.getMusicData(this);
            Log.d("huangyaling","list:"+list);
            allMusicListViewAdapter = new AllMusicListViewAdapter(MyAllMusicActivity.this,list);
            Log.d("huangyaling","allMusicListViewAdapter = "+allMusicListViewAdapter);
            musicListView.setAdapter(allMusicListViewAdapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,CurrentMusicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("uri",list.get(position).getPath());
        bundle.putString("song",list.get(position).getSong());
        bundle.putString("singer",list.get(position).getSinger());
        bundle.putInt("position",position);
        /*intent.putExtra("position",position);
        intent.putExtra("uri",list.get(position).getPath());
        intent.putExtra("song",list.get(position).getSong());
        intent.putExtra("singer",list.get(position).getSinger());*/
        Log.d("huangyaling", "uri = " + list.get(position).getPath());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
