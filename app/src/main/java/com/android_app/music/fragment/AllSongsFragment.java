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
import com.android_app.music.databinding.FragmentAllSongsBinding;
import com.android_app.music.model.Song;
import com.android_app.music.service.MusicService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllSongsFragment extends Fragment {

    private FragmentAllSongsBinding mFragmentAllSongsBinding;
    private List<Song> mListSong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout cho fragment này
        mFragmentAllSongsBinding = FragmentAllSongsBinding.inflate(inflater, container, false);

        // Lấy danh sách tất cả các bài hát từ cơ sở dữ liệu Firebase
        getListAllSongs();

        // Khởi tạo các bộ lắng nghe sự kiện
        initListener();

        return mFragmentAllSongsBinding.getRoot();
    }

    private void getListAllSongs() {
        // Kiểm tra xem fragment có được gắn vào activity không
        if (getActivity() == null) {
            return;
        }

        // Truy vấn cơ sở dữ liệu Firebase để lấy danh sách các bài hát và sắp xếp theo số lượt nghe từ lớn đến bé
        ControllerApplication.get(getActivity()).getSongsDatabaseReference()
                .orderByChild("count")
                .limitToLast(10) // Giới hạn số lượng bài hát trả về, ở đây giả sử là 10
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mListSong = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Song song = dataSnapshot.getValue(Song.class);
                            if (song == null) {
                                return;
                            }
                            // Thêm bài hát vào đầu danh sách để giữ thứ tự từ lớn đến bé
                            mListSong.add(0, song);
                        }
                        // Hiển thị danh sách tất cả các bài hát trong RecyclerView
                        displayListAllSongs();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Hiển thị thông báo nếu có lỗi khi lấy dữ liệu từ Firebase
                        GlobalFunction.showToastMessage(getActivity(), getString(R.string.msg_get_date_error));
                    }
                });
    }


    private void displayListAllSongs() {
        // Kiểm tra xem fragment có được gắn vào activity không
        if (getActivity() == null) {
            return;
        }

        // Thiết lập RecyclerView với LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentAllSongsBinding.rcvData.setLayoutManager(linearLayoutManager);

        // Thiết lập SongAdapter với danh sách các bài hát và bộ lắng nghe sự kiện click
        SongAdapter songAdapter = new SongAdapter(mListSong, this::goToSongDetail);
        mFragmentAllSongsBinding.rcvData.setAdapter(songAdapter);
    }

    private void goToSongDetail(@NonNull Song song) {
        // Xóa danh sách các bài hát đang được phát, thêm bài hát đã chọn và bắt đầu phát nó
        MusicService.clearListSongPlaying();
        MusicService.mListSongPlaying.add(song);
        MusicService.isPlaying = false;
        GlobalFunction.startMusicService(getActivity(), Constant.PLAY, 0);
        GlobalFunction.startActivity(getActivity(), PlayMusicActivity.class);
    }

    private void initListener() {
        // Lấy MainActivity và binding của nó
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null || activity.getActivityMainBinding() == null) {
            return;
        }

        // Thiết lập bộ lắng nghe click cho nút "Phát tất cả" trong header
        activity.getActivityMainBinding().header.layoutPlayAll.setOnClickListener(v -> {
            // Xóa danh sách các bài hát đang được phát, thêm tất cả các bài hát và bắt đầu phát bài hát đầu tiên
            MusicService.clearListSongPlaying();
            MusicService.mListSongPlaying.addAll(mListSong);
            MusicService.isPlaying = false;
            GlobalFunction.startMusicService(getActivity(), Constant.PLAY, 0);
            GlobalFunction.startActivity(getActivity(), PlayMusicActivity.class);
        });
    }
}
