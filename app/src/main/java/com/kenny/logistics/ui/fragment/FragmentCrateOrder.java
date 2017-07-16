package com.kenny.logistics.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.kenny.logistics.R;
import com.kenny.logistics.ui.base.BaseFragment;
import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.BindView;
import butterknife.OnClick;

public class FragmentCrateOrder extends BaseFragment {
    @BindView(R.id.send_name)
    EditText send_name;
    @BindView(R.id.send_phone)
    EditText send_phone;
    @BindView(R.id.send_addr)
    EditText send_addr;
    @BindView(R.id.send_addr_info)
    EditText send_addr_info;
    @BindView(R.id.recive_name)
    EditText recive_name;
    @BindView(R.id.recive_phone)
    EditText recive_phone;
    @BindView(R.id.recive_addr)
    EditText recive_addr;
    @BindView(R.id.recive_addr_info)
    EditText recive_addr_info;
    @BindView(R.id.recive_time)
    EditText recive_time;
    @BindView(R.id.send_time)
    EditText send_time;

    CityPicker cityPicker;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_create_order;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitCityPicker();
    }

    private void InitCityPicker(){
        cityPicker = new CityPicker.Builder(getContext())
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#ffffff")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("安徽省")
                .city("合肥市")
                .district("包河区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                send_name.setText(province+"/"+city+"/"+district);
            }
            @Override
            public void onCancel() {
            }
        });

    }

    @OnClick(R.id.send_time)
    void onSendTimeClick(){
        cityPicker.show();
    }
}
	
