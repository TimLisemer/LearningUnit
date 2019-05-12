package learningunit.learningunit.Objects.Organizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.graphics.Matrix;
import java.io.File;
import learningunit.learningunit.R;



public class GalleryPreview extends AppCompatActivity {

    ImageView GalleryPreviewImg;
    Matrix matrix = new Matrix();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_gallery_preview);
        Intent intent = getIntent();
        GalleryPreviewImg = (ImageView) findViewById(R.id.GalleryPreviewImg);

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "LearningUnit" + File.separator + "Pictures" + File.separator + "5011b67a-a468-434c-b77e-b5f22de7ba16.jpg");

        if(file.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            GalleryPreviewImg.setImageBitmap(myBitmap);
        }

        GalleryPreviewImg.setScaleType(ImageView.ScaleType.MATRIX);
        matrix.postRotate((float) 90, 230, 250);
        GalleryPreviewImg.setImageMatrix(matrix);


        }

    }








