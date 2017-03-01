package com.example.android.cafune.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.android.cafune.MediaPlayer.LocalData.findAlbumsByArtist;
import static com.example.android.cafune.MediaPlayer.LocalData.findSongsByArtist;

/**
 * Created by Shubham on 2/27/2017.
 */

public final class Artist {


    //List of Albums mapped to this artist
    private  Map<Artist, List<Album>> albums ;

    //List of Songs Mapped to this artist
    private Map<Artist, List<Song>> songs;

    //Name of Artist
    private final String name;

    //Number of Album
    private int numberOfAlbums;

    //constructor
    public Artist(String name){
        this.name = name;

    }

    void mapData(){
        Artist artist = new Artist(name);
        albums = new HashMap<>();
        albums.put(artist, LocalData.findAlbumsByArtist(artist));
        numberOfAlbums = findAlbumsByArtist(artist).size();
        songs = new HashMap<>();
        songs.put(artist, LocalData.findSongsByArtist(artist));
    }

    public String getName(){
        return name;
    }

    public int getNumberOfAlbums(){
        return numberOfAlbums;
    }

    public Map<Artist, List<Song>> getMappedSongs(){
        return songs;
    }

    public Map<Artist, List<Album>> getMappedAlbums(){
        return albums;
    }
}

