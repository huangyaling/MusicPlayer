package com.huangyaling.musicplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by huangyaling on 2017/5/26.
 */
public class DrawLayoutListViewAdapter extends BaseAdapter {
    ArrayList<String> listTitle = new ArrayList<String>();
    ArrayList<String> listicon = new ArrayList<String>();
    public DrawLayoutListViewAdapter(Context context){

    }
    @Override
    public int getCount() {
        return listTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
