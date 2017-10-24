package com.jing.library.http.listener;

import com.jing.library.http.bean.ProgressInfo;

/**
 * Created by liujing on 2017/10/23.
 */

public interface ProgressListener {
    /**
     * 进度监听
     * @param info 进度条的信息
     */
    void onProgress(ProgressInfo info);

    /**
     * 错误监听
     * @param id 进度条的id
     * @param e 错误的异常
     */
    void onError(long id, Exception e);
}
