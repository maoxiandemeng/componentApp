package com.jing.library.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 处理崩溃情况下的工具类
 * 在Application里调用init方法进行初始化
 * 需添加权限 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */

public class CrashUtils implements Thread.UncaughtExceptionHandler {
    private volatile static CrashUtils instance;
    private Thread.UncaughtExceptionHandler mHandler;
    private boolean mInitialized;
    private String crashDir;
    private String versionName;
    private int versionCode;

    public CrashUtils() {
    }

    public static CrashUtils getInstance() {
        if (instance == null) {
            synchronized (CrashUtils.class) {
                if (instance == null) {
                    instance = new CrashUtils();
                }
            }
        }
        return instance;
    }

    public boolean init() {
        if (mInitialized) return true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            crashDir = Utils.getContext().getExternalCacheDir().getPath()
                    + File.separator + "crash" + File.separator;
        } else {
            crashDir = Utils.getContext().getCacheDir().getPath()
                    + File.separator + "crash" + File.separator;
        }
        File file = new File(crashDir);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            PackageInfo pi = Utils.getContext()
                    .getPackageManager().getPackageInfo(Utils.getContext().getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        final String fullPath = crashDir + now + ".txt";
        final File file = new File(fullPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(new FileWriter(file, false));
                    pw.write(getCrashHead());
                    throwable.printStackTrace(pw);
                    Throwable cause = throwable.getCause();
                    while (cause != null) {
                        cause.printStackTrace(pw);
                        cause = cause.getCause();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtils.closeIO(pw);
                }
            }
        }).start();
        if (mHandler != null) {
            mHandler.uncaughtException(thread, throwable);
        }
    }

    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    private String getCrashHead() {
        return "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model       : " + Build.MODEL +// 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版本
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Crash Log Head ****************\n\n";
    }
}
