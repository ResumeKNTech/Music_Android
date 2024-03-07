package com.android_app.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android_app.music.ControllerApplication;
import com.android_app.music.R;
import com.android_app.music.ui.MainActivity;
import com.android_app.music.ui.PlayMusicActivity;
import com.android_app.music.adapter.SongAdapter;
import com.android_app.music.constant.Constant;
import com.android_app.music.constant.GlobalFunction;
import com.android_app.music.databinding.FragmentFeaturedSongsBinding;
import com.android_app.music.model.Song;
import com.android_app.music.service.MusicService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeaturedSongsFragment extends Fragment {

    private FragmentFeaturedSongsBinding mFragmentFeaturedSongsBinding;
    private List<Song> mListSong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentFeaturedSongsBinding = FragmentFeaturedSongsBinding.inflate(inflater, container, false);

        getListFeaturedSongs();
        initListener();

        return mFragmentFeaturedSongsBinding.getRoot();
    }

    // Phương thức để lấy danh sách các bài hát nổi bật từ Firebase Realtime Database
    private void getListFeaturedSongs() {
        if (getActivity() == null) {
            return;
        }
        ControllerApplication.get(getActivity()).getSongsDatabaseReference().orderByChild("count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListSong = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Song song = dataSnapshot.getValue(Song.class);
                    if (song == null) {
                        continue;
                    }
                    if (song.isFeatured()) {
                        mListSong.add(0, song); // Thêm vào đầu danh sách để hiển thị các bài hát mới nhất đầu tiên
                    }
                }
                displayListFeaturedSongs();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                GlobalFunction.showToastMessage(getActivity(), getString(R.string.msg_get_date_error));
            }
        });
    }


    // Phương thức để hiển thị danh sách các bài hát nổi bật
    private void displayListFeaturedSongs() {
        if (getActivity() == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentFeaturedSongsBinding.rcvData.setLayoutManager(linearLayoutManager);

        // Tạo Adapter cho RecyclerView và set nó vào RecyclerView
        SongAdapter songAdapter = new SongAdapter(mListSong, this::goToSongDetail);
        mFragmentFeaturedSongsBinding.rcvData.setAdapter(songAdapter);
    }

    // Phương thức để chuyển đến màn hình phát nhạc khi người dùng chọn một bài hát
    private void goToSongDetail(@NonNull Song song) {
        // Xóa danh sách bài hát đang phát hiện tại và thêm bài hát được chọn vào danh sách mới
        MusicService.clearListSongPlaying();
        MusicService.mListSongPlaying.add(song);
        MusicService.isPlaying = false;
        // Bắt đầu dịch vụ phát nhạc với hành động "PLAY" và vị trí bắt đầu từ 0
        GlobalFunction.startMusicService(getActivity(), Constant.PLAY, 0);
        // Chuyển đến màn hình phát nhạc
        GlobalFunction.startActivity(getActivity(), PlayMusicActivity.class);
    }

    // Phương thức để thiết lập sự kiện khi người dùng nhấn vào nút "Phát tất cả"
    private void initListener() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null || activity.getActivityMainBinding() == null) {
            return;
        }
        activity.getActivityMainBinding().header.layoutPlayAll.setOnClickListener(v -> {
            // Xóa danh sách bài hát đang phát hiện tại và thêm tất cả các bài hát vào danh sách mới
            MusicService.clearListSongPlaying();
            MusicService.mListSongPlaying.addAll(mListSong);
            MusicService.isPlaying = false;
            // Bắt đầu dịch vụ phát nhạc với hành động "PLAY" và vị trí bắt đầu từ 0
            GlobalFunction.startMusicService(getActivity(), Constant.PLAY, 0);
            // Chuyển đến màn hình phát nhạc
            GlobalFunction.startActivity(getActivity(), PlayMusicActivity.class);
        });
    }
}
