package com.kenny.logistics.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kenny.logistics.R;
import com.kenny.logistics.api.ApiRetrofit;
import com.kenny.logistics.model.response.JsonBean;
import com.kenny.logistics.model.response.user.UserToken;
import com.kenny.logistics.ui.activity.HomeActivity;
import com.kenny.logistics.ui.base.BaseFragment;
import com.kenny.logistics.ui.component.EditTextWithDel;
import com.kenny.logistics.ui.component.PaperButton;
import com.kenny.logistics.util.CheckUtils;
import com.kenny.logistics.util.Tools;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class FragmentLogin extends BaseFragment {
    @BindView(R.id.userph)
    EditTextWithDel userphone;
    @BindView(R.id.userpass)
    EditTextWithDel userpass;
    @BindView(R.id.bt_login)
    PaperButton bt_login;
    @BindView(R.id.login_progress)
    ProgressBar login_progress;
    @BindView(R.id.tv_forgetcode)
    TextView tv_forgetcode;
    @BindView(R.id.loginusericon)
    ImageView loginusericon;
    @BindView(R.id.codeicon)
    ImageView codeicon;
    @BindView(R.id.rela_name)
    RelativeLayout rela_name;
    @BindView(R.id.rela_pass)
    RelativeLayout rela_pass;
    private Handler handler = new Handler() {
    };

    @Override
    public int getContentViewId() {
        return R.layout.fragment_login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLogin();
    }

    private void initLogin() {
        SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", 0);
        userphone.setText(userinfo.getString("username", "18949821385"));
        userpass.setText(userinfo.getString("password", "4811298"));
    }

    @OnClick(R.id.bt_login)
    void onClick(View v) {
        final String phone = userphone.getText().toString();
        final String passwords = userpass.getText().toString();
        final View view = v;

        if (TextUtils.isEmpty(phone)) {
            rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
            loginusericon.setAnimation(Tools.shakeAnimation(2));
            showSnackar(v, "提示：请输入手机号码");
            return;
        }
        if (!CheckUtils.isMobile(phone)) {
            //抖动
            rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
            loginusericon.setAnimation(Tools.shakeAnimation(2));
            showSnackar(v, "提示：手机号不正确");
            return;
        }
        if (TextUtils.isEmpty(passwords)) {
            rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
            codeicon.setAnimation(Tools.shakeAnimation(2));
            showSnackar(v, "提示：请输入密码");
            return;
        }
        login_progress.setVisibility(View.VISIBLE);
        ApiRetrofit.getInstance().login(phone, passwords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonBean<UserToken>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull JsonBean<UserToken> userTokenJsonBean) {
                        int code = userTokenJsonBean.getError_code();
                        login_progress.setVisibility(View.GONE);
                        if (code == 0) {
                            rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));
                            rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));
                            showSnackar(view, "提示：登陆成功");
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    login_progress.setVisibility(View.GONE);
                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.fade,R.anim.my_alpha_action);
                                }
                            }, 500);
                        } else {
                            rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                            codeicon.setAnimation(Tools.shakeAnimation(2));
                            showSnackar(view, "登陆失败：" + userTokenJsonBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        login_progress.setVisibility(View.GONE);
                        e.printStackTrace();
                        showSnackar(view, "登陆失败：网络错误");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }
}

