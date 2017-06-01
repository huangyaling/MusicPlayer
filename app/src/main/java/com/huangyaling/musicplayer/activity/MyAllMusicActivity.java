package com.huangyaling.musicplayer.activity;

import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.adapter.AllMusicListViewAdapter;
import com.huangyaling.musicplayer.bean.MusicInfoBean;
import com.huangyaling.musicplayer.utils.MusicUtils;
import com.huangyaling.musicplayer.utils.PermissionCheckUtils;

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
        PermissionCheckUtils.checkPermission(this,0);
        /*try{
            list = MusicUtils.getMusicData(this);
        }catch(Exception e){

        }
        allMusicListViewAdapter = new AllMusicListViewAdapter(this,list);
        musicListView.setAdapter(allMusicListViewAdapter);*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 999){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                list = MusicUtils.getMusicData(this);
                Log.d("huangyaling","list:"+list);
                allMusicListViewAdapter = new AllMusicListViewAdapter(this,list);
                musicListView.setAdapter(allMusicListViewAdapter);
            }else{
                finish();
            }

        }
    }
}
