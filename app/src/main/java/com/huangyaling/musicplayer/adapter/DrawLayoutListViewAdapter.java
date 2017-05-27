package com.huangyaling.musicplayer.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.projection.MediaProjection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.view.DrawLayoutListViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangyaling on 2017/5/26.
 */
public class DrawLayoutListViewAdapter extends BaseAdapter {
    //ImageView[] listIcon = null;
    TypedArray listIcon = null;
    String[] listTitle = null;
    private Context mContext;
    ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
    public DrawLayoutListViewAdapter(Context context){
        mContext = context;
        listTitle = context.getResources().getStringArray(R.array.drawlayout_list);
        listIcon = context.getResources().obtainTypedArray(R.array.drawlayout_list_icon);
        //listIcon = context.getResources().getStringArray(R.array.drawlayout_list_icon);
        for(int i = 0;i<listTitle.length;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("drawlayout_list_title",listTitle[i]);
            map.put("drawlayout_list_icon",listIcon.getResourceId(i,0));
            list.add(map);
        }
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
        DrawLayoutListViewHolder drawLayoutListViewHolder = null;
        if(convertView == null){
            drawLayoutListViewHolder = new DrawLayoutListViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawlayout_listview_item,null);
            drawLayoutListViewHolder.title = (TextView)convertView.findViewById(R.id.dl_lv_title);
            drawLayoutListViewHolder.icon = (ImageView) convertView.findViewById(R.id.dl_lv_icon);
            convertView.setTag(drawLayoutListViewHolder);
        }else{
            drawLayoutListViewHolder = (DrawLayoutListViewHolder) convertView.getTag();
        }
        drawLayoutListViewHolder.title.setText(list.get(position).get("drawlayout_list_title").toString());
        drawLayoutListViewHolder.icon.setImageResource((Integer)list.get(position).get("drawlayout_list_icon"));
        return convertView;
    }
}
