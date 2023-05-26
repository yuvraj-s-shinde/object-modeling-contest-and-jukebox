package com.crio.jukebox.repositories;

import java.util.List;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;

public interface IPlaylistRepository extends CRUDRepository<Playlist,String> {
    public List<Playlist> findAllPlaylistForUser(String userId);
    public Playlist addSongs(String playlistId, List<Song> songs);
    public Playlist removeSongs(String playlistId, List<Song> songs);

}
