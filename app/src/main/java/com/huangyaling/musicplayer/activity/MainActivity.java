package com.huangyaling.musicplayer.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.fragment.TabAccountFragment;
import android.util.Log;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout tab_account;
    private LinearLayout tab_discover;


    private LinearLayout tab_music;
    private LinearLayout tab_friend;

    private TabAccountFragment tabAccount;
    private FragmentManager mFragmentManager;

    //private List<Fragment> mFragemnt = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        setTabSelection(0);
    }

    private void initView(){
        tab_account = (LinearLayout) findViewById(R.id.tab_account);
        tab_discover = (LinearLayout) findViewById(R.id.tab_discover);
        tab_music = (LinearLayout) findViewById(R.id.tab_music);
        tab_friend = (LinearLayout) findViewById(R.id.tab_friend);
        //tabAccount = new TabAccountFragment();
        //mFragemnt.add(tabAccount);

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
            case 0:
                if(tabAccount == null){
                    tabAccount = new TabAccountFragment();
                    transaction.add(R.id.fragment_content,tabAccount);
                    Log.d("huangyaling", "transaction add");
                    transaction.show(tabAccount);
                }else{
                    transaction.show(tabAccount);
                    Log.d("huangyaling", "transaction show");
                }
                break;
        }
        transaction.commit();

    }

    private void hideFragment(FragmentTransaction transaction){
        if(tabAccount!=null){
            transaction.hide(tabAccount);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_account:
                setTabSelection(0);
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
