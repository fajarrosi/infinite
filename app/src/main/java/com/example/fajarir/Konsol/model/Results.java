package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.SerializedName;



/**
 * Created by WIN 8 on 24/08/2017.
 */

public class Results {

    @SerializedName("rooms_info")
    private RoomsInfo[] roomsInfo ;


    public RoomsInfo[] getRoomsInfo() {
        return roomsInfo;
    }

    public void setRoomsInfo(RoomsInfo[] roomsInfo) {
        this.roomsInfo = roomsInfo;
    }
}
