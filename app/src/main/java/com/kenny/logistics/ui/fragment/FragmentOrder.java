package com.kenny.logistics.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jaeger.library.StatusBarUtil;
import com.kenny.logistics.R;
import com.kenny.logistics.ui.base.BaseFragment;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentOrder extends BaseFragment {
    @BindView(R.id.list_view)
    ListView listView;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_order;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> data = new ArrayList<String>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");

        listView.setAdapter(new CommonAdapter<String>(getContext(), R.layout.item_order, data)
        {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        });
    }
}
	
