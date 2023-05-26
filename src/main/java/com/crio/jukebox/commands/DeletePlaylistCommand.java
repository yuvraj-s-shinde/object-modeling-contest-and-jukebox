package com.crio.jukebox.commands;

import java.util.List;

import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

public class DeletePlaylistCommand implements ICommand{

    private final IPlaylistService playlistService;
    
    public DeletePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String playlistId = tokens.get(2);

        try {
            playlistService.deletePlaylist(userId, playlistId);
            System.out.println("Delete Successful");
        } catch (PlaylistNotFoundException | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }     
    }
    
}
