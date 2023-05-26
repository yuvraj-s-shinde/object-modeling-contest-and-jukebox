package com.crio.jukebox.dtos;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.Album;
import com.crio.jukebox.entities.Artist;
import com.crio.jukebox.entities.Song;

public class CSVParser {

    public List<Song> parse(String filename){
        try{
            CSVReader reader=
                    new CSVReaderBuilder(new FileReader(filename)).
                            build();
            List<Song> songs=reader.readAll().stream().map(data-> {
                List<Artist> featuredArtists = new ArrayList<Artist>();
                String[] artistNames= data[5].split("#");
                for (String name: artistNames) {
                    featuredArtists.add(new Artist(name));
                }
                Album album = new Album(data[3], new Artist(data[4]), featuredArtists);
                Song song = new Song(data[0], data[1], data[2], album);
                return song;
            }).collect(Collectors.toList());
            return songs;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

