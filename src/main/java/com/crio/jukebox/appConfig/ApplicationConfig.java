package com.crio.jukebox.appConfig;

import com.crio.jukebox.commands.CreatePlaylistCommand;
import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.commands.DeletePlaylistCommand;
import com.crio.jukebox.commands.LoadDataCommand;
import com.crio.jukebox.commands.ModifyPlaylistCommand;
import com.crio.jukebox.commands.PlayPlaylistCommand;
import com.crio.jukebox.commands.PlaySongCommand;
import com.crio.jukebox.commands.CreateUserCommand;
import com.crio.jukebox.repositories.PlaylistRepository;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.SongRepository;
import com.crio.jukebox.repositories.UserRepository;
import com.crio.jukebox.services.PlaylistService;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.ISongService;
import com.crio.jukebox.services.IUserService;
import com.crio.jukebox.services.SongService;
import com.crio.jukebox.services.UserService;

public class ApplicationConfig {
    private final ISongRepository SongRepository = new SongRepository();
    private final IUserRepository userRepository = new UserRepository();
    private final IPlaylistRepository PlaylistRepository = new PlaylistRepository();

    private final ISongService SongService = new SongService(SongRepository);
    private final IUserService userService = new UserService(userRepository);
    private final IPlaylistService PlaylistService = new PlaylistService(PlaylistRepository, SongService, userRepository, userService);
    
    private final CreateUserCommand createUserCommand = new CreateUserCommand(userService);
    private final LoadDataCommand loadDataCommand = new LoadDataCommand(SongService);
    private final PlaySongCommand playSongCommand = new PlaySongCommand(PlaylistService);
    private final CreatePlaylistCommand createPlaylistCommand = new CreatePlaylistCommand(PlaylistService);
    private final DeletePlaylistCommand deletePlaylistCommand = new DeletePlaylistCommand(PlaylistService);
    private final PlayPlaylistCommand playPlaylistCommand = new PlayPlaylistCommand(PlaylistService);
    private final ModifyPlaylistCommand modifyPlaylistCommand = new ModifyPlaylistCommand(PlaylistService);
    
    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("CREATE-USER",createUserCommand);
        commandInvoker.register("LOAD-DATA",loadDataCommand);
        commandInvoker.register("PLAY-SONG",playSongCommand);
        commandInvoker.register("CREATE-PLAYLIST",createPlaylistCommand);
        commandInvoker.register("DELETE-PLAYLIST",deletePlaylistCommand);
        commandInvoker.register("PLAY-PLAYLIST",playPlaylistCommand);
        commandInvoker.register("MODIFY-PLAYLIST",modifyPlaylistCommand);
        return commandInvoker;
    }
}
