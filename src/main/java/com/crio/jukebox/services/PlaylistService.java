package com.crio.jukebox.services;

import java.util.List;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.InvalidPlaylistException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.IUserRepository;

public class PlaylistService implements IPlaylistService {
    private final IPlaylistRepository playlistRepository;
    private final ISongService songService;
    private final IUserRepository userRepository;
    private final IUserService userService;

    public PlaylistService(IPlaylistRepository playlistRepository, ISongService songService,
            IUserRepository userRepository, IUserService userService) {
        this.playlistRepository = playlistRepository;
        this.songService = songService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Playlist create(String userId, String playlistName, List<String> songIds) throws UserNotFoundException, SongNotFoundException {
        final User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot Create Playlist. Playlist Creator for given name: " + userId + " not found!"));
        final List<Song> songs = songService.findSongsById(songIds);
        Playlist playlist = playlistRepository.save(new Playlist(user,playlistName,songs,false));
        user.addPlaylist(playlist);
        userService.update(user);
        return playlist;
    }

    @Override
    public Playlist modifyPlaylist(String userId, String playlistId, String action, List<String> songIds) throws UserNotFoundException, PlaylistNotFoundException, SongNotFoundException {
        final User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot Create Playlist. Playlist Creator for given name: " + userId + " not found!"));
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistNotFoundException("Cannot update Playlist. Playlist for given id:"+playlistId+" not found!"));
        
        if(!playlist.getCreator().getId().equals(userId)){
            throw new InvalidPlaylistException("Cannot Run Playlist. User:"+userId+ " is not the playlist creator of playlist id:"+playlist.getId());
        }
        
        final List<Song> songs = songService.findSongsById(songIds);
        
        if (action.equals("ADD-SONG")) {
            playlist = playlistRepository.addSongs(playlistId, songs);
        } else if (action.equals("DELETE-SONG")) {
            playlist = playlistRepository.removeSongs(playlistId, songs);
        }
        user.updatePlaylist(playlist);
        userService.update(user);
        return playlist;
    }

    @Override
    public Song playPlaylist(String userId, String playlistId) throws UserNotFoundException, PlaylistNotFoundException, InvalidPlaylistException {
        final User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot Play Playlist. Playlist Creator for given id: " + userId + " not found!"));
        final Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistNotFoundException("Cannot Play Playlist. Playlist for given id:"+playlistId+" not found!"));
        validatePlaylist(userId, playlist);
        user.setActivePlaylist(playlist);
        playlist.setPlaylistIsActive(true);
        List<Song> songs = playlist.getSongs();
        playlist.setActiveSong(songs.get(0));
        playlistRepository.save(playlist);
        userService.update(user);
       return songs.get(0);
    }

    @Override
    public Song playSong(String userId, String nextSongLocation) throws UserNotFoundException, InvalidPlaylistException, SongNotFoundException {
        final User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot Play Playlist. Playlist Creator for given id: " + userId + " not found!"));
        final Playlist playlist = user.getActivePlaylist().orElseThrow(() -> new PlaylistNotFoundException("No active playlist!"));
        List<Song> songs = playlist.getSongs();
        
        Song song = playlist.getActiveSong().get();
        Song songToPlay = new Song(song);
        if (nextSongLocation.equals("BACK")) {
            try {
                songToPlay = songs.get(songs.indexOf(song) - 1);
                playlist.setActiveSong(songToPlay);
            } catch (IndexOutOfBoundsException e) {
                songToPlay = songs.get(songs.size()-1);
                playlist.setActiveSong(songToPlay);
            }
            
        } else if (nextSongLocation.equals("NEXT")) {
            try {
                songToPlay = songs.get(songs.indexOf(song) + 1);
                playlist.setActiveSong(songToPlay);
            } catch (IndexOutOfBoundsException e) {
                songToPlay = songs.get(0);
                playlist.setActiveSong(songToPlay);
            }

        } else {            
            songToPlay = songService.findById(nextSongLocation);
            if(!songs.contains(songToPlay)) {
                throw new SongNotFoundException("Given song id is not a part of the active playlist");
            }
            playlist.setActiveSong(songToPlay);
        }
        playlistRepository.save(playlist);
       return songToPlay;
    }
    
    private void validatePlaylist(final String userId, final Playlist playlist) throws InvalidPlaylistException {
        if(!playlist.getCreator().getId().equals(userId)){
            throw new InvalidPlaylistException("Cannot Run Playlist. User:"+userId+ " is not the playlist creator of playlist id:"+playlist.getId());
        }
        if(playlist.getPlaylistIsActive()){
            throw new InvalidPlaylistException("Cannot Run Playlist. Playlist for given id:"+playlist.getId()+" is in progress!");
        }
        if(playlist.getSongs().size() == 0) {
            throw new InvalidPlaylistException("Cannot Run Playlist. Playlist for given id:"+playlist.getId()+" does not have songs!");
        }
    }

    public void deletePlaylist(final String userId, final String playlistId) throws PlaylistNotFoundException, UserNotFoundException {
        if(!playlistRepository.existsById(playlistId)){
            throw new PlaylistNotFoundException("Playlist for given id:"+playlistId+" not found!");
        }
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException("User for given id:"+userId+ " not found!");
        }
        playlistRepository.deleteById(playlistId);
        userService.deletePlaylist(userId, playlistId);
    }
}
