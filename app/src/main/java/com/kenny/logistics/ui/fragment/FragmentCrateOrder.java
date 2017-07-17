package com.kenny.logistics.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kenny.logistics.R;
import com.kenny.logistics.api.ApiRetrofit;
import com.kenny.logistics.model.response.JsonBean;
import com.kenny.logistics.model.response.order.OrderCustomer;
import com.kenny.logistics.ui.base.BaseFragment;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentCrateOrder extends BaseFragment {
    @BindView(R.id.send_name)
    EditText send_name;
    @BindView(R.id.send_phone)
    EditText send_phone;
    @BindView(R.id.send_addr)
    TextView send_addr;
    @BindView(R.id.send_addr_info)
    EditText send_addr_info;
    @BindView(R.id.recive_name)
    EditText recive_name;
    @BindView(R.id.recive_phone)
    EditText recive_phone;
    @BindView(R.id.recive_addr)
    TextView recive_addr;
    @BindView(R.id.recive_addr_info)
    EditText recive_addr_info;
    @BindView(R.id.recive_time)
    TextView recive_time;
    @BindView(R.id.send_time)
    TextView send_time;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    View view;

    CityPicker cityPicker;
    Context context;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_create_order;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitCityPicker();
        this.view = view;
        context = getContext();
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
                if(type==0)
                    send_addr.setText(province+"/"+city+"/"+district);
                else
                    recive_addr.setText(province+"/"+city+"/"+district);

            }
            @Override
            public void onCancel() {
            }
        });

    }

    int type = 0;
    @OnClick(R.id.send_addr)
    void onSendAddrClick(){
        type = 0;
        cityPicker.show();
    }


    @OnClick(R.id.recive_addr)
    void onReciveAddrClick(){
        type = 1;
        cityPicker.show();
    }

    @OnClick(R.id.send_time)
    void onSendTimeClick(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        send_time.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.recive_time)
    void onReciveTimeClick(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        recive_time.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.button)
    void onSubmit(){
        SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", 0);
        String token = userinfo.getString("token","");
        progressBar.setVisibility(View.VISIBLE);
        ApiRetrofit.getInstance().insert_customer(
                token,
                send_name.getText().toString(),
                send_phone.getText().toString(),
                send_addr.getText().toString(),
                send_addr_info.getText().toString(),
                recive_name.getText().toString(),
                recive_phone.getText().toString(),
                recive_addr.getText().toString(),
                recive_addr_info.getText().toString(),
                send_time.getText().toString(),
                recive_time.getText().toString(),
                "dispatching")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonBean<OrderCustomer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull JsonBean<OrderCustomer> orderCustomerJsonBean) {
                        progressBar.setVisibility(View.GONE);
                        if(orderCustomerJsonBean.getError_code() == 0){

                            send_name.setText("");
                            send_phone.setText("");
                            send_addr.setText("");
                            send_addr_info.setText("");
                            recive_name.setText("");
                            recive_phone.setText("");
                            recive_addr.setText("");
                            recive_addr_info.setText("");
                            send_time.setText("");
                            recive_time.setText("");


                            new MaterialDialog.Builder(context)
                                    .title("提示")
                                    .content("下单成功！")
                                    .positiveText("确定")
                                    .show();

                            FragmentOrder.instance.Init();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                        showSnackar(view, "获取失败：" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
	
