package com.example.android.cafune;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.android.cafune.ArtistsFragment.ALBUM_DETAILS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.ARTISTS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.ARTIST_DETAILS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.NOW_PLAYING_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.PLAYLISTS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.PLAYLIST_DETAILS_FRAGMENT;
import static com.example.android.cafune.R.id.view;
import static com.example.android.cafune.SampleDataSet.LOG_TAG;

/**
 * Created by Shubham on 2/21/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    View inflatedView;
    private ArrayList<ListItem> list;
    private final static String FROM_ACTIVITY = "";
    String fragment_tag;
    public static final int GRID = 0;
    public static final int LIST = 1;

    int position;
    OnItemClickedListener mListener;

    int screenLocation[];
    int dimensions[];
    private int layoutResourceId;

    public RecyclerAdapter(ArrayList<ListItem> list, int gridOrList, OnItemClickedListener mListener) {
        //this.fragment_tag = fragment_tag;
        this.mListener = mListener;
        this.list = list;
        switch (gridOrList) {
            case (GRID):
                layoutResourceId = R.layout.grid_item_artist;
                break;
            case (LIST):
                layoutResourceId = R.layout.list_item;
                break;
        }
    }

    public RecyclerAdapter(ArrayList<ListItem> list, int gridOrList) {
        this(list, gridOrList, null);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemImage;
        private TextView mItemInformation;
        private TextView mItemDescription;


        public ViewHolder(View v) {
            super(v);

            mItemImage = (ImageView) v.findViewById(R.id.item_image);
            mItemInformation = (TextView) v.findViewById(R.id.item_information);
            mItemDescription = (TextView) v.findViewById(R.id.item_description);

        }


    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(layoutResourceId, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ListItem item = list.get(position);
        this.position = position;
        holder.mItemImage.setImageResource(item.getImageResource());
        holder.mItemDescription.setText(item.getDescriptionResource());
        holder.mItemInformation.setText(item.getInformationResource());
        final View parentView = (View) holder.mItemImage.getParent();
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                screenLocation = new int[2];
                dimensions = new int[2];
                dimensions[0] = parentView.getWidth();
                dimensions[1] = parentView.getHeight();
                parentView.getLocationOnScreen(screenLocation);


            }
        });
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {

                    mListener.onItemClicked(position, screenLocation, dimensions);
                    Log.d(LOG_TAG, "print the values of left" + screenLocation[0]);
                    Log.d(LOG_TAG, "print the values of top" + screenLocation[1]);
                    Log.d(LOG_TAG, "print the values of width" + dimensions[0]);
                    Log.d(LOG_TAG, "print the values of height" + dimensions[1]);
                }
            }
        });

    }

    Context context;


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickedListener {
        void onItemClicked(int position, int[] screenLocation, int[] dimensions);
    }
}
