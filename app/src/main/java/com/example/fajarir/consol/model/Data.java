package com.example.fajarir.consol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarir on 8/8/2017.
 */

public class Data {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public Data(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
