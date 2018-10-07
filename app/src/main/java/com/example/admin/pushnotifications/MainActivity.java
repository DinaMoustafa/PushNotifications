package com.example.admin.pushnotifications;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    Context mCtx = this;
    Button mNotifyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotifyButton = findViewById(R.id.notify_button);
        mNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pushNotification();

            }
        });


    }


    private void pushNotification(){

        //STEP 1: Create Notification Builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, "ChannelId_01");
        //STEP 2: Setting Notification Properties
        mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        mBuilder.setContentTitle("Push Notification");
        mBuilder.setContentText("Welcome Back Dina");
        mBuilder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mCtx.getPackageName() + "/" + R.raw.sound));
        mBuilder.setAutoCancel(true);
        //STEP 3: Attach Actions
        Intent intent= new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        Intent newIntent = new Intent(this,ResultIntent.class);
        stackBuilder.addNextIntent(newIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        //STEP 4: Issue the notification
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Making Screen LIGHT UP when receiving notifications.
        mNotificationManager.notify(0, mBuilder.build());
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "Tag");
        wakeLock.acquire();
        wakeLock.release();



    }

}
