package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;

public class organizer_picture_CustomAdapter extends BaseAdapter{

    Activity activity;
    ArrayList<String> filepath;

    public organizer_picture_CustomAdapter(Activity activity, ArrayList<String> filepath){
        this.filepath = filepath;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return filepath.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(activity).inflate(R.layout.organizer_picture_listview, null);
        Matrix matrix = new Matrix();
        ImageView GalleryPreviewImg = (ImageView) convertView.findViewById(R.id.organizer_picture_listview_imageview);
        final File file = new File(filepath.get(position));
        if(file.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            GalleryPreviewImg.setImageBitmap(myBitmap);
        }

        GalleryPreviewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventMethods.fullscreen = file;
                Intent intent = new Intent(activity, organizer_fullscreen_picture.class);
                activity.startActivity(intent);
            }
        });

        GalleryPreviewImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

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
                        file.delete();
                        Intent intent = new Intent(activity, organizer_pictures.class);
                        activity.startActivity(intent);
                    }
                });

                builder.setTitle(R.string.DeleteImageTitle);

                builder.setMessage(R.string.DeleteImageQuestion);
                builder.show();

                return false;
            }
        });


        return convertView;
    }




}
