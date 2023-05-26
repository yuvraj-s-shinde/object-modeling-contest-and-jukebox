package com.crio.jukebox.services;

import java.util.Optional;

import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.IUserRepository;

public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String name) {
     User user = new User(name);
     return userRepository.save(user);
    }
    
    @Override
    public User update(User user) {
     return userRepository.update(user);
    }

    @Override
    public User deletePlaylist(String playlistCreatorId, String playlistId) {
     Optional<User> user = userRepository.findById(playlistCreatorId);
     return userRepository.save(user.get());
    }
    
}
