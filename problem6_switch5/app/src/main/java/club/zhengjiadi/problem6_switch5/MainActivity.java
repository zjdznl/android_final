package club.zhengjiadi.problem6_switch5;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private IntentFilter intentFilter;

    private LocalReceiver localReceiver;

    private LocalBroadcastManager localBroadcastManager;

    NotificationManager manager;

    Notification notification;

    PendingIntent pi;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localBroadcastManager = LocalBroadcastManager.getInstance(this); // 获取实例

        String id = "my_channel_01";
        String name="channel name";
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        Bundle bundle=new Bundle();
        bundle.putString("value","100");
        intent.putExtras(bundle);


        pi = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
//            Toast.makeText(this, mChannel.toString(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, mChannel.toString());
            manager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(this)
                    .setChannelId(id)
                    .setContentTitle("Notice")
                    .setContentText("send local broadcast, click me to see message")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.notice)
                    .setContentIntent(pi)
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle("Notice")
                    .setContentText("send local broadcast, click me to see message")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.notice)
                    .setContentIntent(pi)
                    .setOngoing(true);
//                    .setChannel(id);//无效
            notification = notificationBuilder.build();
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "you click button");

                Intent intent = new Intent("club.zhengjiadi.problem6_switch5.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent); // 发送本地广播

                Log.d(TAG, "I has send!!!");
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("club.zhengjiadi.problem6_switch5.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();

        localBroadcastManager.registerReceiver(localReceiver, intentFilter); // 注册本地广播监听器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "fuck, receive broadcast");
            manager.notify(1, notification);
        }

    }
}
