package com.example.kcmusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SongFragment extends Fragment {

    private ListView mListView;
    private View mView;
    // 创建歌曲的String数组和歌手图片的int数组
    public String[] name = {"歌曲0", "歌曲1", "歌曲2"};
    public static int[] icons = {R.drawable.music0, R.drawable.music1, R.drawable.music2};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 绑定布局，只不过这里是用inflate()方法
        mView = inflater.inflate(R.layout.music_list, null);
        // 创建listView列表并且绑定控件
        mListView = mView.findViewById(R.id.music_lv);
        // 实例化一个适配器
        MyBaseAdapter adapter = new MyBaseAdapter();
        // 列表设置适配器
        mListView.setAdapter(adapter);
        // 列表元素的点击监听器
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 创建Intent对象，参数就是从SongFragment跳转到MusicActivity
                Intent intent = new Intent(SongFragment.this.getContext(), MusicActivity.class);
                // 将歌曲名和歌曲的下标存入Intent对象
                intent.putExtra("name", name[position]);
                intent.putExtra("position", String.valueOf(position));
                // 开始跳转
                startActivity(intent);
            }
        });

        return mView;
    }

    // 这里是创建一个自定义适配器，可以作为模板
    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 绑定好View，然后绑定控件
            View view = View.inflate(SongFragment.this.getContext(), R.layout.item_layout, null);
            TextView songNameTv = view.findViewById(R.id.song_name_tv);
            ImageView songImageIv = view.findViewById(R.id.song_image_iv);
            // 设置控件显示的内容，就是获取的歌曲名和歌手图片
            songNameTv.setText(name[position]);
            songImageIv.setImageResource(icons[position]);

            return view;
        }
    }
}
