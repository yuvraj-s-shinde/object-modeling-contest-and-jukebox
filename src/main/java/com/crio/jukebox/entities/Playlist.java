package com.crio.jukebox.entities;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Playlist extends BaseEntity {
    private List<Song> songs;
    private final User creator;
    private boolean isActive;
    private Song activeSong;

    public List<Song> getSongs() {
        return songs.stream().collect(Collectors.toList());
    }

    public Playlist(Playlist playlist) {
        this(playlist.id, playlist.creator, playlist.name, playlist.songs, playlist.isActive);
    }

    public Playlist (String id, User creator, String name, List<Song> songs, boolean isActive) {
        this(creator, name, songs, isActive);
        this.id = id;
    }

    public Playlist (User creator, String name, List<Song> songs, boolean isActive) {
        this.name = name;
        this.creator = creator;
        this.songs = songs;
        this.isActive = isActive;
    }

    public User getCreator() {
        return creator;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public boolean getPlaylistIsActive() {
        return isActive;
    }

    public void setPlaylistIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setActiveSong(Song song) {
        this.activeSong = song;
    }

    public Optional<Song> getActiveSong() {
        return Optional.ofNullable(this.activeSong);
    }
}
