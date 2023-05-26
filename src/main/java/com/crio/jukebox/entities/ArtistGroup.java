package com.crio.jukebox.entities;

import java.util.List;
import java.util.stream.Collectors;

public class ArtistGroup extends BaseEntity {
    private final List<Artist> artists;

    public List<Artist> getArtists() {
        return artists.stream().collect(Collectors.toList());
    }

    public ArtistGroup(ArtistGroup artistGroup) {
        this(artistGroup.id, artistGroup.name, artistGroup.artists);
    }

    public ArtistGroup (String id, String name, List<Artist> artists) {
        this(name, artists);
        this.id = id;
    }

    public ArtistGroup (String name, List<Artist> artists) {
        this.name = name;
        this.artists = artists;
    }
}
