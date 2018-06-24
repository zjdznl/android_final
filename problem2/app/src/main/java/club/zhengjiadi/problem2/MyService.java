package club.zhengjiadi.problem2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.util.Timer;

import static org.litepal.LitePalApplication.getContext;

public class MyService extends Service {
    /**
     * this is whj implement, not work in this project
     */

    Timer timer = new Timer();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getContext(), "start", Toast.LENGTH_SHORT).show();
        timer = new Timer();
        timer.schedule(new MyTask(), 0, 2000);//在0秒后执行此任务,每次间隔2秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getContext(), "stop", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        timer.cancel();
    }

    private void parseJSONWithJSONObjectBack(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("userId");
                if (id.equals("10152510184")) {
                    Looper.prepare();
                    Toast.makeText(getContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int returnCode = Integer.parseInt(jsonObject.getString("returnCode"));
                String returnValue = jsonObject.getString("returnValue");
//                String returnMsg = jsonObject.getString("returnMsg");
                String returnMsg = "fuck";

                if (returnCode >= 0) {
                    Looper.prepare();
                    Toast.makeText(getContext(), returnValue, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyTask extends java.util.TimerTask {
        @Override
        public void run() {
            try {
                String URL = "http://115.29.231.93:8080/CkeditorTest/AndroidTest?userId=10152510143&style=json";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(URL).build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
