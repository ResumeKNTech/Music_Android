package com.android_app.music.utils;

import android.annotation.SuppressLint;

public class AppUtil {

    // Phương thức chuyển đổi thời gian từ milliseconds thành chuỗi dạng "phút:giây"
    @SuppressLint("DefaultLocale")
    public static String getTime(int millis) {
        // Tính toán số giây và phút từ số milliseconds đầu vào
        long second = (millis / 1000) % 60;
        long minute = millis / (1000 * 60);

        // Định dạng chuỗi "phút:giây" và trả về
        return String.format("%02d:%02d", minute, second);
    }
}