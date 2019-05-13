package learningunit.learningunit.Objects.Organizer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import learningunit.learningunit.R;

public class organizer_fullscreen_picture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_fullscreen_picture);
        ImageView view = (ImageView) findViewById(R.id.organizer_fullscreen_image);
        Bitmap myBitmap = BitmapFactory.decodeFile(EventMethods.fullscreen.getAbsolutePath());
        view.setImageBitmap(myBitmap);


        Button back = (Button) findViewById(R.id.organizer_fullscreen_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), organizer_pictures.class);
                startActivity(intent);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(organizer_fullscreen_picture.this);

                builder.setCancelable(true);
                builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton(R.string.DeleteImage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventMethods.fullscreen.delete();
                        Intent intent = new Intent(organizer_fullscreen_picture.this, organizer_pictures.class);
                        startActivity(intent);
                    }
                });

                builder.setTitle(R.string.DeleteImageTitle);

                builder.setMessage(R.string.DeleteImageQuestion);
                builder.show();

                return false;
            }
        });
    }
}
