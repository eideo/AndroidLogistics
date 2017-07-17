package com.kenny.logistics.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.kenny.logistics.model.response.JsonBean;
import com.kenny.logistics.model.response.PageResponse;
import com.kenny.logistics.model.response.order.OrderCustomer;
import com.kenny.logistics.model.response.order.OrderSet;
import com.kenny.logistics.model.response.user.UserToken;

import java.lang.reflect.Type;
import java.util.Date;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 使用Retrofit对网络请求进行配置
 */
public class ApiRetrofit{

    public ServiceApi userApi;
    public static ApiRetrofit mInstance;

    private ApiRetrofit() {
        super();
        Gson gson = new GsonBuilder()
                //Gson转换Long 到 Date 适配器
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                })
                .setLenient()
                .create();


        //在构造方法中完成对Retrofit接口的初始化
        userApi = new Retrofit.Builder()
                .baseUrl(ServiceApi.BASE_URL)
                //.client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ServiceApi.class);
    }

    public static ApiRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (ApiRetrofit.class) {
                if (mInstance == null) {
                    mInstance = new ApiRetrofit();
                }
            }
        }
        return mInstance;
    }

    //登录
    public Observable<JsonBean<UserToken>> login(String phone, String password) {
        return userApi.login_phone(phone,password);
    }

    public Observable<JsonBean<OrderCustomer>> insert_customer(String token,
                                                               String send_name,
                                                               String send_phone,
                                                               String send_addr,
                                                               String send_addr_info,
                                                               String recive_name,
                                                               String recive_phone,
                                                               String recive_addr,
                                                               String recive_addr_info,
                                                               String send_time,
                                                               String recive_time,
                                                               String dispatching_type){
        return userApi.insert_customer(token,send_name,send_phone,send_addr,send_addr_info,recive_name,recive_phone,recive_addr,recive_addr_info,send_time,recive_time,dispatching_type);
    }

    public Observable<JsonBean<PageResponse<OrderSet>>> getOrderSet(){
        return userApi.getOrderSet();
    }
}
