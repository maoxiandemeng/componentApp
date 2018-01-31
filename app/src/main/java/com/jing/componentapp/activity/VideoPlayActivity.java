package com.jing.componentapp.activity;

import android.os.Bundle;
import android.os.Environment;

import com.jing.componentapp.R;
import com.jing.componentapp.base.BaseActivity;
import com.jing.library.video.MyVideoPlayer;
import com.jing.library.video.TxVideoPlayerController;
import com.jing.library.video.VideoPlayerManager;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.my_video_player)
    MyVideoPlayer myVideoPlayer;
    private String videoUrl;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myVideoPlayer.setPlayerType(MyVideoPlayer.TYPE_NATIVE);
        videoUrl = Environment.getExternalStorageDirectory().getPath().concat("/02281f218cc32979e80b73c3841332f4.mp4");
//        String videoUrl = "http://vodg3higzzw.vod.126.net/vodg3higzzw/f2aac32429684c95985b8429e4643994_1509435024396_1509435071616_22761200-00000.mp4";
        String videoUrl = "https://1253526086.vod2.myqcloud.com/30385705vodgzp1253526086/8351eeee9031868223376675319/f0.mp4";
        myVideoPlayer.setVideoPath(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
//        controller.setTitle("haha");
//        controller.setLenght(98000);
        myVideoPlayer.setController(controller);
    }

    @OnClick(R.id.change)
    public void onViewClicked() {
        VideoPlayerManager.instance().releaseNiceVideoPlayer();
        videoUrl = "http://vodg3higzzw.vod.126.net/vodg3higzzw/f2aac32429684c95985b8429e4643994_1509435024396_1509435071616_22761200-00000.mp4";
//        videoUrl = "rtmp://vd4f9d5b2.live.126.net/live/aee121f49d3f470e9f706dfb7e940fc5";
        myVideoPlayer.setVideoPath(videoUrl, null);
        myVideoPlayer.start();
    }
}
