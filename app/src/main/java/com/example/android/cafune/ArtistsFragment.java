package com.example.android.cafune;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cafune.RecyclerAdapter.OnItemClickedListener;

import java.util.ArrayList;

import static android.R.attr.fragment;
import static android.R.attr.variablePadding;
import static com.example.android.cafune.R.id.artists_grid;

/**
 * Created by Shubham on 2/21/2017.
 */

public class ArtistsFragment extends Fragment  {
    public ArtistsFragment() {
        super();
    }


    private RecyclerAdapter mAdapter;

    public static final String ARTISTS_FRAGMENT = "artists";
    public static final String ARTIST_DETAILS_FRAGMENT = "artist_details";
    public static final String ALBUM_DETAILS_FRAGMENT = "album_details";
    public static final String NOW_PLAYING_FRAGMENT = "now_playing";
    public static final String PLAYLIST_DETAILS_FRAGMENT = "playlist_details";
    public static final String PLAYLISTS_FRAGMENT = "playlists";

    public static ArtistsFragment newInstance() {


        ArtistsFragment fragment = new ArtistsFragment();

        return fragment;
    }
    MainActivity activity ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

         activity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_artists, container, false);


        OnItemClickedListener mListener = new OnItemClickedListener() {
            @Override
            public void onItemClicked(int position, int[]screenLocation, int[]dimensions) {

                int orientation = getResources().getConfiguration().orientation;

                ArtistDetailsFragment fragment = ArtistDetailsFragment.newInstance(screenLocation, dimensions, orientation);
                activity.replaceFragment(R.id.fragment_parent2, fragment, activity.artistsFragment, ARTIST_DETAILS_FRAGMENT);
            }
        };


        RecyclerView artists_grid = (RecyclerView) rootView.findViewById(R.id.artists_grid);
        ArrayList<ListItem> arrayArtists = SampleDataSet.getSampleArtists();
        mAdapter = new RecyclerAdapter(arrayArtists, RecyclerAdapter.GRID, mListener);
        artists_grid.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(), 2));
        artists_grid.setAdapter(mAdapter);
        return rootView;
    }
    }



