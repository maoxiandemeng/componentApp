package com.jing.library.video;

/**
 * 视频播放器管理器.
 */
public class VideoPlayerManager {

    private MyVideoPlayer mVideoPlayer;

    private VideoPlayerManager() {
    }

    private static VideoPlayerManager sInstance;

    public static synchronized VideoPlayerManager instance() {
        if (sInstance == null) {
            sInstance = new VideoPlayerManager();
        }
        return sInstance;
    }

    public MyVideoPlayer getCurrentNiceVideoPlayer() {
        return mVideoPlayer;
    }

    public void setCurrentNiceVideoPlayer(MyVideoPlayer videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            releaseNiceVideoPlayer();
            mVideoPlayer = videoPlayer;
        }
    }

    public void suspendNiceVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying())) {
            mVideoPlayer.pause();
        }
    }

    public void resumeNiceVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused())) {
            mVideoPlayer.restart();
        }
    }

    public void releaseNiceVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }

    public boolean onBackPressd() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                return mVideoPlayer.exitTinyWindow();
            }
        }
        return false;
    }
}
