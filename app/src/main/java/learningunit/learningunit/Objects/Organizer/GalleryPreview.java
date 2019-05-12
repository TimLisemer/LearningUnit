package learningunit.learningunit.Objects.Organizer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import learningunit.learningunit.R;

public class GalleryPreview extends AppCompatActivity {

    ImageView GalleryPreviewImg;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.organizer_gallery_preview);
        Intent intent = getIntent();
        path = intent.getStringExtra(Environment.getExternalStorageDirectory() + File.separator + "LearningUnit" + File.separator + "Pictures" + File.separator + "5011b67a-a468-434c-b77e-b5f22de7ba16.jpg");
        GalleryPreviewImg = (ImageView) findViewById(R.id.GalleryPreviewImg);
        Glide.with(GalleryPreview.this)
                .load(new File(path)) // Uri of the picture
                .into(GalleryPreviewImg);
    }




}
