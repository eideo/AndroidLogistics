package com.kenny.logistics.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.kenny.logistics.model.response.JsonBean;
import com.kenny.logistics.model.response.user.UserToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 使用Retrofit对网络请求进行配置
 */
public class ApiRetrofit{

    public UserApi userApi;
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
                .baseUrl(UserApi.BASE_URL)
                //.client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UserApi.class);
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


}
