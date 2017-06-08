package com.huangyaling.musicplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.adapter.DrawLayoutListViewAdapter;
import com.huangyaling.musicplayer.adapter.MyMusicListAdapter;
import com.huangyaling.musicplayer.adapter.ViewPagerAdapter;


public class MainActivity extends Activity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private LinearLayout tab_account;
    private LinearLayout tab_discover;
    private LinearLayout tab_music;
    private LinearLayout tab_friend;
    private ListView drawLayoutListView;
    private ListView myMusicListView;

    private ViewPager pager = null;
    private PagerTabStrip tabStrip = null;
    private ViewPagerAdapter viewPagerAdapter;
    private DrawLayoutListViewAdapter drawLayoutListViewAdapter;
    private MyMusicListAdapter myMusicListAdapter;

    private static final int SettingsItem = 0;
    private static final int Friend = 1;
    private static final int Clock = 2;
    private static final int Message = 3;
    private static final int LoginOut = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("huangyaling","oncreate mainactivity");
        initView();
        init();
    }

    private void initView(){
        viewPagerAdapter = new ViewPagerAdapter(this);
        tab_account = (LinearLayout) findViewById(R.id.tab_account);
        tab_discover = (LinearLayout) findViewById(R.id.tab_discover);
        tab_music = (LinearLayout) findViewById(R.id.tab_music);
        tab_friend = (LinearLayout) findViewById(R.id.tab_friend);
        drawLayoutListView = (ListView) findViewById(R.id.drawlayout_listview);

        myMusicListView = (ListView) viewPagerAdapter.viewsContainer.get(1).findViewById(R.id.mymusic_list);

        pager = (ViewPager) findViewById(R.id.main_viewpager);
        tabStrip = (PagerTabStrip) findViewById(R.id.viewpager_tab);
        tabStrip.setTextSpacing(50);
    }

    private void init(){
        tab_account.setOnClickListener(this);
        tab_discover.setOnClickListener(this);
        tab_music.setOnClickListener(this);
        tab_friend.setOnClickListener(this);

        pager.setAdapter(viewPagerAdapter);
        drawLayoutListViewAdapter = new DrawLayoutListViewAdapter(this);
        drawLayoutListView.setAdapter(drawLayoutListViewAdapter);
        drawLayoutListView.setOnItemClickListener(this);
        myMusicListAdapter = new MyMusicListAdapter(this);
        myMusicListView.setAdapter(myMusicListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_account:
                startActivity(new Intent(MainActivity.this,AccountActivity.class));
                break;
            case R.id.tab_discover:
                break;
            case R.id.tab_music:
                startActivity(new Intent(MainActivity.this,MyAllMusicActivity.class));
                break;
            case R.id.tab_friend:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case SettingsItem:
                Toast.makeText(this,"SettingsItem",Toast.LENGTH_SHORT).show();
                break;
            case Friend:
                Toast.makeText(this,"Friend",Toast.LENGTH_SHORT).show();
                break;
            case Clock:
                Toast.makeText(this,"Clock",Toast.LENGTH_SHORT).show();
                break;
            case Message:
                Toast.makeText(this,"Message",Toast.LENGTH_SHORT).show();
                break;
            case LoginOut:
                Toast.makeText(this,"LoginOut",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
