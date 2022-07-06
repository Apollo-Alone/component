package com.user.libbase.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

public class LogUtil {
    private static String TAG = "Component";
    private static Boolean MYLOG_SWITCH = false; // 日志文件总开关
    private static Boolean MYLOG_WRITE_TO_FILE = true;// 日志写入文件开关
    private static char MYLOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static String MYLOG_PATH_SDCARD_DIR = "/sdcard/log";// 日志文件在sdcard中的路径
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 0;// sd卡中日志文件的最多保存天数
    private static String MYLOGFILEName = "Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
    public Context context;


    public static void init(String tag, boolean isShowLog) {
        TAG = tag;
        MYLOG_SWITCH = isShowLog;
    }

    public static void w(Object msg) { // 警告信息
        log(msg.toString(), 'w');
    }

    public static void e(Object msg) { // 错误信息
        log(msg.toString(), 'e');
    }

    public static void d(Object msg) {// 调试信息
        log(msg.toString(), 'd');
    }

    public static void i(Object msg) {//
        log(msg.toString(), 'i');
    }

    public static void v(Object msg) {
        log(msg.toString(), 'v');
    }

    public static void w(String text) {
        log(text, 'w');
    }

    public static void e(String text) {
        log(text, 'e');
    }

    public static void d(String text) {
        log(text, 'd');
    }

    public static void i(String text) {
        log(text, 'i');
    }

    public static void v(String text) {
        log(text, 'v');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param msg
     * @param level
     */
    private static void log(String msg, char level) {
        if (MYLOG_SWITCH) {//日志文件总开关
            if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) { // 输出错误信息
                Log.e(TAG, msg);
            } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.w(TAG, msg);
            } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.d(TAG, msg);
            } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.i(TAG, msg);
            } else {
                Log.v(TAG, msg);
            }
            if (MYLOG_WRITE_TO_FILE)//日志写入文件开关
                writeLogtoFile(String.valueOf(level), msg);
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param mylogtype
     * @param text
     */
    private static void writeLogtoFile(String mylogtype, String text) {// 新建或打开日志文件
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype + "    " + TAG + "    " + text;
        File dirPath = Environment.getExternalStorageDirectory();

        File dirsFile = new File(MYLOG_PATH_SDCARD_DIR);
        if (!dirsFile.exists()) {
            dirsFile.mkdirs();
        }
        File file = new File(dirsFile.toString(), needWriteFiel + MYLOGFILEName);// MYLOG_PATH_SDCARD_DIR
        if (!file.exists()) {
            try {
                //在指定的文件夹中创建文件
                file.createNewFile();
            } catch (Exception e) {
            }
        }

        try {
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除日志文件
     */
    public static void delFile() {
        File dir = new File(MYLOG_PATH_SDCARD_DIR);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }


    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS);
        return now.getTime();
    }
}
