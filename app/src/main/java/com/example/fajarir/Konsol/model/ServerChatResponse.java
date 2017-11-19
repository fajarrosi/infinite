package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WIN 8 on 24/08/2017.
 */

public class ServerChatResponse {
    @SerializedName("results")
    Results results;
    @SerializedName("status")
    private String status;

    public Results getResults() {return results;}

    public void setResults(Results results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
