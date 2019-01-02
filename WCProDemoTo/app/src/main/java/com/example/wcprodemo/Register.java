package com.example.wcprodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class Register extends Activity {
    private final String state = "0";

    private EditText username, password, repassword;
    private Button submit;

    Handler mHandler;

    public void init() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);

        submit = (Button) findViewById(R.id.submit);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if ("OK".equals(msg.obj.toString())) {
                    Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_main);
                    Intent main_activity = new Intent(Register.this, MainActivity.class);
                    main_activity.putExtra("uname", getusername());
                    startActivity(main_activity);
                } else if ("Repeat".equals(msg.obj.toString())) {
                    Toast.makeText(Register.this, "用户名已存在！", Toast.LENGTH_SHORT).show();
                } else if ("Wrong".equals(msg.obj.toString())) {
                    Toast.makeText(Register.this, "注册失败！", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();
            }
        };
    }

    public String getusername() {
        return username.getText().toString();
    }

    public String getpassword() {
        return password.getText().toString();
    }

    public String getrepassword() {
        return repassword.getText().toString();
    }

    public boolean checkup() {
        if (getusername().length() <= 15 && getusername().length() >= 5) {
            if (getpassword().length() >= 6 && getpassword().length() <= 16) {
                if (getpassword().equals(getrepassword())) {
                } else {
                    Toast.makeText(Register.this, "两次输入的密码不一致！", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(Register.this, "密码长度应在6至16个字符之间！", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(Register.this, "用户名长度应在5至15个字符之间！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void Submit() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkup()) {
                    SendDateToServer sdts = new SendDateToServer(mHandler);
                    sdts.setValue("state", state);
                    sdts.setValue("username", getusername());
                    sdts.setValue("password", getpassword());
                    try {
                        sdts.submitPostData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(Register.this, ShowUser(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        init();
        Submit();
    }
}
