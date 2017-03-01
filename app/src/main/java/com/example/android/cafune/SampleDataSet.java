package com.example.android.cafune;

import android.util.Log;

import java.util.ArrayList;


import static com.example.android.cafune.R.id.artists;

/**
 * Created by Shubham on 2/4/2017.
 */

public class SampleDataSet {

    public static final String LOG_TAG = MainActivity.class.getName();

    public static ArrayList<ListItem> getSamplePlayLists() {

        Log.d(LOG_TAG, "Hey! I am samplePlaylists I am being called");
        ArrayList<ListItem> playLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            playLists.add(new ListItem(R.string.playlists, R.string.playlists, android.R.drawable.ic_media_play, android.R.drawable.ic_media_play));
        }
        return playLists;
    }
    public static ArrayList<ListItem> getSampleArtists() {
        Log.d(LOG_TAG, "Hey! I am sampleArtists I am being called");
        ArrayList<ListItem> artists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            artists.add(new ListItem(R.string.artists, R.string.artists, android.R.drawable.ic_media_play, android.R.drawable.ic_media_play));
            Log.d(MainActivity.class.getName(), "print Artists" + artists.get(i).getDescriptionResource());
        }

        return artists;

    }

    public static ArrayList<ListItem> getSampleAlbums(){
        Log.d(LOG_TAG, "Hey! I am sampleAlbums I am being called");
        ArrayList<ListItem> albums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            albums.add(new ListItem(R.string.albums, R.string.albums, android.R.drawable.ic_media_play, android.R.drawable.ic_media_play));
            Log.d(MainActivity.class.getName(), "print Album" + albums.get(i).getDescriptionResource());
        }

        return albums;
    }
    public static ArrayList<ListItem> getSampleSongs(){
        Log.d(LOG_TAG, "Hey! I am sampleSongs I am being called");
        ArrayList<ListItem> songs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            songs.add(new ListItem(R.string.songs, R.string.songs, android.R.drawable.ic_media_play, android.R.drawable.ic_media_play));
            Log.d(MainActivity.class.getName(), "print Album" + songs.get(i).getDescriptionResource());
        }

        return songs;
    }


    public static String[] pageTitles = {"Playlists", "Artists"};


    public static int getArtistPhoto(){
        return android.R.drawable.ic_media_play;
    }

    public static int getArtistName(){
        return R.string.sample_artist_name;
    }
}
