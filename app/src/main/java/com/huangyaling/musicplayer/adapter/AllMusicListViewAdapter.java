package com.huangyaling.musicplayer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.bean.SongBean;
import com.huangyaling.musicplayer.utils.LocalMusicUtil;

import java.util.List;

/**
 * Created by huangyaling on 2017/5/31.
 */
public class AllMusicListViewAdapter extends BaseAdapter {
    List<SongBean> list;
    public Context mContext;

    public AllMusicListViewAdapter(Context mContext,List<SongBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        AllMusicViewHolder allMusicViewHolder = null;
        if(convertView == null){
            allMusicViewHolder = new AllMusicViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.allmusic_list_item,null);
            allMusicViewHolder.song = (TextView)convertView.findViewById(R.id.allmusic_song);
            allMusicViewHolder.singer = (TextView) convertView.findViewById(R.id.allmusic_singer);
            allMusicViewHolder.duration = (TextView) convertView.findViewById(R.id.allmusic_duration);
            allMusicViewHolder.overflow = (ImageView) convertView.findViewById(R.id.overflowmenu);
            convertView.setTag(allMusicViewHolder);
        }else{
            allMusicViewHolder = (AllMusicViewHolder) convertView.getTag();
        }
        allMusicViewHolder.song.setText(list.get(position).displayName);
        allMusicViewHolder.singer.setText(list.get(position).artist);
        allMusicViewHolder.duration.setText(LocalMusicUtil.formatTime(list.get(position).duration));
        allMusicViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog(position);
            }
        });
        Log.d("huangyaling","convertView:"+convertView);
        return convertView;
    }

    public void bottomDialog(int position){
        Dialog bottomDialog = new Dialog(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.bottomdialog, null);
        TextView currentMusic = (TextView) dialogView.findViewById(R.id.current_music);
        currentMusic.setText(dialogView.getResources().getString(R.string.bottom_title)+list.get(position).displayName);
        bottomDialog.setContentView(dialogView);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)dialogView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        layoutParams.bottomMargin = 8;
        dialogView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.show();
    }

    class AllMusicViewHolder{
        public TextView song;//歌曲
        public TextView singer;//歌手
        public TextView duration;//歌曲时长
        public ImageView overflow;//overflow菜单
    }
}
