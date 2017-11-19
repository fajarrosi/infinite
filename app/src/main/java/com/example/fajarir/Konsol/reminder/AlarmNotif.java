package com.example.fajarir.Konsol.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.fajarir.Konsol.R;
import com.example.fajarir.Konsol.data.DataManager;
import com.example.fajarir.Konsol.mahasiswa.MahasiswaActivity;

/**
 * Created by WIN 8 on 28/08/2017.
 */

public class AlarmNotif extends BroadcastReceiver {


    private DataManager dataManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        dataManager = new DataManager(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent intent2 = new Intent(context,MahasiswaActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0,intent2,0);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.warning)
                .setContentTitle(dataManager.getTitles())
                .setContentIntent(pIntent)
                .setContentText(dataManager.getNotes())
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

    }
}
