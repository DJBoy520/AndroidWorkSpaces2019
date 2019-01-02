package com.example.wcprodemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class OperateXIAOMI {
    private Activity activity;

    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final String AUDIO_UNSPECIFIED = "audio/*";
    public static final String VIDEO_UNSPECIFIED = "video/*";
    public static final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
    public static final int VIDEO_CODE = 1;
    public static final int AUDIO_CODE = 2;

    private String FilePath;
    private String FileName;
    private String key;
    private int status;
    private FileOperate ff;   //代操作文件对象

    private Thread thread;
    private Handler mHandler;

    public TextView message;

    OperateXIAOMI(Activity activity, String key, int status, TextView message) {    //activity为界面活动，此即为GetAllMsg，key为加解密秘钥,state为加解密状态码
        this.activity = activity;
        this.key = key;
        this.status = status;
        this.message = message;
    }

    public void init() {
        thread = new Thread();
    }

    public String getFilePath() {//获取文件的存储路径
        return this.FilePath;
    }

    public void setFilePath(String FilePath) {//设置文件的存储路径
        this.FilePath = FilePath;
    }

    public void setFileName(String FileName) {//设置文件的文件名
        this.FileName = FileName;
    }

    public String getFileName() {//获取文件的文件名
        return this.FileName;
    }

    public void getFileName(String uri) {      //根据文件uri获取文件名
        String path = uri;
        int len = path.length();
        for (int i = len - 1; i > 0; i--) {    //可优化此处，判断语句
            if (path.charAt(i) == '/') {
                setFileName(path.substring(i + 1, len));
                return;
            }
        }
    }


    public void getImage() {  //获取图片列表，供用户选取，选择的后将返回文件路径，以file://开头,小米红米
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(IMAGE_UNSPECIFIED);
        activity.startActivityForResult(intent, IMAGE_CODE);
    }

    public void getAudio() { //获取音频列表，以供选择，选择后将返回文件路径，以file://开头
        Intent audioIntent = new Intent(Intent.ACTION_GET_CONTENT);
        audioIntent.setType(AUDIO_UNSPECIFIED);
        activity.startActivityForResult(audioIntent, AUDIO_CODE);
    }

    public void getvideo() { //调用视频列表，以供选择，选择后将返回文件路径，以file://开头
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_UNSPECIFIED);
        activity.startActivityForResult(intent, VIDEO_CODE);
    }

    public void operateImage(Intent data) {
        String path = "";
        if (data != null) {
            Uri uri = data.getData();
            if (uri.getScheme().equals("content")) {
                Uri originalUri = data.getData();
                Cursor cursor = activity.getContentResolver().query(originalUri, null, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);// 获得用户选择的图片的索引值
                int column_index1 = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
                int column_index2 = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE);

                cursor.moveToFirst();// 将光标移至开头 ，这个很重要，不小心很容易引起越界
                path = cursor.getString(column_index);// 最后根据索引值获取图片路径
                String name = cursor.getString(column_index1);
                String type = cursor.getString(column_index2);

                setFilePath(path);   //获取文件的路径
                setFileName(name + "." + type.substring(6, type.length()));//获取文件名
                //setFilePath(path);   //获取文件的路径
                getFileName(path);
                if (status == 0) {
                    useaesToImage();
                } else {
                    useDeaesToImage();
                }
                return;
            }
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                Cursor cursor = activity.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            } else {
                path = uri.getPath();
            }
        }

        setFilePath(path);   //获取文件的路径
        getFileName(path);
        if (status == 0) {
            useaesToImage();
            return;
        } else {
            useDeaesToImage();
            return;
        }
    }

    public void operateAudio(Intent data) {
        Uri uri = data.getData();
        String path;

        if (!TextUtils.isEmpty(uri.getAuthority())) {
            Cursor cursor = activity.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            cursor.close();
            if (path == null) {
            }
        } else {
            path = uri.getPath();
        }

        setFilePath(path);
        getFileName(path);
        if (status == 0) {
            useaesToAudio();
        } else {
            useDeaesToAudio();
        }
    }

    public void operateVideo(Intent data) {
        Uri uri = data.getData();
        String path;
        //String path = uri.getPath();

        if (!TextUtils.isEmpty(uri.getAuthority())) {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            cursor.close();
            if (path == null) {
            }
        } else {
            path = uri.getPath();
        }

        setFilePath(path);
        getFileName(path);
        if (status == 0) {
            useaesToVideo();
        } else {
            useDeaesToVideo();
        }
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if ((requestCode == IMAGE_CODE) && (data != null)) {
            operateImage(data);
            return;
        } else if ((requestCode == AUDIO_CODE) && (data != null)) {//获取音频文件路径，file://开头
            operateAudio(data);
            return;
        } else if ((requestCode == VIDEO_CODE) && (data != null)) {//获取视频文件路径，file://开头
            operateVideo(data);
            return;
        }
        onActivityResult(requestCode, resultCode, data);
    }

    public void useaesToVideo() {
        ff = new FileOperate(mHandler, activity);
        ff.setInterruptTrue();
        ff.setkey(key);
        ff.setSaveFilePath("1-DJdown");//Environment.DIRECTORY_MOVIES
        ff.setFilePath(getFilePath());
        ff.setFileName(getFileName());

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("正在加密！");
                    }
                });
                ff.readSDcardToaes();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("加密完成！");
                    }
                });
            }
        });
        thread.start();
    }

    public void useaesToAudio() {
        ff = new FileOperate(mHandler, activity);
        ff.setInterruptTrue();
        ff.setkey(key);
        ff.setFilePath(getFilePath());
        ff.setSaveFilePath("1-DJdown");//Environment.DIRECTORY_MUSIC
        ff.setFileName(getFileName());

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("正在加密！");
                    }
                });
                ff.readSDcardToaes();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("加密完成！");
                    }
                });
            }
        });
        thread.start();
    }

    public void useaesToImage() {
        ff = new FileOperate(mHandler, activity);
        ff.setInterruptTrue();
        ff.setkey(key);
        ff.setSaveFilePath("1-DJdown");    //Environment.DIRECTORY_PICTURES
        ff.setFilePath(getFilePath());
        ff.setFileName(getFileName());

        thread = new Thread(new Runnable() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        message.setText("正在加密！");
                    }
                });
                ff.readSDcardToaes();
                mHandler.post(new Runnable() {
                    public void run() {
                        message.setText("加密完成！");
                    }
                });
            }
        });
        thread.start();
    }

    public void useDeaesToVideo() {
        ff = new FileOperate(mHandler, activity);
        ff.setInterruptTrue();
        ff.setkey(key);
        ff.setFilePath(getFilePath());
        ff.setFileName(getFileName());
        ff.setSaveFilePath("1-DJdown");

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("正在解密！");
                    }
                });
                ff.readSDcardTodeaes();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("解密完成！");
                    }
                });
            }
        });
        thread.start();
    }

    public void useDeaesToImage() {
        ff = new FileOperate(mHandler, activity);
        ff.setInterruptTrue();
        ff.setkey(key);
        ff.setFilePath(getFilePath());
        ff.setFileName(getFileName());
        ff.setSaveFilePath("1-DJdown");

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("正在解密！");
                    }
                });
                ff.readSDcardTodeaes();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("解密完成！");
                    }
                });
            }
        });
        thread.start();
    }

    public void useDeaesToAudio() {
        ff = new FileOperate(mHandler, activity);
        ff.setInterruptTrue();
        ff.setkey(key);
        ff.setFilePath(getFilePath());
        ff.setSaveFilePath("1-DJdown");
        ff.setFileName(getFileName());

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("正在解密！");
                    }
                });
                ff.readSDcardTodeaes();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("解密完成！");
                    }
                });
            }
        });
        thread.start();
    }
}
