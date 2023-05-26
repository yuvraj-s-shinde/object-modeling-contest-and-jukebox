package com.crio.jukebox.entities;

import java.util.stream.Collectors;

public class Song extends BaseEntity {
    private final String genre;
    private final Album album;

    public String getGenre() {
        return genre;
    }

    public Album getAlbum() {
        return album;
    }

    public Song (Song song) {
        this(song.getId(), song.getName(), song.getGenre(), song.getAlbum());
    }

    public Song (String id, String name, String genre, Album album) {
        this(name, genre, album);
        this.id = id;
    }

    public Song (String name, String genre, Album album) {
        this.name = name;
        this.genre = genre;
        this.album = album;
    }

    @Override
    public String toString() {
        return  "Song - " + this.name + "\n"+
                "Album - " + this.album.getName() + "\n"+
                "Artists - " + String.join(",", this.album.getFeaturedArtists().stream().map(Artist::getName).collect(Collectors.toList()));
    }
}
