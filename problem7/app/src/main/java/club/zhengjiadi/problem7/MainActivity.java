package club.zhengjiadi.problem7;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    TextView textView;
    List<LogMessage> logMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startService = (Button) findViewById(R.id.start);
        Button stopService = (Button) findViewById(R.id.stop);
        Button quertData = (Button) findViewById(R.id.query_data);
        textView = findViewById(R.id.query_result);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        quertData.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
//                Intent startIntent = new Intent(this, ZjdService.class);
                Intent startIntent = new Intent(this, ZjdService.class);
                startService(startIntent);
                break;
            case R.id.stop:
//                Intent stopIntent = new Intent(this, ZjdService.class);
                Intent stopIntent = new Intent(this, ZjdService.class);
                stopService(stopIntent);
                break;
            case R.id.query_data:
//                Intent stopIntent = new Intent(this, ZjdService.class);
                queryData();
                StringBuilder result = new StringBuilder();
                for (LogMessage logMessage : logMessageList) {
                    result.append(logMessage).append("\n");
                }
                textView.setText(result.toString());
                break;
            default:
                break;
        }
    }

    public void queryData() {
        logMessageList = DataSupport.findAll(LogMessage.class);
        Log.d(TAG, String.valueOf(logMessageList));
    }

}
