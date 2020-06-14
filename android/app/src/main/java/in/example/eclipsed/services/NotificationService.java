package in.example.eclipsed.services;

import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.R;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("NotificationService", "onMessageReceived Called");
        Log.d("NotificationService", remoteMessage.getNotification().getTitle());
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title, String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"MessageReceived")
                .setContentTitle(title)
                .setSmallIcon(androidx.media.R.drawable.notification_bg_low_pressed)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(body);
        NotificationManagerCompat manger = NotificationManagerCompat.from(getApplicationContext());
        manger.notify(999,builder.build());
    }

}
