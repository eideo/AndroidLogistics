package com.kenny.logistics.api;

import com.kenny.logistics.model.response.JsonBean;
import com.kenny.logistics.model.response.PageResponse;
import com.kenny.logistics.model.response.order.OrderCustomer;
import com.kenny.logistics.model.response.order.OrderSet;
import com.kenny.logistics.model.response.user.UserToken;

import java.util.Date;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Kenny on 2017/7/10.
 */

//创建RxJava上游 Observable
public interface ServiceApi {
    public static final String BASE_URL = "http://192.168.3.21";

    //手机号密码登录
    @GET("/v1/user/customer/login_phone")
    Observable<JsonBean<UserToken>> login_phone(@Query("phone") String phone, @Query("password") String password);


    @POST("/v1/order/customer")
    Observable<JsonBean<OrderCustomer>> insert_customer(@Query("token") String token,
                                                        @Query("send_name") String send_name,
                                                        @Query("send_phone") String send_phone,
                                                        @Query("send_addr") String send_addr,
                                                        @Query("send_addr_info") String send_addr_info,
                                                        @Query("recive_name") String recive_name,
                                                        @Query("recive_phone") String recive_phone,
                                                        @Query("recive_addr") String recive_addr,
                                                        @Query("recive_addr_info") String recive_addr_info,
                                                        @Query("send_time") String send_time,
                                                        @Query("recive_time") String recive_time,
                                                        @Query("dispatching_type") String dispatching_type);

    @GET("/v1/order/page")
    Observable<JsonBean<PageResponse<OrderSet>>> getOrderSet();
}
