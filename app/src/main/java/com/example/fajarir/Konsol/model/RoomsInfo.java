package com.example.fajarir.Konsol.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WIN 8 on 24/08/2017.
 */

public class RoomsInfo {
    @SerializedName("last_comment_id")
    private Integer lastCommentId;
    @SerializedName("last_comment_message")
    private String lastCommentMessage;
    @SerializedName("last_comment_timestamp")
    private String lastCommentTimestamp;
    @SerializedName("room_avatar_url")
    private String roomAvatarUrl;
    @SerializedName("room_id")
    private Integer roomId;
    @SerializedName("room_name")
    private String roomName;
    @SerializedName("room_type")
    private String roomType;
    @SerializedName("unread_count")
    private Integer unreadCount;

    public Integer getLastCommentId() {
        return lastCommentId;
    }

    public void setLastCommentId(Integer lastCommentId) {
        this.lastCommentId = lastCommentId;
    }

    public String getLastCommentMessage() {
        return lastCommentMessage;
    }

    public void setLastCommentMessage(String lastCommentMessage) {
        this.lastCommentMessage = lastCommentMessage;
    }

    public String getLastCommentTimestamp() {
        return lastCommentTimestamp;
    }

    public void setLastCommentTimestamp(String lastCommentTimestamp) {
        this.lastCommentTimestamp = lastCommentTimestamp;
    }

    public String getRoomAvatarUrl() {
        return roomAvatarUrl;
    }

    public void setRoomAvatarUrl(String roomAvatarUrl) {
        this.roomAvatarUrl = roomAvatarUrl;
    }

    public Integer
    getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }
}
