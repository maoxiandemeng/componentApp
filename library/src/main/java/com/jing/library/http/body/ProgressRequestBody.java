package com.jing.library.http.body;

import android.os.Handler;
import android.os.SystemClock;

import com.jing.library.http.bean.ProgressInfo;
import com.jing.library.http.listener.ProgressListener;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by liujing on 2017/10/23.
 * 上传的二进制数据
 */

public class ProgressRequestBody extends RequestBody {
    protected Handler mHandler;
    protected int mRefreshTime;
    protected RequestBody mDelegate;
    protected ProgressListener[] mListeners;
    protected ProgressInfo mProgressInfo;
    private BufferedSink mBufferedSink;

    public ProgressRequestBody(Handler mHandler, RequestBody mDelegate, List<ProgressListener> mListeners, int mRefreshTime) {
        this.mHandler = mHandler;
        this.mDelegate = mDelegate;
        this.mListeners = mListeners.toArray(new ProgressListener[mListeners.size()]);
        this.mRefreshTime = mRefreshTime;
        this.mProgressInfo = new ProgressInfo(System.currentTimeMillis());
    }

    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        try {
            return mDelegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(new CountingSink(sink));
        }
        try {
            mDelegate.writeTo(mBufferedSink);
            mBufferedSink.flush();
        } catch (IOException e) {
            e.printStackTrace();
            for (int i = 0; i < mListeners.length; i++) {
                mListeners[i].onError(mProgressInfo.getId(), e);
            }
            throw e;
        }
    }

    protected final class CountingSink extends ForwardingSink {
        private long totalBytesRead = 0L;
        private long lastRefreshTime = 0L;  //最后一次刷新的时间
        private long tempSize = 0L;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            try {
                super.write(source, byteCount);
            } catch (IOException e) {
                e.printStackTrace();
                for (int i = 0; i < mListeners.length; i++) {
                    mListeners[i].onError(mProgressInfo.getId(), e);
                }
                throw e;
            }
            if (mProgressInfo.getContentLength() == 0) { //避免重复调用 contentLength()
                mProgressInfo.setContentLength(contentLength());
            }
            totalBytesRead += byteCount;
            tempSize += byteCount;
            if (mListeners != null) {
                long curTime = SystemClock.elapsedRealtime();
                if (curTime - lastRefreshTime >= mRefreshTime || totalBytesRead == mProgressInfo.getContentLength()) {
                    final long finalTempSize = tempSize;
                    final long finalTotalBytesRead = totalBytesRead;
                    final long finalIntervalTime = curTime - lastRefreshTime;
                    for (int i = 0; i < mListeners.length; i++) {
                        final ProgressListener listener = mListeners[i];
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Runnable 里的代码是通过 Handler 执行在主线程的,外面代码可能执行在其他线程
                                // 所以必须使用 final ,保证在 Runnable 执行前使用到的变量,在执行时不会被修改
                                mProgressInfo.setEachBytes(finalTempSize);
                                mProgressInfo.setCurrentBytes(finalTotalBytesRead);
                                mProgressInfo.setIntervalTime(finalIntervalTime);
                                mProgressInfo.setFinish(finalTotalBytesRead == mProgressInfo.getContentLength());
                                listener.onProgress(mProgressInfo);
                            }
                        });
                    }
                    lastRefreshTime = curTime;
                    tempSize = 0;
                }
            }
        }
    }
}
