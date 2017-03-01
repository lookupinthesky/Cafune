package com.example.android.cafune;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import static com.example.android.cafune.ArtistsFragment.ARTISTS_FRAGMENT;

/**
 * Created by Shubham on 2/24/2017.
 */

public class FragmentBackPressed {

    public FragmentBackPressed(FragmentBackPressedListener mListener){
        this.mListener = mListener;
    }

   public interface FragmentBackPressedListener {

       void onFragmentBackPressed();

   }

    FragmentBackPressedListener mListener;
  /*  OnBackPressedListener listener_album_details;
    OnBackPressedListener listener_playlist_details;
    OnBackPressedListener listener_now_playing;*/


    public void performBackAction(String fragment_tag){

        mListener.onFragmentBackPressed();

        }


    }

