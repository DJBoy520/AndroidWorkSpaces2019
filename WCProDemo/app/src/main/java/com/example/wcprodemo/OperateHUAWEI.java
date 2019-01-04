package com.example.wcprodemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

public class OperateHUAWEI {
    private Activity activity;

    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final String AUDIO_UNSPECIFIED = "audio/*";
    private static final String VIDEO_UNSPECIFIED = "video/*";
    private static final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
    private static final int VIDEO_CODE = 1;
    private static final int AUDIO_CODE = 2;
    private static final int OTHER_CODE = 3;

    private Handler mHandler;

    public TextView message;

    OperateHUAWEI() {
    }

    OperateHUAWEI(Activity activity, String key, int status, TextView message) {    //activity为界面活动，此即为GetAllMsg，key为加解密秘钥,state为加解密状态码
        this.activity = activity;
        this.message = message;
    }

    public void getImage() {  //获取图片列表，供用户选取，选择的后将返回文件路径，以file://开头,小米红米
        Log.i("工作线程", "step1");
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        activity.startActivityForResult(intent, IMAGE_CODE);
    }

    public void getAudio() { //获取音频列表，以供选择，选择后将返回文件路径，以file://开头
        Intent audioIntent = new Intent(Intent.ACTION_PICK, null);
        audioIntent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, AUDIO_UNSPECIFIED);
        activity.startActivityForResult(audioIntent, AUDIO_CODE);
    }

    public void getvideo() { //调用视频列表，以供选择，选择后将返回文件路径，以file://开头
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_UNSPECIFIED);
        activity.startActivityForResult(intent, VIDEO_CODE);
    }

    public void getother() { //调用视频列表，以供选择，选择后将返回文件路径，以file://开头
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, OTHER_CODE);
    }

    public Uri geturiForImage(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = activity.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/audio/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }
}
