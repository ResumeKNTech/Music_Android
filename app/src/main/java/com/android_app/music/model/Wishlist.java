package com.android_app.music.model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private int userId;
    private List<Song> songs;

    public Wishlist(int userId) {
        this.userId = userId;
        this.songs = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }
}
