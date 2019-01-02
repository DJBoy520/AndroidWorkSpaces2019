package com.example.wcprodemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int Success;
    private final String state = "1";//标识码，表示登录
    private String username, password;

    Button btn_login, btn_register;
    EditText edit_username, edit_password;

    Handler mHandler;

    Intent it2;

    public void setUsername() {
        this.username = edit_username.getText().toString();
    }

    public void setPassword() {
        this.password = edit_password.getText().toString();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getSuccess() {
        return this.Success;
    }

    public void setSuccess(int success) {
        this.Success = success;
    }

    public void init() {
        Success = 0;

        btn_login = (Button) findViewById(R.id.login);
        btn_register = (Button) findViewById(R.id.register);


        edit_username = (EditText) findViewById(R.id.user);
        edit_password = (EditText) findViewById(R.id.password);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if ("OK".equals(msg.obj.toString())) {
                    setSuccess(1);
                } else if ("Wrong".equals(msg.obj.toString())) {
                    setSuccess(2);
                } else {
                    setSuccess(3);
                }
                if (getSuccess() == 1) {
                    Intent dataToAes = new Intent(MainActivity.this, GetAllMsg.class);
                    startActivity(dataToAes);
                } else if (getSuccess() == 2) {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                } else if (getSuccess() == 3) {
                    Toast.makeText(getApplicationContext(), "未知错误！", Toast.LENGTH_SHORT).show();
                }
            }
        };

        it2 = getIntent();

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.datatoaes);

        //init();
        /*Log.i("codecraeer", "测试getFilesDir = " + getFilesDir());
        Log.i("codecraeer", "测试getExternalFilesDir = " + getExternalFilesDir("exter_test"));
        Log.i("codecraeer", "测试getDownloadCacheDirectory = " + Environment.getDownloadCacheDirectory().getAbsolutePath());
        Log.i("codecraeer", "测试getDataDirectory = " + Environment.getDataDirectory().getAbsolutePath());
        Log.i("codecraeer", "测试getExternalStorageDirectory = " + Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.i("codecraeer", "测试getExternalStoragePublicDirectory = " + Environment.getExternalStoragePublicDirectory("read"));
        Log.i("codecraeer", "测试Environment.DIRECTORY_DOCUMENTS = " + Environment.getExternalStorageState());*/

        Intent dataToAes = new Intent(MainActivity.this, GetAllMsg.class);
        startActivity(dataToAes);
}

    public boolean checkFromServer() {
        SendDateToServer sdts = new SendDateToServer(mHandler);
        sdts.setValue("state", state);
        sdts.setValue("username", getUsername());
        sdts.setValue("password", getPassword());
        Log.i("测试：", sdts.getUrl());
        try {
            sdts.submitPostData();
        } catch (IOException e) {
            Log.i("测试：", e.toString());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                setUsername();
                setPassword();

                if (getUsername().equals("") || getPassword().equals("")) {
                    Intent dataToAes = new Intent(MainActivity.this, GetAllMsg.class);
                    startActivity(dataToAes);
                    //Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    checkFromServer();
                }
                break;
            case R.id.register:
                Intent register = new Intent(MainActivity.this, Register.class);
                startActivity(register);
                break;
            default:
                Toast.makeText(getApplicationContext(), "ERROR！", Toast.LENGTH_SHORT);
        }
    }
}
