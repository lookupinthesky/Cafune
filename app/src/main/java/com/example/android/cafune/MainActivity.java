package com.example.android.cafune;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.transitionseverywhere.Visibility;

import java.util.List;

import static android.R.attr.fragment;
import static android.R.attr.tag;
import static com.example.android.cafune.ArtistsFragment.ARTISTS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.ARTIST_DETAILS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.PLAYLISTS_FRAGMENT;
import static com.example.android.cafune.R.id.view;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    RadioButton playLists;
    RadioButton artists;
    ArtistsFragment artistsFragment;
    PlayListsFragment playlistsFragment;
    RadioGroup homeScreen;

    private static final int PLAYLISTS = R.id.playLists;
    private static final int ARTISTS = R.id.artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playLists = (RadioButton) findViewById(R.id.playLists);
        artists = (RadioButton) findViewById(R.id.artists);
        homeScreen = (RadioGroup) findViewById(R.id.home_screen);
        artistsFragment = ArtistsFragment.newInstance();
        playlistsFragment = PlayListsFragment.newInstance();
        displayFragment(0);
        homeScreen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                displayFragment(i);
            }
        });


    }

    public void displayFragment(int button) {

        switch (button) {
            case PLAYLISTS:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_parent, playlistsFragment, PLAYLISTS_FRAGMENT).commit();
                break;
            case ARTISTS:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_parent, artistsFragment, ARTISTS_FRAGMENT).commit();
                break;
            default:
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_parent, playlistsFragment, PLAYLISTS_FRAGMENT).commit();
        }

    }

    public  void replaceFragment(int containerId, Fragment toFragment, Fragment fromFragment, String fragment_tag){


        getSupportFragmentManager()
                .beginTransaction()

                .replace(containerId, toFragment, fragment_tag)
                .addToBackStack(null)
                .commit();
        overridePendingTransition(0, 0);
    }
    public void replaceFragment(int containerId, Fragment fragment, String toFragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, toFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {

         super.onBackPressed();

    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
