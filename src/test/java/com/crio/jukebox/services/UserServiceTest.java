package com.crio.jukebox.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.InvalidOperationException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.IUserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UserServiceTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    // TODO: WARNING!!!
    //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
    //  Any modifications in this file may result in Assessment failure!


    @Mock
    private IUserRepository userRepositoryMock;

    @Mock
    private IPlaylistRepository playlistRepositoryMock;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Validate user creation")
    public void create_ShouldReturnUser(){
        //Arrange
        User expectedUser = new User("1", "Yakshit");
        when(userRepositoryMock.save(any(User.class))).thenReturn(expectedUser);

        //Act
        User actualUser = userService.create("Yakshit");

        //Assert
        Assertions.assertEquals(expectedUser,actualUser);
        verify(userRepositoryMock,times(1)).save(any(User.class));
    }
    
    @Test
    @DisplayName("Validate user updation")
    public void update_ShouldUpdateUser(){
        //Arrange
        
        User actualUser = new User("1", "Yakshit");
        when(userRepositoryMock.save(any(User.class))).thenReturn(actualUser);

        userService.create("Yakshit");
        
        User user2 = new User(actualUser.getId(), "YakshitUpdated");
        when(userRepositoryMock.update(any(User.class))).thenReturn(user2);


        //Act
        User actualUserUpd =  userService.update(user2);
        //Assert
        Assertions.assertEquals(user2, actualUserUpd);
        verify(userRepositoryMock,times(1)).update(any(User.class));

    }
}
