package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WIN 8 on 17/08/2017.
 */

public class DataContact {
    @SerializedName("users")
    private User[] user;

    public User[] getUser() {
        return user;
    }

    public void setUser(User[] user) {
        this.user = user;
    }
}
