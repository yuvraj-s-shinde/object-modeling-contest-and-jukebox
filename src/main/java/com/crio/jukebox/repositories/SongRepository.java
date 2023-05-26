package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.dtos.CSVParser;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.SongNotFoundException;

public class SongRepository implements ISongRepository {

    private final List<Song> songs;
    // private Integer autoIncrement = 0;

    public SongRepository(){
        songs = new ArrayList<Song>();
    }

    public SongRepository(List<Song> songs) {
        this.songs = songs;
        // this.autoIncrement = songs.size();
    }

    @Override
    public Song save(Song entity) {
        if( entity.getId() == null ){
            // autoIncrement++;
            Song s = new Song(songs.get(songs.size()-1).getId() + 1,entity.getName(),entity.getGenre(),entity.getAlbum());
            songs.add(s);
            return s;
        }
        songs.add(entity);
        return entity;
    }

    @Override
    public List<Song> findAll() {
        // List<Song> songs = new ArrayList<>(songs.values());
        return songs;
    }

    @Override
    public Optional<Song> findById(String id) {
        // return Optional.ofNullable(songs.get(id));
        Song song = songs.stream().filter((s) -> s.getId().equals(id)).findAny().get();
        return Optional.ofNullable(song);
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public void delete(Song entity) {
        // TODO Auto-generated method stub
        songs.remove(entity);
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        songs.removeIf(value -> value.getId() == id);
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return (long) songs.size();
    }

    @Override
    public void loadAllSong(String filename) {
        CSVParser csvParser = new CSVParser();
        this.songs.addAll(csvParser.parse(filename));
    }

    @Override
    public List<Song> findSongsById(List<String> songIds) throws SongNotFoundException {
        List<Song> songs = new ArrayList<>();
        for (String id : songIds) {
            songs.add(this.findById(id).orElseThrow(() -> new SongNotFoundException("Song for given id:"+id+" not found!")));
        }
        return songs;
    }
    
}
