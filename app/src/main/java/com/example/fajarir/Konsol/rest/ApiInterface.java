package com.example.fajarir.Konsol.rest;

import com.example.fajarir.Konsol.model.Registeruser;
import com.example.fajarir.Konsol.model.ServerChatResponse;
import com.example.fajarir.Konsol.model.ServerContactResponse;
import com.example.fajarir.Konsol.model.ServerRequest;
import com.example.fajarir.Konsol.model.ServerResponse;
import com.example.fajarir.Konsol.model.UpdateProfileRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by fajarir on 8/8/2017.
 */

public interface ApiInterface {

    @POST("users/session")
    Call<ServerResponse> login(@Body ServerRequest request);

    @POST("users")
    Call<ServerResponse> register(@Body Registeruser request);

    @GET("users")
    Call<ServerContactResponse> getDataUser(@Header("Authorization") String authorization);

    @PATCH("users/profile")
    Call<ServerResponse> updateProfile(@Header("Authorization") String authorization,
                                       @Body UpdateProfileRequest updateProfileRequest);

    @GET("/api/v2/mobile/get_user_rooms")
    Call<ServerChatResponse> getListChat(@Query("token") String accessToken);
}
