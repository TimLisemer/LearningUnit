package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import learningunit.learningunit.R;

import static learningunit.learningunit.Objects.Organizer.Function.convertDpToPixel;

public class organizer_album extends AppCompatActivity {

    static Activity apli;
    static GridView galleryGridView;
    static ArrayList<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
    static String album_name = "";
    LoadAlbumImages loadAlbumTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_album);

        apli = this;
        Intent intent = getIntent();
        album_name = intent.getStringExtra("Album");
        setTitle(album_name);


        galleryGridView = (GridView) findViewById(R.id.galleryGridView);

        int iDisplayWidth = getResources().getDisplayMetrics().widthPixels ;
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        float dp = iDisplayWidth / (metrics.densityDpi / 160f);

        if(dp < 360)
        {
            dp = (dp - 17) / 2;
            float px = convertDpToPixel(dp, getApplicationContext());
            galleryGridView.setColumnWidth(Math.round(px));
        }


        loadAlbumTask = new LoadAlbumImages();
        loadAlbumTask.execute();

    }
}
