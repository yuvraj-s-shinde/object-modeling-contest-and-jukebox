package com.crio.jukebox.entities;

import java.util.List;
import java.util.stream.Collectors;

public class Album extends BaseEntity {
    private Artist albumCreator;
    private List<Artist> featuredArtists;

    public Album(Album album) {
        this(album.id, album.name, album.albumCreator, album.featuredArtists);
    }

    public Album (String id, String name, Artist albumCreator, List<Artist> featuredArtists) {
        this(name, albumCreator, featuredArtists);
        this.id = id;
    }

    public Album (String name, Artist albumCreator, List<Artist> featuredArtists) {
        this.name = name;
        this.albumCreator = albumCreator;
        this.featuredArtists = featuredArtists;
    }

    public Artist getAlbumCreator() {
        return albumCreator;
    }

    public List<Artist> getFeaturedArtists() {
        return featuredArtists.stream().collect(Collectors.toList());
    }
}
