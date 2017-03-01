package com.example.android.cafune.MediaPlayer;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

/**
 * Created by Shubham on 2/28/2017.
 */

 final class PlayList {

    //Name of PlayList
    String name;

    //Photo of the PlayList
    Bitmap mPhoto;

    //Description or about playlist
    String mDescription;

    //Number of songs in the playlist
    int numberOfSongs;

    //Date this was created
    Date date;

    //Total Duration
    long time;

    //List of Songs in the playlist
    List<Song> songs;


    //constructor
    public PlayList(String name){

        this.name = name;

    }

    public PlayList(String name, Bitmap photo, String description, List<Song> songs){
        this.name = name;
        this.mPhoto = photo;
        this.mDescription = description;
        this.songs = songs;
    }


    public void setPhoto(Bitmap bitmap){
        this.mPhoto = bitmap;
    }


}
