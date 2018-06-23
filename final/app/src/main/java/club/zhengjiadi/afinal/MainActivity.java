package club.zhengjiadi.afinal;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private EditText name;
    private Spinner sexSelect;
    private EditText password;
    private EditText ensurePassword;
    private CheckBox acceptSignup;
    private TextView acceptString;
    private Button signup;

    private String nameString;
    private String sexString;
    private String passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_info);

        name = findViewById(R.id.name);
        sexSelect = findViewById(R.id.sex_select);
        password = findViewById(R.id.password);
        ensurePassword = findViewById(R.id.ensure_password);
        acceptSignup = findViewById(R.id.accept_signup);
        acceptString = findViewById(R.id.accept_string);
        acceptString.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("name", nameString);
                    intent.putExtra("sex", sexString);
                    intent.putExtra("password", passwordString);
                    startActivity(intent);
//                    Toast.makeText(MainActivity.this, "跳转到另一界面！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sexSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexString = sexSelect.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sexString = "";
            }
        });


    }

    private boolean checkInput() {
        boolean flag = true;
        nameString = name.getText().toString();
        passwordString = password.getText().toString();
        String ensurePasswordString = ensurePassword.getText().toString();


        if (nameString.equals("")) {
            Toast.makeText(MainActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (sexString.equals("")) {
            Toast.makeText(MainActivity.this, "请手动择性别!", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (passwordString.equals("")) {
            Toast.makeText(MainActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (!ensurePasswordString.equals(passwordString)) {
            Toast.makeText(MainActivity.this, "确认密码错误！", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (!acceptSignup.isChecked()) {
            Toast.makeText(MainActivity.this, "请先接受注册协议", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        return flag;

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }


}
