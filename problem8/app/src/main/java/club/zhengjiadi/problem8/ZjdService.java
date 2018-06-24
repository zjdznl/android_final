package club.zhengjiadi.problem8;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;
import java.util.Timer;


public class ZjdService extends Service {
    String TAG = "ZjdService";
    //显示 Toast
    Handler handler = new Handler(Looper.getMainLooper());
    private static Timer timer;

    public ZjdService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        Connector.getDatabase();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        timer = new Timer();
        timer.scheduleAtFixedRate(new LogMessageTask(), 0, 2000);
        return super.onStartCommand(intent, flags, startId);
    }

    class LogMessageTask extends java.util.TimerTask {
        @Override
        public void run() {
            try {
                insertToSQLite();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertToSQLite() {
//        show("insert success!");
        addData();
    }


    public void addData() {
        Log.d(TAG, "addData() called");
        LogMessage logMessage = new LogMessage();
        logMessage.setCreateTime(new Date());
        logMessage.setLog("Log at time : " + new Date());
        logMessage.save();
        show("success add a data!");
    }

    public void deleteData() {
        Log.d(TAG, "deleteData() called");
        DataSupport.deleteAll(LogMessage.class);
    }

    public void queryData() {
        Log.d(TAG, "queryData() called");
        List<LogMessage> logMessageList = DataSupport.findAll(LogMessage.class);
        Log.d(TAG, String.valueOf(logMessageList));
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        show("stop!");
        timer.cancel();
        super.onDestroy();
    }

    public void show(final String toastString) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ZjdService.this.getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
