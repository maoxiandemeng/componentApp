package com.jing.library.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujing on 2017/10/23.
 */

public class ProgressInfo implements Parcelable{
    private long currentBytes; //当前已上传或下载的总长度
    private long contentLength; //数据总长度
    private long intervalTime; //本次调用距离上一次被调用所间隔的时间(毫秒)
    private long eachBytes; //本次调用距离上一次被调用的间隔时间内上传或下载的byte长度
    private long id; //如果同一个 Url 地址,上一次的上传或下载操作都还没结束,
    //又开始了新的上传或下载操作(比如用户点击多次点击上传或下载同一个 Url 地址,当然你也可以在上层屏蔽掉用户的重复点击),
    //此 id (请求开始时的时间)就变得尤为重要,用来区分正在执行的进度信息,因为是以请求开始时的时间作为 id ,所以值越大,说明该请求越新
    private boolean finish; //进度是否完成

    public ProgressInfo(long id) {
        this.id = id;
    }

    protected ProgressInfo(Parcel in) {
        currentBytes = in.readLong();
        contentLength = in.readLong();
        intervalTime = in.readLong();
        eachBytes = in.readLong();
        id = in.readLong();
        finish = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(currentBytes);
        dest.writeLong(contentLength);
        dest.writeLong(intervalTime);
        dest.writeLong(eachBytes);
        dest.writeLong(id);
        dest.writeByte((byte) (finish ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProgressInfo> CREATOR = new Creator<ProgressInfo>() {
        @Override
        public ProgressInfo createFromParcel(Parcel in) {
            return new ProgressInfo(in);
        }

        @Override
        public ProgressInfo[] newArray(int size) {
            return new ProgressInfo[size];
        }
    };

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public long getEachBytes() {
        return eachBytes;
    }

    public void setEachBytes(long eachBytes) {
        this.eachBytes = eachBytes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
