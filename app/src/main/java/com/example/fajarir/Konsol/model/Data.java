package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarir on 8/8/2017.
 */

public class Data {
    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
