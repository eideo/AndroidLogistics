package com.kenny.logistics.api;

import com.kenny.logistics.model.response.JsonBean;
import com.kenny.logistics.model.response.user.UserToken;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kenny on 2017/7/10.
 */

//创建RxJava上游 Observable
public interface UserApi {
    public static final String BASE_URL = "http://192.168.3.115/v1/user/api/";

    //手机号密码登录
    @GET("login_phone")
    Observable<JsonBean<UserToken>> login_phone(@Query("phone") String phone, @Query("password") String password);
}
