package com.crio.jukebox.services;

import java.util.List;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.InvalidPlaylistException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;

public interface IPlaylistService {
    public Playlist create(String userId, String playlistName, List<String> songIds) throws UserNotFoundException, SongNotFoundException;
    public Song playPlaylist(String userId, String playlistId) throws UserNotFoundException, PlaylistNotFoundException, InvalidPlaylistException;
    public void deletePlaylist(final String userId, final String playlistId) throws PlaylistNotFoundException, UserNotFoundException;
    public Playlist modifyPlaylist(String userId, String playlistId, String action, List<String> songIds) throws UserNotFoundException, PlaylistNotFoundException, SongNotFoundException;
    public Song playSong(String userId, String nextSongLocation) throws UserNotFoundException, InvalidPlaylistException, SongNotFoundException;

}
