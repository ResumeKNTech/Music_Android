package com.android_app.music;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControllerApplication extends Application {
    // Firebase url
    public static final String FIREBASE_URL = "https://musicapp-de99b-default-rtdb.firebaseio.com";

    public static final String CHANNEL_ID = "channel_music_basic_id";
    private static final String CHANNEL_NAME = "channel_music_basic_name";
    private FirebaseDatabase mFirebaseDatabase;

    // Phương thức để truy xuất đối tượng ControllerApplication từ bất kỳ Context nào
    public static ControllerApplication get(Context context) {
        return (ControllerApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_URL);
        createChannelNotification();
    }

    // Phương thức để tạo kênh thông báo cho ứng dụng
    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_MIN);
            channel.setSound(null, null);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    // Phương thức để lấy tham chiếu đến nút 'songs' trong cơ sở dữ liệu Firebase
    public DatabaseReference getSongsDatabaseReference() {
        return mFirebaseDatabase.getReference("/songs");
    }

    // Phương thức để lấy tham chiếu đến nút 'feedback' trong cơ sở dữ liệu Firebase
    public DatabaseReference getFeedbackDatabaseReference() {
        return mFirebaseDatabase.getReference("/feedback");
    }

    // Phương thức để lấy tham chiếu đến nút 'count' trong cơ sở dữ liệu Firebase cho một bài hát cụ thể
    public DatabaseReference getCountViewDatabaseReference(int songId) {
        return FirebaseDatabase.getInstance().getReference("/songs/" + songId + "/count");
    }
}
