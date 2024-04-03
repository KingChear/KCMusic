package com.example.kcmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {

    private MediaPlayer mMediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {

        return new MusicControl();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 创建音乐播放器对象
        mMediaPlayer = new MediaPlayer();
    }


    // Binder是一种跨进程的通信方式
    class MusicControl extends Binder {
        public void play(int i) {  // String path
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + "music" + i);
            try {
                // 重置音乐播放器
                mMediaPlayer.reset();
                // 加载多媒体文件
                mMediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mMediaPlayer.start();  // 播放音乐
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 下面的暂停继续和退出方法全部调用的是MediaPlayer自带的方法
        public void pausePlay() {
            mMediaPlayer.pause();  // 暂停播放音乐
        }

        public void continuePlay() {
            mMediaPlayer.start();  // 继续播放音乐
        }

    }

    // 销毁多媒体播放器
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMediaPlayer == null) {
            return;
        }

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();  // 停止音乐播放
        }

        mMediaPlayer.release();  // 释放占用的资源
        mMediaPlayer = null;  // 将player置为空
    }
}