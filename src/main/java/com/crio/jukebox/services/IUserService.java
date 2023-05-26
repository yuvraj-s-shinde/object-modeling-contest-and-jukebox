package com.crio.jukebox.services;


import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.UserNotFoundException;

public interface IUserService {
    public User create(String name);
    public User update(User user) throws UserNotFoundException;
    public User deletePlaylist(String userId, String playlistId);
    
}
