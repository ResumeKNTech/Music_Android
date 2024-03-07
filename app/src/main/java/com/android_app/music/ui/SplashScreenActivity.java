package com.android_app.music.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;



import com.android_app.music.databinding.ActivitySplashScreenBinding;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding mActivitySplashScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ẩn tiêu đề cửa sổ
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Đặt cửa sổ hoạt động sang chế độ toàn màn hình
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Gắn layout của splash screen vào hoạt động
        mActivitySplashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(mActivitySplashScreenBinding.getRoot());

        // Khởi động MainActivity sau một khoảng thời gian
        startMainActivity();
    }

    // Phương thức để khởi động MainActivity sau một khoảng thời gian nhất định
    private void startMainActivity() {
        // Sử dụng Handler để trì hoãn việc khởi động MainActivity
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // Tạo Intent để khởi động MainActivity
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            // Đặt cờ để xóa các hoạt động khác và tạo hoạt động mới trên đỉnh ngăn xếp
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            // Khởi động MainActivity
            startActivity(intent);
            finish();
        }, 1500); // Sau 1,5 giây (1500 mili giây)
    }
}




