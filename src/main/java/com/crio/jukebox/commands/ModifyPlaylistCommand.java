package com.crio.jukebox.commands;

import java.util.List;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

public class ModifyPlaylistCommand implements ICommand{

    private final IPlaylistService playlistService;

    public ModifyPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String action = tokens.get(1);
        String userId = tokens.get(2);
        String playlistId = tokens.get(3);
        List<String> songIds = tokens.subList(4, tokens.size()); 
        
        try {
            Playlist playlist = playlistService.modifyPlaylist(userId, playlistId, action, songIds);
            System.out.println("Playlist ID - " + playlist.getId() + "\n"+
                                "Playlist Name - " + playlist.getName() + "\n"+
                                "Song IDs - " + String.join(" ", playlist.getSongs().stream().map(Song::getId).collect(Collectors.toList())));
        } catch (UserNotFoundException | SongNotFoundException e) {
            // System.out.println("Some Requested Songs Not Available. Please try again.");

            System.out.println(e.getMessage());
        }
    }
}
