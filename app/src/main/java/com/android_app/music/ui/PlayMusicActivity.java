package com.android_app.music.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.android_app.music.R;
import com.android_app.music.adapter.MusicViewPagerAdapter;
import com.android_app.music.databinding.ActivityPlayMusicBinding;

public class PlayMusicActivity extends AppCompatActivity {

    // Khai báo biến dùng để truy cập giao diện được liên kết bằng Data Binding
    private ActivityPlayMusicBinding mActivityPlayMusicBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Liên kết layout và khởi tạo giao diện sử dụng Data Binding
        mActivityPlayMusicBinding = ActivityPlayMusicBinding.inflate(getLayoutInflater());
        setContentView(mActivityPlayMusicBinding.getRoot());

        // Khởi tạo thanh công cụ (toolbar) của activity
        initToolbar();

        // Khởi tạo giao diện người dùng
        initUI();
    }

    // Phương thức để khởi tạo thanh công cụ (toolbar) của activity
    private void initToolbar() {
        // Thiết lập hình ảnh và tiêu đề cho thanh công cụ
        mActivityPlayMusicBinding.toolbar.imgLeft.setImageResource(R.drawable.ic_back_white);
        mActivityPlayMusicBinding.toolbar.tvTitle.setText(R.string.music_player);

        // Ẩn nút "Phát tất cả" trên thanh công cụ
        mActivityPlayMusicBinding.toolbar.layoutPlayAll.setVisibility(View.GONE);

        // Thiết lập sự kiện cho nút trở lại để thoát activity khi được nhấn
        mActivityPlayMusicBinding.toolbar.imgLeft.setOnClickListener(v -> onBackPressed());
    }

    // Phương thức để khởi tạo giao diện người dùng của activity
    private void initUI() {
        // Tạo adapter để quản lý các fragment hiển thị thông tin nhạc
        MusicViewPagerAdapter musicViewPagerAdapter = new MusicViewPagerAdapter(this);

        // Thiết lập adapter cho ViewPager để hiển thị các fragment
        mActivityPlayMusicBinding.viewpager2.setAdapter(musicViewPagerAdapter);

        // Thiết lập ViewPagerIndicator để hiển thị số trang và hiệu ứng chuyển đổi giữa các trang
        mActivityPlayMusicBinding.indicator3.setViewPager(mActivityPlayMusicBinding.viewpager2);

        // Thiết lập trang mặc định của ViewPager là trang thứ hai
        mActivityPlayMusicBinding.viewpager2.setCurrentItem(1);
    }
}
