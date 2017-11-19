package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WIN 8 on 17/08/2017.
 */

public class ServerContactResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private DataContact data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataContact getData() {
        return data;
    }

    public void setData(DataContact data) {
        this.data = data;
    }
}
