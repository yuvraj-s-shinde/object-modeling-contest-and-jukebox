package com.crio.jukebox.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class User extends BaseEntity {
    private List <Playlist> playlists;
    private Playlist activePlaylist;

    public User(User user) {
        this(user.id, user.name, user.playlists);
    }

    public User (String id, String name) {
        this(name);
        this.id = id;
        this.playlists = new LinkedList<Playlist>();
    }

    public User (String name) {
        this.name = name;
        this.playlists = new LinkedList<Playlist>();
    }

    public User (String id, String name, List<Playlist> playlists) {
        this(name);
        this.id = id;
        this.playlists = playlists;
    }

    public void addPlaylist(Playlist playlist){
        for (Playlist p: playlists) {
            if (p.getId().equals(playlist.getId())) {
                return;
            }
        }
        playlists.add(playlist);
    }

    public void updatePlaylist(Playlist playlist){
        for (Playlist p: playlists) {
            if (p.getId().equals(playlist.getId())) {
                int index = playlists.indexOf(p);
                playlists.set(index, playlist);
            }
        }
    }

    public void deletePlaylist(Playlist playlist){
        playlists.removeIf(c -> c.getId() == playlist.getId());
    }

    public void setActivePlaylist(Playlist playlist){
        this.activePlaylist = playlist;
    }

    public Optional<Playlist> getActivePlaylist(){
        return Optional.ofNullable(activePlaylist);
    }

    public List<Playlist> getPlaylists() {
        return playlists.stream().collect(Collectors.toList());
    }

    public boolean checkIfPlaylistExists(Playlist playlist){
        return playlists.contains(playlist);
    }
}
