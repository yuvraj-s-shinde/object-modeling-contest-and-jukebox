package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.InvalidPlaylistException;
import com.crio.jukebox.exceptions.UserNotFoundException;

import com.crio.jukebox.services.IPlaylistService;

public class PlaySongCommand implements ICommand {

    private final IPlaylistService playlistService;
    
    public PlaySongCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String nextSongLocation = tokens.get(2);

        try {
            Song song = playlistService.playSong(userId, nextSongLocation);
            System.out.println("Current Song Playing");
            System.out.println(song.toString());
        } catch (PlaylistNotFoundException | UserNotFoundException | InvalidPlaylistException e) {
            System.out.println(e.getMessage());
        }  catch (SongNotFoundException e) {
            System.out.println("Given song id is not a part of the active playlist");
        }
    }
    
}
