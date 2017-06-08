package com.huangyaling.musicplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.bean.MusicInfoBean;
import com.huangyaling.musicplayer.utils.MusicUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyaling on 2017/5/31.
 */
public class AllMusicListViewAdapter extends BaseAdapter {
    List<MusicInfoBean> list;
    public Context mContext;

    public AllMusicListViewAdapter(Context mContext,List<MusicInfoBean> list) {
        this.mContext = mContext;
        this.list = list;
        Log.d("huangyaling","AllMusicListViewAdapter list:"+list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AllMusicViewHolder allMusicViewHolder = null;
        if(convertView == null){
            allMusicViewHolder = new AllMusicViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.allmusic_list_item,null);
            allMusicViewHolder.song = (TextView)convertView.findViewById(R.id.allmusic_song);
            allMusicViewHolder.singer = (TextView) convertView.findViewById(R.id.allmusic_singer);
            allMusicViewHolder.duration = (TextView) convertView.findViewById(R.id.allmusic_duration);
            convertView.setTag(allMusicViewHolder);
        }else{
            allMusicViewHolder = (AllMusicViewHolder) convertView.getTag();
        }
        allMusicViewHolder.song.setText(list.get(position).song);
        allMusicViewHolder.singer.setText(list.get(position).singer);
        allMusicViewHolder.duration.setText(MusicUtils.formatTime(list.get(position).duration));
        Log.d("huangyaling","convertView:"+convertView);
        return convertView;
    }

    class AllMusicViewHolder{
        public TextView song;//歌曲
        public TextView singer;//歌手
        public TextView duration;//歌曲时长
    }
}
