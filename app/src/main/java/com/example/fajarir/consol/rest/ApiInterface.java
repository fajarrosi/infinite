package com.example.fajarir.consol.rest;

import com.example.fajarir.consol.model.Data;
import com.example.fajarir.consol.model.User;
import com.example.fajarir.consol.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by fajarir on 8/8/2017.
 */

public interface ApiInterface {
   @POST("users/session")
    Call<User>login(@Body Data data);

    @GET("users/session")
    Call<UserResponse>getSecret(@Header("Authorization")String AuthToken);
}
