package com.kenny.logistics.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;
import com.kenny.logistics.R;
import com.kenny.logistics.ui.base.BaseFragment;

public class FragmentMine extends BaseFragment {

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StatusBarUtil.setColor(getActivity(), Color.RED);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}
	
