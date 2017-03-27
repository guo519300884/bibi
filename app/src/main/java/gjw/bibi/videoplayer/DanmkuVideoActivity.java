package gjw.bibi.videoplayer;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;

import static gjw.bibi.adapter.AllareasAdapter.VIDEO;
import static gjw.bibi.adapter.AllareasAdapter.VT;

/**
 * Created by 皇上 on 2017/3/26.
 */

public class DanmkuVideoActivity extends AppCompatActivity {


    @InjectView(R.id.post_detail_nested_scroll)
    NestedScrollView postDetailNestedScroll;
    @InjectView(R.id.danmaku_player)
    DanmakuVideoPlayer danmakuPlayer;
    @InjectView(R.id.activity_detail_player)
    RelativeLayout activityDetailPlayer;

    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils orientationUtils;
    private String url;
    private String videoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmku_video);
        ButterKnife.inject(this);
        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置
        danmakuPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        danmakuPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);

        //获取视频位置和标题
        url = getIntent().getStringExtra(VIDEO);
        videoTitle = getIntent().getStringExtra(VT);


//        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        //String url = "https://res.exexm.com/cw_145225549855002";
//        danmakuPlayer.setUp(url, true, null, "测试视频");
        danmakuPlayer.setUp(url, true, null, videoTitle);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.ml);
        danmakuPlayer.setThumbImageView(imageView);

        resolveNormalVideoUI();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, danmakuPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        danmakuPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        danmakuPlayer.setRotateViewAuto(false);
        danmakuPlayer.setLockLand(false);
        danmakuPlayer.setShowFullAnimation(false);
        danmakuPlayer.setNeedLockFull(true);

        //detailPlayer.setOpenPreView(true);
        danmakuPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                danmakuPlayer.startWindowFullscreen(DanmkuVideoActivity.this, true, true);
            }
        });

        danmakuPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        danmakuPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!danmakuPlayer.isIfCurrentIsFullscreen()) {
                    danmakuPlayer.startWindowFullscreen(DanmkuVideoActivity.this, true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (danmakuPlayer.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }


    private void resolveNormalVideoUI() {
        //增加title
        danmakuPlayer.getTitleTextView().setVisibility(View.VISIBLE);
//        danmakuPlayer.getTitleTextView().setText("测试视频");
        danmakuPlayer.getTitleTextView().setText(videoTitle);
        danmakuPlayer.getBackButton().setVisibility(View.VISIBLE);

        danmakuPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
