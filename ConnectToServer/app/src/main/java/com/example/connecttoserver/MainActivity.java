package com.example.connecttoserver;

import android.app.Activity;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    String path = "http://10.18.6.148:8080/connects/ServletForMethod";
    Map<String, String> params;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        params = new HashMap<String, String>();
        params.put("username", "dj520");
        params.put("password", "3797003");
        //submitPostData();
        submitGetData();
    }

    public void submitPostData() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = getRequestData().toString();//获得请求体
                try {
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(3000);        //设置连接超时时间
                    httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                    httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
                    httpURLConnection.setRequestMethod("POST");    //设置以Post方式提交数据
                    httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data));
                    OutputStreamWriter outputStream;
                    outputStream = new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8");
                    outputStream.flush();
                    outputStream.write(data);
                    outputStream.close();
                    int response = httpURLConnection.getResponseCode();
                    //获得服务器的响应码
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public StringBuffer getRequestData() {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "utf-8"))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    public String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    public void submitGetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder(path);
                try {
                    if (params != null && !params.isEmpty()) {
                        sb.append("?");
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            sb.append(entry.getKey()).append("=");
                            sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                            sb.append("&");
                        }
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    HttpURLConnection conn = null;
                    conn = (HttpURLConnection) new URL(sb.toString()).openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    int temp = conn.getResponseCode();
                } catch (Exception e) {
                }
            }
        }).start();
    }
}
