package com.crio.jukebox.entities;

public class Artist extends BaseEntity {
    public Artist(Artist artist) {
        this(artist.id, artist.name);
    }

    public Artist (String id, String name) {
        this(name);
        this.id = id;
    }

    public Artist (String name) {
        this.name = name;
    }
}
