package club.zhengjiadi.problem2;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startService = (Button) findViewById(R.id.start);
        Button stopService = (Button) findViewById(R.id.stop);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.start:
//                Intent startIntent = new Intent(this, MyService.class);
                Intent startIntent = new Intent(this, ZjdService.class);
                startService(startIntent);
                break;
            case R.id.stop:
//                Intent stopIntent = new Intent(this, MyService.class);
                Intent stopIntent = new Intent(this, ZjdService.class);
                stopService(stopIntent);
                break;
            default:break;
        }
    }

}
