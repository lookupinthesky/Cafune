package com.example.android.cafune.MediaPlayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Shubham on 2/25/2017.
 */

public final class LocalData {


    private LocalData(){
        throw new RuntimeException();
    }

    private static List<Artist> artists;

    private static List<Album> albums;

    private static List<Song> songs;

    private static List<PlayList> playLists;

    private static List<PlayList> playListDetails;


    //sets of artists, albums and songs to ensure unique values

    private static Set<Artist> setOfArtists = new HashSet<Artist>();

    private static Set<Album> setOfAlbums = new HashSet<Album>();

    private static Set<Song> setOfSongs;


    public static  List<Artist> artists(){
        return artists;
    }

    public static  List<Album> albums(){
        return albums;
    }

    public static  List<Song> songs(){
        return songs;
    }

    public static  List<PlayList> playlists(){
        return playLists;
    }

    public static  List<PlayList> playlistDetails(){
        return playListDetails;
    }



    public static final String[] projection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.DURATION
    };


        //Some audio may be explicitly marked as not being music
      public static final String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";




    ContentResolver contentResolver = mContext.getContentResolver();
    Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    Cursor songCursor = contentResolver.query(songUri, null, null, null, null);


    public static final void populateSongsData(Cursor songCursor) {
        ArrayList<Song> musicData = new ArrayList<>();
        if (songCursor != null && songCursor.moveToFirst()) {
            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songAlbumId = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int songYear = songCursor.getColumnIndex(MediaStore.Audio.Media.YEAR);
            int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

            do {
                long currentId = songCursor.getLong(songId);
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentAlbum = songCursor.getString(songAlbum);
                long currentAlbumId = songCursor.getLong(songAlbumId);
                String currentSongYear = Integer.toString(songCursor.getInt(songYear));
                long currentDuration = songCursor.getLong(songDuration);

                Artist artist = new Artist(currentArtist);
                Album album = new Album(currentAlbum);


                setOfArtists.add(artist);

                setOfAlbums.add(album);

                setOfSongs.add(new Song(currentId,
                        currentTitle,
                        artist,
                        album,
                        currentDuration,
                        currentSongYear));
            } while (songCursor.moveToNext());
        }
        songs = new ArrayList<Song>(setOfSongs);
        artists = new ArrayList<Artist>(setOfArtists);
        albums = new ArrayList<Album>(setOfAlbums);
        mapAllData();
    }

    private static void mapAllData(){

        for(int i = 0; i<artists.size(); i++){

            artists.get(i).mapData();
            albums.get(i).mapData();

        }


    }



















    public ArrayList<Song> sortByArtist() {
        ArrayList<Song> artistsArray = new ArrayList<Song>();
        artistsArray = getLocalSongsData();
        Collections.sort(artistsArray, Song.SORT_BY_ARTIST);
        return artistsArray;
    }

    public ArrayList<Song> sortByTitle() {
        ArrayList<Song> titlesArray = new ArrayList<Song>();
        titlesArray = getLocalSongsData();
        Collections.sort(titlesArray, Song.SORT_BY_TITLE);
        return titlesArray;
    }

    public ArrayList<Song> sortByAlbum() {
        ArrayList<Song> albumsArray = new ArrayList<Song>();
        albumsArray = getLocalSongsData();
        Collections.sort(albumsArray, Song.SORT_BY_ALBUM);
        return albumsArray;
    }

    public ArrayList<Song> sortByYear() {
        ArrayList<Song> yearsArray = new ArrayList<Song>();
        yearsArray = getLocalSongsData();
        Collections.sort(yearsArray, Song.SORT_BY_YEAR);
        return yearsArray;
    }

    public ArrayList<ArrayList<Song>> groupByArtist() {
        ArrayList<Song> list = sortByArtist();
        ArrayList<Song> artist = new ArrayList<>();
        ArrayList<ArrayList<Song>> masterList = new ArrayList<>();
        artist.add(list.get(0));
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            if (list.get(i).getArtistName() == list.get(i + 1).getArtistName()) {
                artist.add(list.get(i + 1));
            } else {
                masterList.add(artist);
                artist = new ArrayList<>();
                artist.add(list.get(i + 1));
            }
        }
        return masterList;

    }

    public ArrayList<ArrayList<Song>> groupByAlbum() {
        ArrayList<Song> list = sortByAlbum();
        ArrayList<Song> album = new ArrayList<>();
        ArrayList<ArrayList<Song>> masterList = new ArrayList<>();
        album.add(list.get(0));
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            if (list.get(i).getAlbumName() == list.get(i + 1).getAlbumName()) {
                album.add(list.get(i + 1));
            } else {
                masterList.add(album);
                album = new ArrayList<>();
                album.add(list.get(i + 1));
            }
        }
        return masterList;

    }

    public static List<Song> findSongsByArtist(Artist artist) {
        String name = artist.getName();
        List<Song> musicData = getLocalSongsData();
        List<Song> songsByArtists = new ArrayList<>();
        for (int i = 0; i < musicData.size(); i++) {
            if (musicData.get(i).getArtist().getName().equals(name)) {
                //TODO: Handle null cases
                songsByArtists.add(musicData.get(i));
            }
        }
        return songsByArtists;
    }

    public static List<Album> findAlbumsByArtist(Artist artist) {
        List<Album> albumsByArtist = new ArrayList<>();
        List<Song> musicData = getLocalSongsData();
        for (int i = 0; i < musicData.size(); i++) {
            if (musicData.get(i).getArtist().getName().equals(artist.getName())) {
                //TODO: Handle null cases
                Album album = musicData.get(i).getAlbum();
                albumsByArtist.add(album);
            }
        }
        return albumsByArtist;

    }

    public static List<Song> findSongsByAlbum(Album album) {
        List<Song> songsByAlbum = new ArrayList<>();
        List<Song> musicData = getLocalSongsData();
        for (int i = 0; i < musicData.size(); i++) {
            if (musicData.get(i).getAlbum().equals(album)) {
                songsByAlbum.add(musicData.get(i));
            }
        }
        return songsByAlbum;
    }

}
