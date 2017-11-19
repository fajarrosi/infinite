package com.example.fajarir.Konsol.rest;

import com.qiscus.sdk.Qiscus;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WIN 8 on 24/08/2017.
 */

public class QClient {
    public static final String BASE_URL = Qiscus.getAppServer();
    private static Retrofit retrofit = null;


    public static Retrofit getClient(){
        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
