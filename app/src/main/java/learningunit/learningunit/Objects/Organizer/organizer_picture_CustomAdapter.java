package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
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
        File file = new File(filepath.get(position));
        if(file.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            GalleryPreviewImg.setImageBitmap(myBitmap);
        }


        return convertView;
    }




}
