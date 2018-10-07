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
    Button mNotifyButtonOne;
    Button mNotifyButtonTwo;
    int numberOfMessages=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotifyButtonOne = findViewById(R.id.notify_button_one);
        mNotifyButtonTwo = findViewById(R.id.notify_button_two);
        mNotifyButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushNotification();
            }
        });


        mNotifyButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();
            }
        });

    }
    //Type 1: Normal View Style.
    private void pushNotification() {
        //STEP 1: Create Notification Builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, "ChannelId_01");
        //STEP 2: Setting Notification Properties
        mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        mBuilder.setContentTitle("Push Notification");
        mBuilder.setContentText("Welcome Back Dina");
        mBuilder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mCtx.getPackageName() + "/" + R.raw.sound));
        mBuilder.setAutoCancel(true);
        //STEP 3: Attach Actions
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        Intent newIntent = new Intent(this, ResultIntent.class);
        stackBuilder.addNextIntent(newIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
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
    //Types 2: Big View Style.
    private void displayNotification() {
        //STEP 1: Create Notification Builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "channelId-02")
                //STEP 2: Setting Notification Properties
                .setContentTitle("Push Notifications")
                .setContentText("You have received a new message.")
                .setTicker("New Message Alert!")
                .setAutoCancel(true)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+getPackageName()+"/"+R.raw.sound))
                .setSmallIcon(R.drawable.ic_launcher_background)
        /* Increase notification number every time a new notification arrives */
        .setNumber(++numberOfMessages);

        /* Add Big View Specific Configuration */
        NotificationCompat.InboxStyle mInboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        events[0] = new String("This is first line....");
        events[1] = new String("This is second line...");
        events[2] = new String("This is third line...");
        events[3] = new String("This is 4th line...");
        events[4] = new String("This is 5th line...");
        events[5] = new String("This is 6th line...");

        // Sets a title for the Inbox style big view
        mInboxStyle.setBigContentTitle("Big Title Details:");
        for(int i=0;i<events.length;i++){
            mInboxStyle.addLine(events[i]);


        }
        mBuilder.setStyle(mInboxStyle);


        /* Creates an explicit intent for an Activity in your app */
        Intent intent = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(intent);
        PendingIntent resultIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0,mBuilder.build());
    }
}
