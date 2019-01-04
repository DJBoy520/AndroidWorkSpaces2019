package com.example.wcprodemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class FileOperate {
    private Handler mHandler;
    private static String SDPATH;        //根目录路径，也就是sd卡的路径名
    private static String encoding = "ISO-8859-1";//存取文件时的编码

    private String FilePath;           //文件存取的路径
    private String FileName;          //文件转存时的文件名
    private String key = "dj520";               //加解密的密钥，不可超过16个字符，否则程序崩
    private boolean interrupt;
    private static String SaveFilePath = "DJdown";     //根目录下的目录名,所有的文件默认都存储在这个文件目录下

    FileOperate() {
        SDPATH = Environment.getExternalStoragePublicDirectory("").getAbsolutePath();
    }

    FileOperate(Handler mHandler, Activity activity) {
        this.mHandler = mHandler;
        mHandler.sendEmptyMessage(0);
        interrupt = true;
        //msg = new Message();
        verifyStoragePermissions(activity);
        SDPATH = Environment.getExternalStoragePublicDirectory("").getAbsolutePath();
    }

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void verifyStoragePermissions(Activity activity) {    // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {     // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public static String getAnimation(String sysbol, float count) {
        String tempsysbol = sysbol;
        int num = (int) count % 5;
        for (int i = 0; i <= num; i++) {
            tempsysbol += ".";
        }
        return tempsysbol;
    }

    public void setInterruptFalse() {
        this.interrupt = false;
    }

    public void setInterruptTrue() {
        this.interrupt = true;
    }

    public boolean getInterrupt() {
        return this.interrupt;
    }

    public void setkey(String key) {
        this.key = key;
    }

    public String getkey() {
        return this.key;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public String getFilePath() {
        return this.FilePath;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFileName() {
        return this.FileName;
    }

    public void setSaveFilePath(String SaveFilePath) {
        this.SaveFilePath = SaveFilePath;
    }

    public String getSaveFilePath() {
        return this.SaveFilePath;
    }

    public boolean checkDIRExists(String filepath) {//检查文件目录是否存在，参数为文件的路径，存在返回true
        File file = new File(SDPATH + File.separator + filepath);
        return file.exists();
    }

    public boolean createDIR(String dirpath) {   //根据输入的文件目录路径，创建一个目录文件
        File dir = null;
        if (!checkDIRExists(dirpath)) {
            dir = new File(SDPATH + File.separator + dirpath);
            dir.mkdir();
            return true;//创建成功返回true
        }
        return false;//创建失败，已存在
    }

    public String reName(String filename, int filecount) {// 文件名已存在，则按序号重起文件名
        String path = filename;
        int len = path.length();
        for (int i = len - 1; i > 0; i--) {
            if (path.charAt(i) == '.') {
                String temp1 = path.substring(i, len);
                String temp2 = path.substring(0, i);
                return temp2 + "(" + filecount + ")" + temp1;
            }
        }
        return null;
    }

    public void createFile(String filename) {         //根据文件名，搜寻文件，不存在则先创建相应的目录文件和普通文件
        createDIR(SaveFilePath);      //指定保存目录路径不存在，则先创建
        File file = new File(SDPATH + File.separator + SaveFilePath + File.separator + filename);
        try {
            if (!file.exists()) {      //若 想要返回实例的文件 不存在，则先创建
                file.createNewFile();
            } else {
                int filecount = 1;
                while (file.exists()) {      //如果文件已存在，则加序号重命名
                    String newFilename = reName(filename, filecount);
                    setFileName(newFilename);
                    file = new File(SDPATH + File.separator + SaveFilePath + File.separator + getFileName());
                    filecount++;
                }
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] readSDcardToaes() {  // 从SD卡中读取数据，文件路径和文件名已存储为全局变量,进行加密
        String sysbol = "正在加密";//正在加密
        FileInputStream in = null;
        float count = 0;
        String state = Environment.getExternalStorageState();

        try {
            createFile(getFileName());
            if (state.equals(Environment.MEDIA_MOUNTED)) {    // 判断是否存在SD卡，也会把内部存储区当做sd卡
                File file = new File(getFilePath());
                if (file.exists()) {     // 判断是否存在该目录路径
                    in = new FileInputStream(getFilePath());
                    float sumByte = FileOperate.showAvailableBytes(in) / 20480;
                    byte[] tempbytes = new byte[20480];
                    int byteread = 0;
                    String key = getkey();
                    Log.i("工作线程", "本地endpath=" + getFilePath() + ":" + count);
                    while ((byteread = in.read(tempbytes)) != -1) {
                        if (!getInterrupt()) {
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException e1) {
                                }
                            }
                            break;
                        }
                        try {
                            if (byteread != 20480) {
                                byte[] temp = new byte[byteread];
                                for (int i = 0; i < byteread; i++) {
                                    temp[i] = tempbytes[i];
                                }
                                String p = ByteToString(temp);
                                String c = AES.Aes(p, key);
                                Message msg = new Message();
                                msg.obj = "完成！";//可以是基本类型，可以是对象，可以是List、map等
                                SaveToSD(count, StringToByte(c), getFileName());
                                mHandler.sendMessage(msg);
                                break;
                            } else {
                                String p = ByteToString(tempbytes);
                                String c = AES.Aes(p, key);
                                String data = sysbol; //String data = getAnimation(sysbol, count);
                                Message msg = new Message();
                                float price = (count / sumByte) * 100;
                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                msg.obj = data + "(" + decimalFormat.format(price) + "%)";//可以是基本类型，可以是对象，可以是List、map等
                                mHandler.sendMessage(msg);
                                //Log.i("工作线程", "本地endpath=" + getFilePath() + ":" + count);
                                SaveToSD(count++, StringToByte(c), getFileName());
                            }
                        } catch (Exception e) {
                            Log.e("------", e.toString());
                            return null;
                        }
                    }
                } else {
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    public byte[] readSDcardTodeaes() {  // 从SD卡中读取数据，文件路径和文件名已存储为全局变量,进行解密
        FileInputStream in = null;
        int count = 0;
        String sysbol = "正在解密";
        String state = Environment.getExternalStorageState();
        try {
            createFile(getFileName());
            if (state.equals(Environment.MEDIA_MOUNTED)) {// 判断是否存在SD卡，也会把内部存储区当做sd卡
                File file = new File(getFilePath());
                if (file.exists()) {  // 判断是否存在该目录路径
                    in = new FileInputStream(getFilePath());
                    float sumByte = FileOperate.showAvailableBytes(in) / 20480;
                    byte[] tempbytes = new byte[20480];
                    int byteread = 0;
                    String key = getkey();
                    Log.i("工作线程", "本地endpath=" + getFilePath() + ":" + count);
                    while ((byteread = in.read(tempbytes)) != -1) {
                        if (!getInterrupt()) {
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException e1) {
                                }
                            }
                            break;
                        }
                        try {
                            if (byteread != 20480) {
                                byte[] temp = new byte[byteread];
                                for (int i = 0; i < byteread; i++) {
                                    temp[i] = tempbytes[i];
                                }
                                String c = ByteToString(temp);
                                String p = AES.deAes(c, key);
                                Message msg = new Message();
                                msg.obj = "完成！";
                                SaveToSD(count++, StringToByte(p), getFileName());
                                mHandler.sendMessage(msg);
                                break;
                            } else {
                                String c = ByteToString(tempbytes);
                                String p = AES.deAes(c, key);
                                String data = sysbol;//String data = getAnimation(sysbol, count);
                                Message msg = new Message();
                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                msg.obj = data + "(" + decimalFormat.format((count / sumByte) * 100) + "%)";
                                mHandler.sendMessage(msg);
                                SaveToSD(count++, StringToByte(p), getFileName());
                            }
                        } catch (Exception e) {
                            Log.e("工作线程异常报错：", e.toString());
                            return null;
                        }
                    }
                } else {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    public static void SaveToSD(float count, byte[] data, String filename) {// 将数据写回硬盘
        FileOutputStream out = null;
        if ((data == null) || (data.length < 3) || (SaveFilePath.equals("")))
            return;
        try {
            String endPath = SDPATH + File.separator + SaveFilePath + File.separator + filename;
            out = new FileOutputStream(endPath, true);
            out.write(data);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] StringToByte(String data) {
        if (data == null)
            return null;
        byte[] bty = null;
        try {
            bty = data.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bty;
    }

    public static String ByteToString(byte[] data) {
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static int showAvailableBytes(InputStream in) {    //显示文件大小
        try {
            Log.i("------", "文件大小:" + in.available());
            return in.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
