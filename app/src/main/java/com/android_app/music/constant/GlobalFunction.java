package com.android_app.music.constant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.android_app.music.service.MusicReceiver;
import com.android_app.music.service.MusicService;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class GlobalFunction {

    // Phương thức để bắt đầu một activity mới từ một context và một lớp
    public static void startActivity(Context context, Class<?> clz) {
        Intent intent = new Intent(context, clz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // Phương thức để ẩn bàn phím mềm khi chúng ta không cần nó nữa
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    // Phương thức để mở ứng dụng Gmail với một địa chỉ email cụ thể
    public static void onClickOpenGmail(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", AboutUsConfig.GMAIL, null));
        context.startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    // Phương thức để hiển thị thông báo ngắn
    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // Phương thức để loại bỏ dấu từ một chuỗi nhập vào
    public static String getTextSearch(String input) {
        String nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    // Phương thức để bắt đầu dịch vụ nhạc với một hành động và vị trí bài hát cụ thể
    public static void startMusicService(Context ctx, int action, int songPosition) {
        Intent musicService = new Intent(ctx, MusicService.class);
        musicService.putExtra(Constant.MUSIC_ACTION, action);
        musicService.putExtra(Constant.SONG_POSITION, songPosition);
        ctx.startService(musicService);
    }

    // Phương thức để mở receiver nhạc với một hành động cụ thể
    @SuppressLint("UnspecifiedImmutableFlag")
    public static PendingIntent openMusicReceiver(Context ctx, int action) {
        Intent intent = new Intent(ctx, MusicReceiver.class);
        intent.putExtra(Constant.MUSIC_ACTION, action);

        int pendingFlag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingFlag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            pendingFlag = PendingIntent.FLAG_UPDATE_CURRENT;
        }

        return PendingIntent.getBroadcast(ctx.getApplicationContext(), action, intent, pendingFlag);
    }
}
