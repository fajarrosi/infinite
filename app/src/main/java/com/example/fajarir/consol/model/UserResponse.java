package com.example.fajarir.consol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarir on 8/8/2017.
 */

public class UserResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Data data;

    public UserResponse(String status, Data data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
