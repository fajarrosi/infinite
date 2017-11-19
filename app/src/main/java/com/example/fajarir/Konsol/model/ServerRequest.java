package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WIN 8 on 16/08/2017.
 */

public class ServerRequest {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
