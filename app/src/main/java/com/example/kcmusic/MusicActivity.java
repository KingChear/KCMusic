package com.example.kcmusic;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mSmallSongIv;
    private TextView mSongNameTv;
    private Button mPlayBtn;
    private Button mPauseBtn;
    private Button mContinueBtn;
    private Button mExitBtn;
    private Intent mIntentFromSongFragment;
    private Intent mIntentToService;

    private MusicService.MusicControl mMusicControl;
    private MyServiceConn mMyServiceConn;

    // 记录服务是否被解绑，默认没有
    private boolean isUnbind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        // 获取从SongFragment传来的信息
        mIntentFromSongFragment = getIntent();

        findViewById();
        setOnClickListener();
        init();

    }

    private void findViewById() {

        mSongNameTv = (TextView) findViewById(R.id.song_name_tv);
        mPlayBtn = findViewById(R.id.play_btn);
        mPauseBtn = findViewById(R.id.pause_btn);
        mContinueBtn = findViewById(R.id.continue_btn);
        mExitBtn = findViewById(R.id.exit_btn);
        mSmallSongIv = findViewById(R.id.small_song_iv);

    }

    private void setOnClickListener() {
        mPlayBtn.setOnClickListener(this);
        mPauseBtn.setOnClickListener(this);
        mContinueBtn.setOnClickListener(this);
        mExitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.play_btn) {  // 播放按钮点击事件
            String position = mIntentFromSongFragment.getStringExtra("position");
            int i = Integer.parseInt(position);
            mMusicControl.play(i);
        } else if (viewId == R.id.pause_btn) {  // 暂停按钮点击事件
            mMusicControl.pausePlay();
        } else if (viewId == R.id.continue_btn) {  // 继续播放按钮点击事件
            mMusicControl.continuePlay();
        } else if (viewId == R.id.exit_btn) {  // 退出按钮点击事件
            unbind(isUnbind);
            isUnbind = true;
            finish();
        }

    }

    private void init() {
        mSongNameTv.setText(mIntentFromSongFragment.getStringExtra("name"));

        // 创建一个Intent，是从当前的Activity跳转到Service
        mIntentToService = new Intent(this, MusicService.class);
        mMyServiceConn = new MyServiceConn();  // 创建服务连接对象
        bindService(mIntentToService, mMyServiceConn, BIND_AUTO_CREATE);  // 绑定服务

        String position = mIntentFromSongFragment.getStringExtra("position");
        int i = Integer.parseInt(position);
        mSmallSongIv.setImageResource(SongFragment.icons[i]);
    }



    // 用于实现连接服务
    class MyServiceConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicControl = (MusicService.MusicControl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    // 判断服务是否被解绑
    private void unbind(boolean isUnbind) {
        // 如果解绑了
        if (!isUnbind) {
            mMusicControl.pausePlay();  // 音乐暂停播放
            unbindService(mMyServiceConn);  // 解绑服务
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind(isUnbind);  // 解绑服务
    }
}