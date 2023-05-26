package com.crio.jukebox.services;

import java.util.List;

import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.exceptions.SongNotFoundException;

public class SongService implements ISongService {
    private final ISongRepository songRepository;

    public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> loadAllSong(String dataStore) {
        songRepository.loadAllSong(dataStore);
        return songRepository.findAll();
    }

    @Override
    public List<Song> findSongsById(List<String> songIds) throws SongNotFoundException{
        return songRepository.findSongsById(songIds);
    }

    @Override
    public Song findById(String songId) throws SongNotFoundException {
        return songRepository.findById(songId).orElseThrow(() -> new SongNotFoundException("Song for given id:"+songId+" not found!"));
    }
}
