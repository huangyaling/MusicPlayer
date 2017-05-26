package com.huangyaling.musicplayer.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.fragment.TabAccountFragment;
import com.huangyaling.musicplayer.fragment.TabDiscoverFragment;
import com.huangyaling.musicplayer.fragment.TabFriendFragment;
import com.huangyaling.musicplayer.fragment.TabMusicFragment;

import android.util.Log;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private final static int TAB_ACCOUNT = 0;
    private final static int TAB_DISCOVER = 1;
    private final static int TAB_MUSIC = 2;
    private final static int TAB_FRIEND = 3;

    private LinearLayout tab_account;
    private LinearLayout tab_discover;
    private LinearLayout tab_music;
    private LinearLayout tab_friend;

    private TabAccountFragment tabAccountFragment;
    private TabDiscoverFragment tabDiscoverFragment;
    private TabFriendFragment tabFriendFragment;
    private TabMusicFragment tabMusicFragment;

    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        setTabSelection(TAB_ACCOUNT);
    }

    private void initView(){
        tab_account = (LinearLayout) findViewById(R.id.tab_account);
        tab_discover = (LinearLayout) findViewById(R.id.tab_discover);
        tab_music = (LinearLayout) findViewById(R.id.tab_music);
        tab_friend = (LinearLayout) findViewById(R.id.tab_friend);
    }

    private void init(){
        tab_account.setOnClickListener(this);
        tab_discover.setOnClickListener(this);
        tab_music.setOnClickListener(this);
        tab_friend.setOnClickListener(this);
        mFragmentManager = getFragmentManager();
    }

    private void setTabSelection(int selection){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (selection){
            case TAB_ACCOUNT:
                if(tabAccountFragment == null){
                    tabAccountFragment = new TabAccountFragment();
                    transaction.add(R.id.fragment_content, tabAccountFragment);
                    Log.d("huangyaling", "transaction add");
                }else{
                    transaction.show(tabAccountFragment);
                    Log.d("huangyaling", "transaction show");
                }
                break;
            case TAB_DISCOVER:
                if(tabDiscoverFragment == null){
                    tabDiscoverFragment = new TabDiscoverFragment();
                    transaction.add(R.id.fragment_content,tabDiscoverFragment);
                }else{
                    transaction.show(tabDiscoverFragment);
                }
                break;
            case TAB_MUSIC:
                if(tabMusicFragment == null){
                    tabMusicFragment = new TabMusicFragment();
                    transaction.add(R.id.fragment_content,tabMusicFragment);
                }else{
                    transaction.show(tabMusicFragment);
                }
                break;
            case TAB_FRIEND:
                if(tabFriendFragment == null){
                    tabFriendFragment = new TabFriendFragment();
                    transaction.add(R.id.fragment_content,tabFriendFragment);
                }else{
                    transaction.show(tabFriendFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if(tabAccountFragment != null){
            transaction.hide(tabAccountFragment);
        }
        if(tabDiscoverFragment != null){
            transaction.hide(tabDiscoverFragment);
        }
        if(tabFriendFragment != null){
            transaction.hide(tabFriendFragment);
        }
        if(tabMusicFragment != null){
            transaction.hide(tabMusicFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_account:
                setTabSelection(TAB_ACCOUNT);
                break;
            case R.id.tab_discover:
                setTabSelection(TAB_DISCOVER);
                break;
            case R.id.tab_music:
                setTabSelection(TAB_MUSIC);
                break;
            case R.id.tab_friend:
                setTabSelection(TAB_FRIEND);
                break;
        }
    }
}
