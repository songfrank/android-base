package com.being.base.http.retrofit;

import android.util.Log;

import com.being.base.Constant;
import com.being.base.http.retrofit.calladapter.CompactCallAdapterFactory;
import com.being.base.http.retrofit.calladapter.RxThreadCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangpeng on 17/2/20.
 */
public class RetrofitManager {

    private static RetrofitManager mInstance;
    private Retrofit.Builder mBuilder;

    public static RetrofitManager get() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    private Retrofit mRetrofit;

    private OkHttpClient mOkHttpClient;

    private RetrofitManager() {
        mBuilder = new Retrofit.Builder();
    }

    /**
     * 初始化方法必须在各种设置完成后最后调用
     * @param baseUrl
     */
    public void initRetrofit(String baseUrl, OkHttpClient client) {
        mBuilder.baseUrl(baseUrl);
        mBuilder.addConverterFactory(GsonConverterFactory.create());
        mBuilder.addCallAdapterFactory(new CompactCallAdapterFactory());
        mBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.createAsync());
        initRetrofit(mBuilder, client);
    }

    /**
     * 初始化方法必须在各种设置完成后最后调用
     * @param baseUrl
     */
    public void initRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        mRetrofit = builder.client(client).build();
    }

    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}