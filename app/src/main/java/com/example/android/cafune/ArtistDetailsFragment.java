package com.example.android.cafune;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.transitionseverywhere.Fade;
import java.util.ArrayList;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import static android.R.attr.bitmap;
import static android.R.attr.width;
import static android.provider.ContactsContract.Directory.PACKAGE_NAME;
import static com.example.android.cafune.ArtistsFragment.ALBUM_DETAILS_FRAGMENT;
import static com.example.android.cafune.ArtistsFragment.ARTIST_DETAILS_FRAGMENT;
import static com.example.android.cafune.R.id.albumsOfArtist;
import static com.example.android.cafune.R.id.artist_photo;
import static com.example.android.cafune.R.id.fragment_parent2;
import static java.lang.annotation.ElementType.PACKAGE;

/**
 * Created by Shubham on 2/21/2017.
 */

public class ArtistDetailsFragment extends Fragment {
    public ArtistDetailsFragment() {
        super();
    }


    private RecyclerAdapter mAdapter;

    public static ArtistDetailsFragment newInstance(int[] screenLocation, int[] dimensions, int orientation) {

        ArtistDetailsFragment fragment = new ArtistDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(PACKAGE + ".orientation", orientation);
        arguments.putInt(PACKAGE + ".left", screenLocation[0]);
        arguments.putInt(PACKAGE + ".top", screenLocation[1]);
        arguments.putInt(PACKAGE + ".width", dimensions[0]);
        arguments.putInt(PACKAGE + ".height", dimensions[1]);

        fragment.setArguments(arguments);

        return fragment;
    }

    MainActivity activity;
    View rootView;

    int mLeftDelta;
    int mTopDelta;
    ColorDrawable mBackground;
    float mWidthScale;
    float mHeightScale;
int mOriginalOrientation;
    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
    static float sAnimatorScale = 1;
    private static final int ANIM_DURATION = 500;
int resourceID;
    int thumbnailWidth;
    int thumbnailHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Transparent);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        rootView = localInflater.inflate(R.layout.fragment_artist_details, container, false);
        activity = (MainActivity) getActivity();
        RecyclerView albumsOfArtist = (RecyclerView) rootView.findViewById(R.id.albumsOfArtist);
        ImageView artistPhoto = (ImageView) rootView.findViewById(R.id.artist_photo);
        TextView artistName = (TextView) rootView.findViewById(R.id.artist_name);
        RecyclerAdapter.OnItemClickedListener mListener = new RecyclerAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position, int[]screenLocation, int[]dimensions) {
                AlbumDetailsFragment fragment = AlbumDetailsFragment.newInstance();
                activity.replaceFragment(fragment_parent2, fragment, ALBUM_DETAILS_FRAGMENT);
            }
        };

        ArrayList<ListItem> arrayArtistsDetails = SampleDataSet.getSampleAlbums();
        mAdapter = new RecyclerAdapter(arrayArtistsDetails, RecyclerAdapter.LIST, mListener);
        albumsOfArtist.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        albumsOfArtist.setAdapter(mAdapter);

       resourceID = SampleDataSet.getArtistPhoto();
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), resourceID);
        BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), mBitmap);
        artistName.setText(SampleDataSet.getArtistName());
        Bundle bundle = getArguments();
        final int thumbnailTop = bundle.getInt(PACKAGE_NAME + ".top");
        final int thumbnailLeft = bundle.getInt(PACKAGE_NAME + ".left");
        thumbnailWidth = bundle.getInt(PACKAGE_NAME + ".width");
        thumbnailHeight  = bundle.getInt(PACKAGE_NAME + ".height");
        mOriginalOrientation = bundle.getInt(PACKAGE_NAME + ".orientation");

        artistPhoto.setImageDrawable(mBitmapDrawable);


        mBackground = new ColorDrawable(Color.WHITE);
        rootView.setBackground(mBackground);

        if (savedInstanceState == null) {
            ViewTreeObserver observer = rootView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    rootView.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    rootView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / rootView.getWidth();
                    mHeightScale = (float) thumbnailHeight / rootView.getHeight();

                    runEnterAnimation();

                    return true;
                }
            });
        }


        return rootView;
    }

    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location, colorizing it in parallel. In parallel, the background of the
     * activity is fading in. When the pictue is in place, the text description
     * drops down.
     */
    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION * sAnimatorScale);

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        rootView.setPivotX(thumbnailWidth/2);
        rootView.setPivotY(thumbnailHeight/2);
        rootView.setScaleX(mWidthScale);
        rootView.setScaleY(mHeightScale);
        rootView.setTranslationX(mLeftDelta);
        rootView.setTranslationY(mTopDelta);

        // We'll fade the text in later
        //  mTextView.setAlpha(0);

        // Animate scale and translation to go from thumbnail to full size
        rootView.animate().setDuration(duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator);//.
        // withEndAction(new Runnable() {
        //  public void run() {
        // Animate the description in after the image animation
        // is done. Slide and fade the text in from underneath
        // the picture.
        // mTextView.setTranslationY(-mTextView.getHeight());
        //  mTextView.animate().setDuration(duration/2).
        //   translationY(0).alpha(1).
        //   setInterpolator(sDecelerator);
        //  }
        //   });

        // Fade in the black background
       ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
        bgAnim.setDuration(duration);
        bgAnim.start();


        // Animate a color filter to take the image from grayscale to full color.
        // This happens in parallel with the image scaling and moving into place.
     /*   ObjectAnimator colorizer = ObjectAnimator.ofFloat(PictureDetailsFragment.this,
                "saturation", 0, 1);
        colorizer.setDuration(duration);
        colorizer.start();

        // Animate a drop-shadow of the image
        ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(mShadowLayout, "shadowDepth", 0, 1);
        shadowAnim.setDuration(duration);
        shadowAnim.start();*/
    }


    /**
     * The exit animation is basically a reverse of the enter animation, except that if
     * the orientation has changed we simply scale the picture back into the center of
     * the screen.
     *
     * @param endAction This action gets run after the animation completes (this is
     *                  when we actually switch activities)
     */
    public void runExitAnimation(final Runnable endAction) {
        final long duration = (long) (ANIM_DURATION * sAnimatorScale);

        // No need to set initial values for the reverse animation; the image is at the
        // starting size/location that we want to start from. Just animate to the
        // thumbnail size/location that we retrieved earlier

        // Caveat: configuration change invalidates thumbnail positions; just animate
        // the scale around the center. Also, fade it out since it won't match up with
        // whatever's actually in the center
        final boolean fadeOut;
        if (getResources().getConfiguration().orientation != mOriginalOrientation) {
            rootView.setPivotX(rootView.getWidth() / 2);
            rootView.setPivotY(rootView.getHeight() / 2);
            mLeftDelta = 0;
            mTopDelta = 0;
            fadeOut = true;
        } else {
            fadeOut = false;
        }

        // First, slide/fade text out of the way
        rootView.animate().
                setDuration(duration / 2).setInterpolator(sAccelerator).
                scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                endAction.run();
            }
        });

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0);
        bgAnim.setDuration(duration);
        bgAnim.start();

        // Animate the shadow of the image
                      /*  ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(mShadowLayout,
                                "shadowDepth", 1, 0);
                        shadowAnim.setDuration(duration);
                        shadowAnim.start();

                        // Animate a color filter to take the image back to grayscale,
                        // in parallel with the image scaling and moving into place.
                        ObjectAnimator colorizer =
                                ObjectAnimator.ofFloat(PictureDetailsFragment.this,
                                        "saturation", 1, 0);
                        colorizer.setDuration(duration);
                        colorizer.start();
                    }
                });*/


    }




}

