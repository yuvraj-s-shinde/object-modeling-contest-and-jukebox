package com.crio.jukebox.commands;

import java.util.List;

import com.crio.jukebox.entities.Song;
import com.crio.jukebox.services.ISongService;

public class LoadDataCommand implements ICommand{

    private final ISongService songService;
    
    public LoadDataCommand(ISongService songService) {
        this.songService = songService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute getAllSongLevelWise method of ISongService and print the result.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["LIST_QUESTION","HIGH"]
    // or
    // ["LIST_QUESTION"]

    @Override
    public void execute(List<String> tokens) {
       
        String filename = tokens.get(1);
        List<Song> songs = songService.loadAllSong(filename);
        System.out.println("Songs Loaded successfully");
    }
    
}