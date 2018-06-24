package club.zhengjiadi.problem6_switch5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String data = bundle.getString("value");
        textView.setText("the value is: " + data);

//        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "data");
    }
}
