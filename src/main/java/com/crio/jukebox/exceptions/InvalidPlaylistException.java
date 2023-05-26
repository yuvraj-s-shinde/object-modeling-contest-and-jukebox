package com.crio.jukebox.exceptions;

public class InvalidPlaylistException extends RuntimeException{
    public InvalidPlaylistException()
 {
  super();
 }
 public InvalidPlaylistException(String msg)
 {
  super(msg);
 }
}
