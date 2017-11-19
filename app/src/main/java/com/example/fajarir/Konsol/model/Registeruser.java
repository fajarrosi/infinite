package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WIN 8 on 25/08/2017.
 */

public class Registeruser {
    @SerializedName("user")
    private RegisterRequest user;

    public RegisterRequest getUser() {
        return user;
    }

    public void setUser(RegisterRequest user) {
        this.user = user;
    }
}
