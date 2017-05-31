package com.huangyaling.musicplayer.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huangyaling.musicplayer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangyaling on 2017/5/30.
 */
public class MyMusicListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Map<String,Object>> mymusiclist = new ArrayList<Map<String, Object>>();
    private String[] mymusicType = null;
    private TypedArray mymusicIcon = null;

    public MyMusicListAdapter(Context mContext) {
        this.mContext = mContext;
        mymusicType = mContext.getResources().getStringArray(R.array.mymusic_list);
        mymusicIcon = mContext.getResources().obtainTypedArray(R.array.mymusic_list_icon);
        for(int i = 0;i<mymusicType.length;i++){
            Map<String,Object> musicMap = new HashMap<String, Object>();
            musicMap.put("mymusic_type",mymusicType[i]);
            musicMap.put("mymusic_icon",mymusicIcon.getResourceId(i,0));
            mymusiclist.add(musicMap);
        }
    }

    @Override
    public int getCount() {
        return mymusiclist.size();
    }

    @Override
    public Object getItem(int position) {
        return mymusiclist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyMusicListViewHolder myMusicListViewHolder = null;
        if(convertView == null){
            myMusicListViewHolder = new MyMusicListViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mymusic_listview_item,null);
            myMusicListViewHolder.TypeTitle = (TextView) convertView.findViewById(R.id.mymusic_list_title);
            myMusicListViewHolder.TypeIcon = (ImageView) convertView.findViewById(R.id.mymusic_list_icon);
            convertView.setTag(myMusicListViewHolder);
        }else{
            myMusicListViewHolder = (MyMusicListViewHolder) convertView.getTag();
        }
        myMusicListViewHolder.TypeIcon.setImageResource(Integer.parseInt(mymusiclist.get(position).get("mymusic_icon").toString()));
        myMusicListViewHolder.TypeTitle.setText(mymusiclist.get(position).get("mymusic_type").toString());
        return convertView;
    }
 class MyMusicListViewHolder {
        public TextView TypeTitle;
        public ImageView TypeIcon;
    }
}
