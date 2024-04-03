package com.example.kcmusic;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 创建需要用到的控件变量
    private TextView mSongMenuTv;
    private TextView mAlbumMenuTv;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        setOnClickListener();

        // 若是继承FragmentActivity, fragmentManager = getFragmentManager();
        mFragmentManager = getSupportFragmentManager();
        // fragmentManager，fragmentTransaction
        mFragmentTransaction = mFragmentManager.beginTransaction();
        // 默认情况下就显示frag1
        mFragmentTransaction.replace(R.id.content_fl, new SongFragment());
        // 提交改变的内容
        mFragmentTransaction.commit();

    }

    public void findViewById() {

        mSongMenuTv = (TextView) findViewById(R.id.song_menu_tv);
        mAlbumMenuTv = (TextView) findViewById(R.id.album_menu_tv);

    }
    public void setOnClickListener() {
        mSongMenuTv.setOnClickListener(this);
        mAlbumMenuTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        int viewId = v.getId();

        mFragmentTransaction = mFragmentManager.beginTransaction();
        // 切换选项卡
        if (viewId == R.id.song_menu_tv) {
            mFragmentTransaction.replace(R.id.content_fl, new SongFragment());
        } else if (viewId == R.id.album_menu_tv) {
            mFragmentTransaction.replace(R.id.content_fl, new AlbumFragment());
        }

        mFragmentTransaction.commit();

    }
}