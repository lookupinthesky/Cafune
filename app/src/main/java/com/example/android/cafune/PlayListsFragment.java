package com.example.android.cafune;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.example.android.cafune.ArtistsFragment.ALBUM_DETAILS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.ARTIST_DETAILS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.PLAYLIST_DETAILS_FRAGMENT;

/**
 * Created by Shubham on 2/21/2017.
 */

public class PlayListsFragment extends Fragment {
    RecyclerAdapter mAdapter;

    public PlayListsFragment() {
        super();
    }

    public static PlayListsFragment newInstance() {


        PlayListsFragment fragment = new PlayListsFragment();

        return fragment;
    }
    MainActivity activity ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        activity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_playlists, container, false);

        RecyclerAdapter.OnItemClickedListener mListener = new RecyclerAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position, int[]screenLocation, int[]dimensions) {
                PlayListDetailsFragment fragment = PlayListDetailsFragment.newInstance();
                activity.replaceFragment(R.id.fragment_parent2, fragment, activity.playlistsFragment, PLAYLIST_DETAILS_FRAGMENT);
            }
        };



        RecyclerView list = (RecyclerView) rootView.findViewById(R.id.list);
        ArrayList<ListItem> arrayArtists = SampleDataSet.getSamplePlayLists();
        mAdapter = new RecyclerAdapter(arrayArtists, RecyclerAdapter.LIST, mListener);
        list.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        list.setAdapter(mAdapter);
        return rootView;


    }
}




