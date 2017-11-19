package com.example.fajarir.Konsol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusRoomMember;
import com.qiscus.sdk.data.remote.QiscusGlide;
import com.qiscus.sdk.ui.QiscusBaseChatActivity;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.fragment.QiscusChatFragment;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;
import com.qiscus.sdk.util.QiscusDateUtil;

import java.util.Date;

/**
 * Created by WIN 8 on 27/08/2017.
 */

public class Custom_chat extends QiscusBaseChatActivity {
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvSubtitle;
    private QiscusCircularImageView ivAvatar;
    private String roomName;
    private static final String EXTRA_NAME = "extra_name";

    private QiscusAccount qiscusAccount;

    public static Intent generateIntent(Context context, QiscusChatRoom qiscusChatRoom, String RoomName) {
        Intent intent = new Intent(context, Custom_chat.class);
        intent.putExtra(CHAT_ROOM_DATA, qiscusChatRoom);
        intent.putExtra(EXTRA_NAME, RoomName);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.custom_chat;
    }

    @Override
    protected void onLoadView() {
        roomName = getIntent().getStringExtra(EXTRA_NAME);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSubtitle = (TextView) findViewById(R.id.tv_subtitle);
        chatConfig.setNotificationSmallIcon(R.drawable.notif_icon);
        chatConfig.setNotificationBigIcon(R.drawable.notif_icon);
        ivAvatar = (QiscusCircularImageView) findViewById(R.id.profile_picture);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);

    }

    @Override
    protected QiscusBaseChatFragment onCreateChatFragment() {
        return QiscusChatFragment.newInstance(qiscusChatRoom, startingMessage, shareFile, autoSendExtra, forwardComments);
    }

    @Override
    public void onUserStatusChanged(String user, boolean online, Date lastActive) {
        if (qiscusChatRoom.getSubtitle().isEmpty()) {
            String last = QiscusDateUtil.getRelativeTimeDiff(lastActive);
            tvSubtitle.setText(online ? "Online" : "Last seen " + last);
            tvSubtitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        if (qiscusChatRoom.getSubtitle().isEmpty()) {
            tvSubtitle.setText(typing ? "Typing..." : "Online");
            tvSubtitle.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        qiscusAccount = Qiscus.getQiscusAccount();
        super.onViewReady(savedInstanceState);
    }


    @Override
    protected void applyChatConfig() {
        chatConfig.setNotificationBigIcon(R.drawable.notif_icon);
        chatConfig.setNotificationSmallIcon(R.drawable.notif_icon);
        tvSubtitle.setTextColor(ContextCompat.getColor(this, chatConfig.getSubtitleColor()));
    }

    @Override
    protected void binRoomData() {
        super.binRoomData();
        tvTitle.setText(roomName);
        if (!qiscusChatRoom.getSubtitle().isEmpty()) {
            tvSubtitle.setText(qiscusChatRoom.getSubtitle());
            tvSubtitle.setVisibility(qiscusChatRoom.getSubtitle().isEmpty() ? View.GONE : View.VISIBLE);
        }
        showRoomImage();
    }

    protected void showRoomImage() {
        for (QiscusRoomMember member : qiscusChatRoom.getMember()) {
            if (!member.getEmail().equalsIgnoreCase(qiscusAccount.getEmail())) {
                QiscusGlide.getInstance().get().load(R.mipmap.user_icon)
                        .error(com.qiscus.sdk.R.drawable.ic_qiscus_avatar)
                        .placeholder(com.qiscus.sdk.R.drawable.ic_qiscus_avatar)
                        .dontAnimate()
                        .into(ivAvatar);
                break;
            }
        }
    }
}
