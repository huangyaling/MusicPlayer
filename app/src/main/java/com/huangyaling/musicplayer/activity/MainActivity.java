package com.huangyaling.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.adapter.DrawLayoutListViewAdapter;
import com.huangyaling.musicplayer.adapter.ViewPagerAdapter;

import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout tab_account;
    private LinearLayout tab_discover;
    private LinearLayout tab_music;
    private LinearLayout tab_friend;
    private ListView drawLayoutListView;

    private ViewPager pager = null;
    private PagerTabStrip tabStrip = null;
    private ViewPagerAdapter viewPagerAdapter;
    private DrawLayoutListViewAdapter drawLayoutListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void initView(){
        tab_account = (LinearLayout) findViewById(R.id.tab_account);
        tab_discover = (LinearLayout) findViewById(R.id.tab_discover);
        tab_music = (LinearLayout) findViewById(R.id.tab_music);
        tab_friend = (LinearLayout) findViewById(R.id.tab_friend);
        drawLayoutListView = (ListView) findViewById(R.id.drawlayout_listview);

        pager = (ViewPager) findViewById(R.id.main_viewpager);
        tabStrip = (PagerTabStrip) findViewById(R.id.viewpager_tab);
        tabStrip.setTextSpacing(50);
    }

    private void init(){
        tab_account.setOnClickListener(this);
        tab_discover.setOnClickListener(this);
        tab_music.setOnClickListener(this);
        tab_friend.setOnClickListener(this);
        viewPagerAdapter = new ViewPagerAdapter(this);
        pager.setAdapter(viewPagerAdapter);
        drawLayoutListViewAdapter = new DrawLayoutListViewAdapter(this);
        drawLayoutListView.setAdapter(drawLayoutListViewAdapter);
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
                break;
            case R.id.tab_friend:
                break;
        }
    }
}
