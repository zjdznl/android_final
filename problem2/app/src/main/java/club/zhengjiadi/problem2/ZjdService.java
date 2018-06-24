package club.zhengjiadi.problem2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Timer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        timer =  new Timer();
        timer.scheduleAtFixedRate(new GetMessageTask(), 0, 2000);
        return super.onStartCommand(intent, flags, startId);
    }

    class GetMessageTask extends java.util.TimerTask {
        @Override
        public void run() {
            try {
                getMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getMessage() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String URL = "http://115.29.231.93:8080/CkeditorTest/AndroidTest?userId=10152510143&style=json";
        final Request request = new Request.Builder()
                .url(URL)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "network error ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                parseJSONWithGSON(response.body().string());
                parseJSONWithGSON(response.body().string());
            }

        });
    }

    public void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        Message message = gson.fromJson(jsonData, Message.class);

        int returnCode = message.getReturnCode();
        String returnValue = message.getReturnValue();

        Log.d(TAG, String.valueOf(returnCode));
        Log.d(TAG, returnValue);

        if (returnCode < 0) {
            show("error");
        } else if (returnCode == 0) {
            show(returnValue);
        } else {
            show("10152510143" + "bbb");
        }

    }

    public void parseXML(String xmlData)  {

//        xmlData = "<message>\n" +
//                "    <returnCode>0</returnCode>\n" +
//                "    <returnValue>return value</returnValue>\n" +
//                "    <returnMsg>return message</returnMsg>\n" +
//                "</message>";
        int  returnCode = 0;
        String returnValue = "";
        String returnMsg = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));
            int eventType = parser.getEventType();


            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                    // 开始解析某个结点
                    case XmlPullParser.START_TAG: {
                        if ("returnCode".equals(nodeName)) {
                            returnCode = Integer.parseInt(parser.nextText());
                        } else if ("returnValue".equals(nodeName)) {
                            returnValue = parser.nextText();
                        } else if ("returnMsg".equals(nodeName)) {
                            returnMsg = parser.nextText();
                        }
                        break;
                    }
                    // 完成解析某个结点
                    case XmlPullParser.END_TAG: {
                        if ("message".equals(nodeName)) {
                            Log.d(TAG, "returnCode is " + returnCode);
                            Log.d(TAG, "returnValue is " + returnValue);
                            Log.d(TAG, "returnMsg is " + returnMsg);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        Log.d(TAG, String.valueOf(returnCode));
        Log.d(TAG, returnValue);

        if (returnCode < 0) {
            show("error");
        } else if (returnCode == 0) {
            show(returnValue);
        } else {
            show("10152510143" + "bbb");
        }

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
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
