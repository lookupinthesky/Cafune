package com.example.android.cafune;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by Shubham on 2/21/2017.
 */

public class AlbumDetailsFragment extends Fragment {
    public AlbumDetailsFragment(){
        super();
    }


    private RecyclerAdapter mAdapter;

    public static AlbumDetailsFragment newInstance() {


        AlbumDetailsFragment fragment = new AlbumDetailsFragment();

        return fragment;
    }
    MainActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        activity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_album_details, container, false);
        RecyclerView songsOfAlbum = (RecyclerView)rootView.findViewById(R.id.songsOfAlbum);
        ArrayList<ListItem> arrayAlbumsDetails = SampleDataSet.getSampleSongs();
        mAdapter = new RecyclerAdapter(arrayAlbumsDetails, RecyclerAdapter.LIST);
        songsOfAlbum.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        songsOfAlbum.setAdapter(mAdapter);



        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolBar);
        final FrameLayout viewOnClick = (FrameLayout)rootView.findViewById(R.id.viewOnClick);

        OverScrollDecoratorHelper.setUpOverScroll(songsOfAlbum, LinearLayoutManager.VERTICAL);

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValueAnimator anim = ValueAnimator.ofInt(0, 240);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int val = (Integer) valueAnimator.getAnimatedValue();
                        ViewGroup.LayoutParams layoutParams = viewOnClick.getLayoutParams();
                        layoutParams.height = val;
                        viewOnClick.setLayoutParams(layoutParams);
                    }
                });
                anim.setDuration(500);
                anim.start();

            }
        });
        return rootView;

    }
}
