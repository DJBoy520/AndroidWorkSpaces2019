package com.example.wcprodemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;


public class GetAllMsg extends Activity {
    private Button btn_getimage, btn_getaudio, btn_getvedio, btn_getother, btn_interrupted;

    private Activity activity = this;

    public static final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
    public static final int VIDEO_CODE = 1;
    public static final int AUDIO_CODE = 2;
    public static final int OTHER_CODE = 3;

    private int DeviceCode;

    public Typeface font;
    public TextView message;
    private Spinner model;
    public EditText get_key;   //获取key，秘钥

    private ArrayAdapter<String> arr_adapter;

    private String FilePath;
    private String FileName;
    private int status = 0;
    private FileOperate ff;   //代操作文件对象

    private Thread thread;
    private Handler mHandler;
    private SystemUtil system;   //测试手机各种属性的

    public int getDeviceCode() {
        if (system.getDeviceBrand() == "HUAWEI") DeviceCode = 0;
        if (system.getDeviceBrand() == "XIAOMI") DeviceCode = 1;
        return DeviceCode;
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

    public void init() { //初始化界面上的组件
        font = FontManager.getTypeface(this, FontManager.FONTAWESOME);

        btn_getimage = (Button) findViewById(R.id.btn_getimage);
        btn_getaudio = (Button) findViewById(R.id.btn_getaudio);
        btn_getvedio = (Button) findViewById(R.id.btn_getvedio);
        btn_getother = (Button) findViewById(R.id.btn_getother);
        btn_interrupted = (Button) findViewById(R.id.interrupted);

        message = (TextView) findViewById(R.id.message);
        get_key = (EditText) findViewById(R.id.key);
        model = (Spinner) findViewById(R.id.model);

        btn_getimage.setTypeface(font);
        btn_getvedio.setTypeface(font);
        btn_getaudio.setTypeface(font);
        btn_getother.setTypeface(font);
        btn_interrupted.setTypeface(font);

        thread = new Thread();
        system = new SystemUtil();

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if ((msg != null) && (msg.obj) != null) {
                    switch (msg.what) {
                        case 0:
                            String data = (String) msg.obj;
                            this.obtainMessage();
                            message.setText(data);
                            break;
                        default:
                            break;
                    }
                }
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        TypefaceProvider.registerDefaultIconSets();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datatoaes);

        init();

        btn_getimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDeviceCode() == 0) {
                    OperateHUAWEI huawei = new OperateHUAWEI(activity, get_key.getText().toString(), status, message);
                    huawei.getImage();
                }
                if (getDeviceCode() == 1) {
                    OperateXIAOMI xiaomi = new OperateXIAOMI(activity, message);
                    xiaomi.getImage();
                }
            }
        });//图片
        btn_getaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDeviceCode() == 0) {
                    OperateHUAWEI huawei = new OperateHUAWEI(activity, get_key.getText().toString(), status, message);
                    huawei.getAudio();
                }
                if (getDeviceCode() == 1) {
                    OperateXIAOMI xiaomi = new OperateXIAOMI(activity, message);
                    xiaomi.getAudio();
                }
            }
        });//音频
        btn_getvedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDeviceCode() == 0) {
                    OperateHUAWEI huawei = new OperateHUAWEI(activity, get_key.getText().toString(), status, message);
                    huawei.getvideo();
                }
                if (getDeviceCode() == 1) {
                    OperateXIAOMI xiaomi = new OperateXIAOMI(activity, message);
                    xiaomi.getvideo();
                }
            }
        });//视频
        btn_getother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDeviceCode() == 0) {
                    OperateHUAWEI huawei = new OperateHUAWEI(activity, get_key.getText().toString(), status, message);
                    huawei.getother();
                }
                if (getDeviceCode() == 1) {
                    OperateXIAOMI xiaomi = new OperateXIAOMI(activity, message);
                    xiaomi.getother();
                }
            }
        });//视频

        btn_interrupted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ff != null) {
                    ff.setInterruptFalse();
                    Toast.makeText(getApplicationContext(), "操作已中断！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        model.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 == 0) status = 0;
                if (arg2 == 1) status = 1;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "请选择操作模式！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void operateImage(Intent data) {
        String path = "";
        if (data != null) {
            Uri uri = data.getData();
            if (uri.getScheme().equals("content")) {
                Uri originalUri = data.getData();
                Cursor cursor = this.getContentResolver().query(originalUri, null, null, null, null);
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
                Log.i("测试aaaaaa", "path=" + path);
                getFileName(path);
                if (status == 0) {
                    useaesToImage();
                } else {
                    useDeaesToImage();
                }
                return;
            }
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
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
        Log.i("测试", "uri=" + uri.toString());
        String path;

        if (!TextUtils.isEmpty(uri.getAuthority())) {
            Cursor cursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
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
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
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

    public void operateOther(Intent data) {
        String path = "";
        Uri uri = data.getData();
        if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
            path = uri.getPath();
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
            path = getPath(this, uri);
        } else {//4.4以下下系统调用方法
            path = getRealPathFromURI(uri);
        }

        setFilePath(path);
        getFileName(path);
        if (status == 0) {
            useaesToVideo();
        } else {
            useDeaesToVideo();
        }
    }

    public Uri geturiForImage(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
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

    @Override
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
        } else if ((requestCode == OTHER_CODE) && (data != null)) {
            operateOther(data);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void useaesToVideo() {
        ff = new FileOperate(mHandler, this);
        ff.setInterruptTrue();
        ff.setkey(get_key.getText().toString());
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
        ff = new FileOperate(mHandler, this);
        ff.setInterruptTrue();
        ff.setkey(get_key.getText().toString());
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
        ff = new FileOperate(mHandler, this);
        ff.setInterruptTrue();
        ff.setkey(get_key.getText().toString());
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
        ff = new FileOperate(mHandler, this);
        ff.setInterruptTrue();
        ff.setkey(get_key.getText().toString());
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
        ff = new FileOperate(mHandler, this);
        ff.setInterruptTrue();
        ff.setkey(get_key.getText().toString());
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
        ff = new FileOperate(mHandler, this);
        ff.setInterruptTrue();
        ff.setkey(get_key.getText().toString());
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

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        Log.i("主线程", "step");

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {  // DocumentProvider
            if (isExternalStorageDocument(uri)) {   // ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.i("主线程", "step6-b");
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Log.i("主线程", "step7");
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = this.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            Log.i("主线程", "step8");
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndex(column);
                Log.i("主线程", "step9,colun_index:" + column_index);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            Toast.makeText(this, "换一个文件去！", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
