package com.dreamfactory.recorder.util;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.dreamfactory.recorder.App;
import com.dreamfactory.recorder.R;

public class NotificationUtil {

    public static void showNotification(Context context, int notificationId, String title, String message, Intent notificationIntent) {
        final PendingIntent pendingIntent = PendingIntent.getActivity(App.getContext(), 0, notificationIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.ic_appicon))
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_SOUND)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSmallIcon(R.mipmap.ic_appicon)
                .setContentIntent(pendingIntent);
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public static void cancelAllNotifications() {
        final NotificationManager notificationManager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
