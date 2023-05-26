package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;

public class PlaylistRepository implements IPlaylistRepository {

    private final Map<String,Playlist> playlistMap;
    private Integer autoIncrement = 0;
    
    public PlaylistRepository() {
        playlistMap = new HashMap<String,Playlist>();
    }

    public PlaylistRepository(Map<String, Playlist> playlistMap) {
        this.playlistMap = playlistMap;
        this.autoIncrement = playlistMap.size();
    }

    @Override
    public Playlist save(Playlist entity) {
        if( entity.getId()== null ){
            autoIncrement++;
            Playlist c = new Playlist(Integer.toString(autoIncrement),entity.getCreator(),entity.getName(),entity.getSongs(),entity.getPlaylistIsActive());
            playlistMap.put(c.getId(),c);
            return c;
        }
        playlistMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public Playlist addSongs(String playlistId, List<Song> songs) {
        Playlist playlist = playlistMap.get(playlistId);
        List<Song> existingSongs = playlist.getSongs();
        existingSongs.addAll(songs);
        playlist.setSongs(existingSongs);
        playlistMap.put(playlistId, playlist);
        return playlist;
    }

    @Override
    public Playlist removeSongs(String playlistId, List<Song> songs) {
        Playlist playlist = playlistMap.get(playlistId);
        List<Song> existingSongs = playlist.getSongs();
        existingSongs.removeAll(songs);
        playlist.setSongs(existingSongs);
        playlistMap.put(playlistId, playlist);
        return playlist;
    }

    @Override
    public List<Playlist> findAll() {
        List<Playlist> cList = new ArrayList<>(playlistMap.values());
        return cList;
    }

    @Override
    public Optional<Playlist> findById(String id) {
        return Optional.ofNullable(playlistMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        for (Map.Entry<String, Playlist> entry: playlistMap.entrySet()) {
            if (entry.getValue().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(Playlist entity) {
        // TODO Auto-generated method stub
        playlistMap.values().removeIf(value -> value.equals(entity));        
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        playlistMap.values().removeIf(value -> value.getId() == id);   
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return (long) playlistMap.size();
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Find all the list of Playlist Present in the Repository provided Level
    // Tip:- Use Java Streams

    @Override
    public List<Playlist> findAllPlaylistForUser(String userId) {
        List<Playlist> playlists = new ArrayList<>(playlistMap.values());
        return playlists.stream().filter(plylt -> plylt.getCreator().getId().equals(userId)).collect(Collectors.toList());
    }
    
}
