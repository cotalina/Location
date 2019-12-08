package com.dongdaozhu.test;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.dongdaozhu.test.Service.MyService;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.xdandroid.hellodaemon.DaemonEnv;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .addNetworkInterceptor(new Interceptor() {//自定义拦截器
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request  = chain.request();
                        //配置统一的头
                        Request  newRequest     = request.newBuilder().addHeader("Accept-Charset", "utf-8").build();
                        return  chain.proceed(newRequest);
                    }
                })
                .addNetworkInterceptor( new StethoInterceptor())//增加Stetho拦截器,用于调试
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        //使用自定义OkHttpClient
        OkHttpUtils.initClient(okHttpClient);


        DaemonEnv.initialize(getApplicationContext(),MyService.class,5);
       // getApplicationContext().startService(new Intent(getApplicationContext(), MyService.class));

    }
}
