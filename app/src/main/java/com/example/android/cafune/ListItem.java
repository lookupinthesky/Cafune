package com.example.android.cafune;

import android.app.LauncherActivity;
import android.graphics.Bitmap;

import java.util.List;

import static android.R.id.text1;
import static android.R.id.text2;

/**
 * Created by Shubham on 2/3/2017.
 */

public class ListItem {

    private int description;
    private int information;
    private int image;
    private int playOrPause;


    private List<ListItem> list;
    private Bitmap iconImage;
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;

    public ListItem(Bitmap iconImage, String field1, String field2, String field3, String field4, String field5){
        this.iconImage = iconImage;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
    }

    public ListItem(int text1,
                    int text2,
                    int songIcon,
                    int playOrPause) {
        this.description = text1;
        this.information = text2;
        this.image = songIcon;
        this.playOrPause = playOrPause;

    }

    public int getDescriptionResource(){
        return description;
    }

    public int getInformationResource(){
        return information;
    }

    public int getImageResource() {
        return image;
    }

    public int getPlayOrPause(){
        return playOrPause;
    }
}
