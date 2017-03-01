package com.example.android.cafune.MediaPlayer;

import java.util.Comparator;

/**
 * Created by Shubham on 2/26/2017.
 */

/*This is a unit class to store all the data which will be retrieved
from the user's storage with following information per song */



public class Song {

    //id of song
    private long _ID;

    //song name
    private String title;

    //artist name
    private Artist artist;

    //album name
    private Album album;

    //song duration
    private long  duration;

    //year of release
    private String year;


    //constructor
    public Song(long _ID, String title, Artist artist, Album album, long duration, String year) {

        this._ID = _ID;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.year = year;
    }

    //getters

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public Album getAlbum() {
        return album;
    }

    public long getDuration() {
        return duration;
    }

    public String getYear() {
        return year;
    }

    public long getID() {
        return _ID;
    }

    /*Defining comparators to allow sorting of the data when stored in a Collection*/

    //when sorting by song title is required, this method would be used
    public static Comparator<Song> SORT_BY_TITLE = new Comparator<Song>() {
        @Override
        public int compare(Song t1, Song t2) {
            return t1.title.compareTo(t2.title);
        }
    };

    //when sorting by artist name is required, this method would be used
    public static Comparator<Song> SORT_BY_ARTIST = new Comparator<Song>() {
        @Override
        public int compare(Song t1, Song t2) {
            return t1.artist.getName().compareTo(t2.artist.getName());
        }
    };

    //when sorting by album name is required, this method would be used
    public static Comparator<Song> SORT_BY_ALBUM = new Comparator<Song>() {
        @Override
        public int compare(Song t1, Song t2) {
            return t1.album.getName().compareTo(t2.album.getName());
        }
    };

    //when sorting by year is required, this method would be used
    public static Comparator<Song> SORT_BY_YEAR = new Comparator<Song>() {
        @Override
        public int compare(Song t1, Song t2) {
            return t1.year.compareTo(t2.year);
        }
    };


}
