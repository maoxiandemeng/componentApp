package com.jing.componentapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jing.componentapp.R;
import com.jing.componentapp.base.BaseActivity;
import com.jing.library.http.GlideApp;
import com.jing.library.http.ProgressManager;
import com.jing.library.http.bean.ProgressInfo;
import com.jing.library.http.listener.ProgressListener;
import com.jing.library.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liujing on 2017/10/24.
 */

public class DownLoadActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.glide_progress)
    ProgressBar glideProgress;
    @BindView(R.id.glide_progress_text)
    TextView glideProgressText;
    @BindView(R.id.download_progress)
    ProgressBar downloadProgress;
    @BindView(R.id.download_progress_text)
    TextView downloadProgressText;
    @BindView(R.id.upload_progress)
    ProgressBar uploadProgress;
    @BindView(R.id.upload_progress_text)
    TextView uploadProgressText;
    @BindView(R.id.imageView)
    ImageView imageView;

    private Handler handler;
    private OkHttpClient mOkHttpClient;
    private ProgressInfo mLastDownloadingInfo;
    private ProgressInfo mLastUploadingInfo;

    public String mImageUrl = new String("https://raw.githubusercontent.com/JessYanCoding/MVPArmsTemplate/master/art/step.png");
    public String mDownloadUrl = new String("https://raw.githubusercontent.com/JessYanCoding/MVPArmsTemplate/master/art/MVPArms.gif");
    public String mUploadUrl = new String("http://upload.qiniu.com/");


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_download;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbarBack(toolbar, "下载Demo");
        handler = new Handler(getMainLooper());
        mOkHttpClient = ProgressManager.getInstance().with(new OkHttpClient().newBuilder()).build();
        initListener();
    }

    @OnClick({R.id.glide_start, R.id.download_start, R.id.upload_start, R.id.advance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.glide_start:
                glideStart();
                break;
            case R.id.download_start:
                downloadStart();
                break;
            case R.id.upload_start:
                uploadStart();
                break;
            case R.id.advance:
                break;
        }
    }

    private void initListener() {
        //Glide 加载监听
        ProgressManager.getInstance().addResponseListener(mImageUrl, getGlideListener());

        //Okhttp/Retofit 下载监听
        ProgressManager.getInstance().addResponseListener(mDownloadUrl, getDownloadListener());

        //Okhttp/Retofit 上传监听
        ProgressManager.getInstance().addRequestListener(mUploadUrl, getUploadListener());
    }

    /**
     * 点击开始上传资源,为了演示,就不做重复点击的处理,即允许用户在还有进度没完成的情况下,使用同一个 url 开始新的上传
     */
    private void uploadStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //为了方便就不动态申请权限了,直接将文件放到CacheDir()中
                    File file = new File(getCacheDir(), "a.txt");
                    //读取Assets里面的数据,作为上传源数据
                    writeToFile(getAssets().open("a.txt"), file);

                    Request request = new Request.Builder()
                            .url(mUploadUrl)
                            .post(RequestBody.create(MediaType.parse("multipart/form-data"), file))
                            .build();

                    Response response = mOkHttpClient.newCall(request).execute();
                    response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                    //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
                    ProgressManager.getInstance().notifyOnErorr(mUploadUrl, e);
                }
            }
        }).start();
    }

    /**
     * 点击开始下载资源,为了演示,就不做重复点击的处理,即允许用户在还有进度没完成的情况下,使用同一个 url 开始新的下载
     */
    private void downloadStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(mDownloadUrl)
                            .build();

                    Response response = mOkHttpClient.newCall(request).execute();

                    InputStream is = response.body().byteStream();
                    //为了方便就不动态申请权限了,直接将文件放到CacheDir()中
                    File file = new File(getCacheDir(), "download");
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    bis.close();
                    is.close();


                } catch (IOException e) {
                    e.printStackTrace();
                    //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
                    ProgressManager.getInstance().notifyOnErorr(mDownloadUrl, e);
                }
            }
        }).start();
    }

    /**
     * 点击开始 Glide 加载图片,为了演示,就不做重复点击的处理,但是 Glide 自己对重复加载做了处理
     * 即重复加载同一个 Url 时,停止还在请求当中的进度,再开启新的加载
     */
    private void glideStart() {
        GlideApp.with(this)
                .load(mImageUrl)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }


    @NonNull
    private ProgressListener getGlideListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                int progress = progressInfo.getPercent();
                glideProgress.setProgress(progress);
                glideProgressText.setText(progress + "%");
                LogUtil.d(TAG, "--Glide-- " + progress + " %  " + progressInfo.getSpeed() + " byte/s  " + progressInfo.toString());
                if (progressInfo.isFinish()) {
                    //说明已经加载完成
                    LogUtil.d(TAG, "--Glide-- finish");
                }
            }

            @Override
            public void onError(long id, Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        glideProgress.setProgress(0);
                        glideProgressText.setText("error");
                    }
                });
            }
        };
    }

    @NonNull
    private ProgressListener getUploadListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
                // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
                // 这里我就取最新的上传进度用来展示,顺便展示下 id 的用法

                if (mLastUploadingInfo == null) {
                    mLastUploadingInfo = progressInfo;
                }

                //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
                if (progressInfo.getId() < mLastUploadingInfo.getId()) {
                    return;
                } else if (progressInfo.getId() > mLastUploadingInfo.getId()) {
                    mLastUploadingInfo = progressInfo;
                }

                int progress = mLastUploadingInfo.getPercent();
                uploadProgress.setProgress(progress);
                uploadProgressText.setText(progress + "%");
                LogUtil.d(TAG, "--Upload-- " + progress + " %  " + mLastUploadingInfo.getSpeed() + " byte/s  " + mLastUploadingInfo.toString());
                if (mLastUploadingInfo.isFinish()) {
                    //说明已经上传完成
                    LogUtil.d(TAG, "--Upload-- finish");
                }
            }

            @Override
            public void onError(long id, Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        uploadProgress.setProgress(0);
                        uploadProgressText.setText("error");
                    }
                });
            }
        };
    }

    @NonNull
    private ProgressListener getDownloadListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
                // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
                // 这里我就取最新的下载进度用来展示,顺便展示下 id 的用法

                if (mLastDownloadingInfo == null) {
                    mLastDownloadingInfo = progressInfo;
                }

                //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
                if (progressInfo.getId() < mLastDownloadingInfo.getId()) {
                    return;
                } else if (progressInfo.getId() > mLastDownloadingInfo.getId()) {
                    mLastDownloadingInfo = progressInfo;
                }

                int progress = mLastDownloadingInfo.getPercent();
                downloadProgress.setProgress(progress);
                downloadProgressText.setText(progress + "%");
                LogUtil.d(TAG, "--Download-- " + progress + " %  " + mLastDownloadingInfo.getSpeed() + " byte/s  " + mLastDownloadingInfo.toString());
                if (mLastDownloadingInfo.isFinish()) {
                    //说明已经下载完成
                    LogUtil.d(TAG, "--Download-- finish");
                }
            }

            @Override
            public void onError(long id, Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        uploadProgress.setProgress(0);
                        uploadProgressText.setText("error");
                    }
                });
            }
        };
    }

    public static File writeToFile(InputStream in, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = in.read(buf)) != -1) {
            out.write(buf, 0, buf.length);
        }
        out.close();
        return file;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //记得释放引用
        mImageUrl = null;
        mDownloadUrl = null;
        mUploadUrl = null;
    }
}
