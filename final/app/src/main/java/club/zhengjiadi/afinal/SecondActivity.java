package club.zhengjiadi.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView name;
    private TextView sexText;
    private TextView password;
    private Button signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);


        name = findViewById(R.id.name);
        sexText = findViewById(R.id.sex_text);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);

        Intent intent = getIntent();
        String nameS = intent.getStringExtra("name");
        String sexS = intent.getStringExtra("sex");
        String passwordS = intent.getStringExtra("password");

        name.setText(nameS);
        sexText.setText(sexS);
        password.setText(passwordS);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "返回上一个活动！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
