package com.huangyaling.musicplayer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangyaling.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by huangyaling on 2017/5/26.
 */
public class ViewPagerAdapter extends PagerAdapter {
    ArrayList<View> viewsContainer = new ArrayList<View>();
    ArrayList<String> titleContainer = new ArrayList<String>();
    public ViewPagerAdapter(Context context){

        View view1 = LayoutInflater.from(context).inflate(R.layout.viewpager01,null);
        View view2 = LayoutInflater.from(context).inflate(R.layout.viewpager02,null);
        View view3 = LayoutInflater.from(context).inflate(R.layout.viewpager03,null);

        viewsContainer.add(view1);
        viewsContainer.add(view2);
        viewsContainer.add(view3);

        titleContainer.add(context.getResources().getText(R.string.viewpage_recommand).toString());
        titleContainer.add(context.getResources().getText(R.string.viewpager_music).toString());
        titleContainer.add(context.getResources().getText(R.string.viewpager_top).toString());
    }
    @Override
    public int getCount() {
        return viewsContainer.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(viewsContainer.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(viewsContainer.get(position));
        return viewsContainer.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleContainer.get(position);
    }
}
