package com.example.wcprodemo;

/**
 * Created by 杜健 on 2017/10/14.
 */

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SendDateToServer {
    private String path;
    private boolean SEND_SUCESSED;
    private Map<String, String> params;
    private String encoding;
    private String state = "";
    Handler mHandler;
    HttpURLConnection httpURLConnection = null;

    SendDateToServer(Handler mHandler) {
        SEND_SUCESSED = false;
        params = new HashMap<String, String>();
        path = "http://10.3.132.37:8080/connects/servlet/ServletForMethod";
        //path = "http://10.3.132.37:8080/connects/NewFile.jsp";
        encoding = "utf-8";
        this.mHandler = mHandler;
    }

    public void setUrl(String path) {
        this.path = path;
    }

    public String getUrl() {
        return this.path;
    }

    public String getState() {
        return this.state;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setValue(String key, String value) {
        this.params.put(key, value);
    }

    public Map<String, String> getValue() {
        return params;
    }

    public boolean getSucessed() {
        return this.SEND_SUCESSED;
    }

    public void submitPostData() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = getRequestData().toString();//获得请求体，封装数据
                try {
                    URL url = new URL(path);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(3000);        //设置连接超时时间
                    httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                    httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
                    httpURLConnection.setRequestMethod("POST");    //设置以Post方式提交数据
                    httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data));
                    OutputStreamWriter outputStream;
                    outputStream = new OutputStreamWriter(httpURLConnection.getOutputStream(), encoding);//确定链接
                    outputStream.flush();
                    outputStream.write(data);//传数据
                    outputStream.close();

                    int code = httpURLConnection.getResponseCode();
                    Log.i("测试1:", "我进来啦code" + code);
                    if (code == 200) {   //获得服务器的响应码，
                        getStreamFromInputstream(mHandler, code, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Message message = new Message();
                                message.obj = response;
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void onError(Exception e) {
                                Message message = new Message();
                                message.obj = e.toString();
                                mHandler.sendMessage(message);
                            }
                        });
                        SEND_SUCESSED = true;
                    } else {
                        SEND_SUCESSED = false;
                    }
                } catch (IOException e) {
                    Log.i("测试1:", "异常1" + e.toString());
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.i("测试1:", "异常2" + e.toString());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void submitGetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = getRequestData().toString();
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(data).openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setRequestMethod("GET");
                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {      //获得服务器的响应码
                        SEND_SUCESSED = true;
                    } else {
                        SEND_SUCESSED = false;
                    }
                    Log.i("测试", "cedo" + code);
                } catch (Exception e) {
                    Log.i("测试", "异常" + e.toString());
                }
            }
        }).start();
    }

    private StringBuffer getRequestData() {
        StringBuffer stringBuffer = new StringBuffer();//存储封装好的请求体信息
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encoding))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);//删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    public void getStreamFromInputstream(Handler mHandler, int code, final HttpCallbackListener listener) throws Exception {
        if (code == 200) {
            try {
                String line;
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                if (listener != null) { //成功则回调onFinish
                    listener.onFinish(response.toString());
                }
            } catch (Exception e) {//出现异常则回调onError
                if (listener != null) {
                    listener.onError(e);
                }
            }

            /*InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
            String inputLine = null;
            // 使用循环来读取获得的数据，把数据都村到result中了
            while (((inputLine = reader.readLine()) != null)) {
                // 我们在每一行后面加上一个"\n"来换行
                result += inputLine + "\n";
            }
            reader.close();// 关闭输入流*/
        }
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
}