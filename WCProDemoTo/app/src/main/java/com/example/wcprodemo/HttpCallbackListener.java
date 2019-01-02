package com.example.wcprodemo;

/**
 * Created by 杜健 on 2017/10/27.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
