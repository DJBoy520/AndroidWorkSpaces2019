package com.example.qy.connectccs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread connect = new connectthread();
        connect.start();
    }

    class connectthread extends Thread {
        public void run() {
            String http = "http://10.18.6.148:8080/connects/ServletForMethod";
            URL url;
            try {
                url = new URL(http);

                //生成HttpURLConnection连接
                HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
                //设置有输出流，默认为false，就是不能传递参数。
                httpurlconnection.setDoOutput(true);
                //设置发送请求的方式。注意：一定要大写

                httpurlconnection.setRequestMethod("POST");
                //设置连接超时的时间。不过不设置，在网络异常的情况下，可能会造成程序僵死而无法继续向下执行，所以一般设置一个超时时间。单位为毫秒
                httpurlconnection.setConnectTimeout(3000);
                //设置输出流。
                OutputStreamWriter writer = null;

                writer = new OutputStreamWriter(httpurlconnection.getOutputStream(), "utf-8");
                //传递的参数，中间使用&符号分割。
                writer.write("username=dj520&password=37297003");
                //用于刷新缓冲流。因为默认她会写入到内存的缓冲流中，到一定的数据量时，才会写入，使用这个命令可以让他立即写入，不然下面就到关闭流了
                writer.flush();
                //用于关闭输出流，关闭之后就不可以输出数据了，所以要使用flush刷新缓冲流
                writer.close();
                //获得返回的请求吗。
                int responseCode = httpurlconnection.getResponseCode();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
        }
    };
}
