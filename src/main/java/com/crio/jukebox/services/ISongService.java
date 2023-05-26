package com.crio.jukebox.services;

import java.util.List;

import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.SongNotFoundException;

public interface ISongService {
    public List<Song> loadAllSong(String dataStore);
    public List<Song> findSongsById(List<String> songIds) throws SongNotFoundException;
    public Song findById (String songId) throws SongNotFoundException;
}
