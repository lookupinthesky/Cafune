package com.example.android.cafune.MediaPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shubham on 2/27/2017.
 */

public class Album {

    //Name of Album
    private String name;

    //Number of Songs
    private int numberOfSongs;

    //List of Songs Mapped to this Album
    private Map<Album, List<Song>> songs;

    //constructor
    public Album(String album) {
        this.name = album;
    }

    void mapData() {
        Album album = new Album(name);
        songs = new HashMap<>();
        songs.put(album, LocalData.findSongsByAlbum(album));
    }

    //return name of the album
    public String getName() {
        return name;
    }

    //return number of songs in the album
    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    //return a mapping of songs to this album
    public Map<Album, List<Song>> getMappedSongs() {
        return songs;
    }
}
