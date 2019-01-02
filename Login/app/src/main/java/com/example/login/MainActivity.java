package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.login.R.id.register;

public class MainActivity extends Activity implements View.OnClickListener {

    String username, password;
    Button btn_login, btn_register, btn_submit;
    ;
    EditText edit_username, edit_password;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_username = (EditText) findViewById(R.id.user);
        edit_password = (EditText) findViewById(R.id.password);

        btn_login = (Button) findViewById(R.id.login);
        btn_register = (Button) findViewById(R.id.register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        Intent it2 = getIntent();
        edit_username.setText(it2.getStringExtra("uname"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                username = edit_username.getText().toString();
                password = edit_password.getText().toString();

                Toast toastshow;

                if (username.equals("admi")) {
                    if (password.equals("123456")) {
                        toastshow = Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_SHORT);
                        toastshow.setGravity(Gravity.BOTTOM, 0, 0);
                        toastshow.show();
                    } else {
                        toastshow = Toast.makeText(getApplicationContext(), "密码错误！", Toast.LENGTH_LONG);
                        toastshow.setGravity(Gravity.BOTTOM, 0, 0);
                        toastshow.show();
                    }
                } else {
                    toastshow = Toast.makeText(getApplicationContext(), "用户名不错在！", Toast.LENGTH_LONG);
                    toastshow.setGravity(Gravity.BOTTOM, 0, 0);
                    toastshow.show();
                }
                break;

            case R.id.register:
                //setContentView(R.layout.register_main);
                Intent register = new Intent(MainActivity.this, Register.class);
                startActivity(register);
                break;

            default:
                Toast.makeText(getApplicationContext(), "ERROR！", Toast.LENGTH_SHORT);
        }
    }
}