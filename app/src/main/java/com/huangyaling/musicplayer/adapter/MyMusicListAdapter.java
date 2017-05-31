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
import com.huangyaling.musicplayer.view.MyMusicListViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;

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
        Log.d("huangyaling","mymusiclist:"+mymusiclist);
    }

    @Override
    public int getCount() {
        Log.d("huangyaling","mymusiclist.size():"+mymusiclist.size());
        return mymusiclist.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("huangyaling","mymusiclist.get(position):"+mymusiclist.get(position));
        return mymusiclist.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d("huangyaling","position:"+position);
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
            Log.d("huangyaling", "1.myMusicListViewHolder:" + myMusicListViewHolder);
        }else{
            myMusicListViewHolder = (MyMusicListViewHolder) convertView.getTag();
            Log.d("huangyaling", "2.myMusicListViewHolder:" + myMusicListViewHolder);
        }
        myMusicListViewHolder.TypeIcon.setImageResource(Integer.parseInt(mymusiclist.get(position).get("mymusic_icon").toString()));
        myMusicListViewHolder.TypeTitle.setText(mymusiclist.get(position).get("mymusic_type").toString());
        Log.d("huangyaling", "convertView:" + convertView);
        return convertView;
    }
}
