package com.android_app.music.utils;

import android.widget.ImageView;

import com.android_app.music.R;
import com.bumptech.glide.Glide;

public class GlideUtils {

    // Phương thức tải ảnh từ URL và hiển thị trên ImageView, có hỗ trợ cắt tỉ lệ khung hình
    public static void loadUrlBanner(String url, com.android_app.music.widget.AspectRatioNoRadiusImageView imageView) {
        // Kiểm tra xem URL có rỗng không, nếu rỗng thì hiển thị ảnh mặc định và thoát
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.img_no_image);
            return;
        }

        // Sử dụng thư viện Glide để tải ảnh từ URL và hiển thị trên ImageView
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.img_no_image) // Ảnh mặc định nếu tải lỗi
                .dontAnimate() // Không có hiệu ứng animation khi tải ảnh
                .into(imageView); // Hiển thị ảnh vào ImageView
    }

    // Phương thức tải ảnh từ URL và hiển thị trên ImageView
    public static void loadUrl(String url, ImageView imageView) {
        // Kiểm tra xem URL có rỗng không, nếu rỗng thì hiển thị ảnh mặc định và thoát
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.image_no_available);
            return;
        }

        // Sử dụng thư viện Glide để tải ảnh từ URL và hiển thị trên ImageView
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.image_no_available) // Ảnh mặc định nếu tải lỗi
                .dontAnimate() // Không có hiệu ứng animation khi tải ảnh
                .into(imageView); // Hiển thị ảnh vào ImageView
    }
}