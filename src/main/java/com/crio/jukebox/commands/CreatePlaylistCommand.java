package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

public class CreatePlaylistCommand implements ICommand{

    private final IPlaylistService playlistService;

    public CreatePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String playlistName = tokens.get(2);
        List<String> songIds = tokens.subList(3, tokens.size()); 
        
        try {
            Playlist playlist = playlistService.create(userId, playlistName, songIds);
            System.out.println("Playlist ID - " + playlist.getId());
        } catch (UserNotFoundException | SongNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
