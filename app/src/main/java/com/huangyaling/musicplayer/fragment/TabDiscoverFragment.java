package com.huangyaling.musicplayer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangyaling.musicplayer.R;

/**
 * Created by huangyaling on 2017/5/24.
 */
public class TabDiscoverFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_discover, container, false);
    }
}
