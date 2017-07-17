package com.kenny.logistics.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.jaeger.library.StatusBarUtil;
import com.kenny.logistics.R;
import com.kenny.logistics.api.ApiRetrofit;
import com.kenny.logistics.model.response.JsonBean;
import com.kenny.logistics.model.response.PageResponse;
import com.kenny.logistics.model.response.order.OrderCustomer;
import com.kenny.logistics.model.response.order.OrderSet;
import com.kenny.logistics.ui.base.BaseFragment;
import com.lljjcoder.citylist.Toast.ToastUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentOrder extends BaseFragment {
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    View view;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_order;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        Init();
    }

    public void Init(){
        progressBar.setVisibility(View.VISIBLE);
        ApiRetrofit.getInstance().getOrderSet()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonBean<PageResponse<OrderSet>>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull JsonBean<PageResponse<OrderSet>> pageResponseJsonBean) {

                        progressBar.setVisibility(View.GONE);
                        listView.setAdapter(new CommonAdapter<OrderSet>(getContext(), R.layout.item_order, pageResponseJsonBean.getData().getItem())
                        {
                            @Override
                            protected void convert(ViewHolder viewHolder, OrderSet item, int position) {
                                viewHolder.setText(R.id.tv_order_number,item.getOrderCustomer().getOrder_number());
                                viewHolder.setText(R.id.tv_send_addr,item.getOrderCustomer().getSend_addr());
                                viewHolder.setText(R.id.tv_recive_addr,item.getOrderCustomer().getRecive_addr());
                                viewHolder.setText(R.id.tv_recive_addr_info,"收货地："+item.getOrderCustomer().getRecive_addr_info());
                                viewHolder.setText(R.id.tv_recive_time,"限时送达："+item.getOrderCustomer().getRecive_time().getMonth()+"月"+item.getOrderCustomer().getRecive_time().getDay()+"日");

                                if(item.getCar()!=null) {
                                    viewHolder.setText(R.id.tv_car, "车辆信息：" + item.getCar().getPlate());
                                    viewHolder.setText(R.id.tv_driver, "司机信息：" + item.getDriver().getName());
                                }

                                if(item.getOrderCustomer().getStatus().equals("ORDER_PLACE"))
                                {
                                    viewHolder.setText(R.id.tv_order_status,"待处理");
                                }
                                else if(item.getOrderCustomer().getStatus().equals("ORDER_TAKING"))
                                {
                                    viewHolder.setText(R.id.tv_order_status,"已发货");
                                    viewHolder.setTextColor(R.id.tv_order_status,Color.parseColor("#2ecc71"));
                                    viewHolder.setBackgroundColor(R.id.ly,Color.parseColor("#2ecc71"));
                                }
                                else if(item.getOrderCustomer().getStatus().equals("ORDER_SIGN"))
                                {
                                    viewHolder.setText(R.id.tv_order_status,"已签收");
                                    viewHolder.setTextColor(R.id.tv_order_status,Color.parseColor("#e74c3c"));
                                    viewHolder.setBackgroundColor(R.id.ly,Color.parseColor("#e74c3c"));
                                }

                            }
                        });
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        showSnackar(view, "获取失败：" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
	
