package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by X550Z FX on 29/08/2017.
 */

public class UpdateProfileRequest {
    @SerializedName("user")
    @Expose
    private UpdateProfile user;

    public UpdateProfile getUser() {
        return user;
    }

    public void setUser(UpdateProfile user) {
        this.user = user;
    }
}
