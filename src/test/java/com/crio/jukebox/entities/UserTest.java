package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserTest")
public class UserTest {

   // TODO: WARNING!!!
   //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
   //  Any modifications in this file may result in Assessment failure!

   @Test
   @DisplayName("checkIfPlaylistExists should Return true If Playlist is Found")
   public void checkIfPlaylistExists_ShouldReturnTrue_GivenPlaylist(){
       //Arrange
       String id = "1";
       String name = "Crio.Do PhonePe TechScholars Assessment #1";
       ArrayList<Artist> featuredArtists1 = new ArrayList<>();
       featuredArtists1.add(new Artist("art1"));
       featuredArtists1.add(new Artist("art2"));
       ArrayList<Artist> featuredArtists2 = new ArrayList<>();
       featuredArtists1.add(new Artist("art3"));
       featuredArtists1.add(new Artist("art4"));
       ArrayList<Artist> featuredArtists3 = new ArrayList<>();
       featuredArtists1.add(new Artist("art4"));
       featuredArtists1.add(new Artist("art5"));

       List<Song> songs =  new ArrayList<Song>(){
           {
           add(new Song("1", "Song1","genre1",new Album("11", "album1", new Artist("alc1"), featuredArtists1)));
           add(new Song("1", "Song2","genre2",new Album("12", "album2", new Artist("alc2"), featuredArtists2)));
           add(new Song("1", "Song3","genre3",new Album("13", "album3", new Artist("alc3"), featuredArtists3)));
           }
       };
       User creator = new User("1","Yakshit");
       boolean playlistStatus = true;
       Playlist playlist = new Playlist(id, creator, name, songs, playlistStatus);
       ArrayList<Playlist> playlists = new ArrayList<>();
       playlists.add(playlist);
       User user = new User("2",name, playlists);

       //Act
       Assertions.assertTrue(user.checkIfPlaylistExists(playlist));
   }

   @Test
   @DisplayName("checkIfPlaylistExists should Return False If No Playlist is Found")
   public void checkIfPlaylistExists_ShouldReturnFalse_GivenPlaylist(){
       //Arrange
       String id = "1";
       String name = "Crio.Do PhonePe TechScholars Assessment #1";
       ArrayList<Artist> featuredArtists1 = new ArrayList<>();
       featuredArtists1.add(new Artist("art1"));
       featuredArtists1.add(new Artist("art2"));
       ArrayList<Artist> featuredArtists2 = new ArrayList<>();
       featuredArtists1.add(new Artist("art3"));
       featuredArtists1.add(new Artist("art4"));
       ArrayList<Artist> featuredArtists3 = new ArrayList<>();
       featuredArtists1.add(new Artist("art4"));
       featuredArtists1.add(new Artist("art5"));

       List<Song> songs =  new ArrayList<Song>(){
           {
           add(new Song("1", "Song1","genre1",new Album("11", "album1", new Artist("alc1"), featuredArtists1)));
           add(new Song("1", "Song2","genre2",new Album("12", "album2", new Artist("alc2"), featuredArtists2)));
           add(new Song("1", "Song3","genre3",new Album("13", "album3", new Artist("alc3"), featuredArtists3)));
           }
       };
       User creator = new User("1","Yakshit");
       boolean playlistStatus = true;
       Playlist playlist = new Playlist(id, creator, name, songs, playlistStatus);
       User user = new User("2",name);

       //Act
       Assertions.assertFalse(user.checkIfPlaylistExists(playlist));
   }
    
}
