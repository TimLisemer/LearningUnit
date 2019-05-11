package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import learningunit.learningunit.R;

import static learningunit.learningunit.Objects.Organizer.Function.KEY_ALBUM;
import static learningunit.learningunit.Objects.Organizer.Function.KEY_COUNT;
import static learningunit.learningunit.Objects.Organizer.Function.KEY_PATH;

public class AlbumAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap< String, String >> data;



    public AlbumAdapter(Activity a, ArrayList < HashMap < String, String >> d) {
        activity = a;
        data = d;
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumViewHolder holder = null;
        if (convertView == null) {
            holder = new AlbumViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.organizer_album_raw, parent, false);

            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.gallery_count = (TextView) convertView.findViewById(R.id.gallery_count);
            holder.gallery_title = (TextView) convertView.findViewById(R.id.gallery_title);

            convertView.setTag(holder);
        } else {
            holder = (AlbumViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.gallery_count.setId(position);
        holder.gallery_title.setId(position);

        HashMap < String, String > song = new HashMap < String, String > ();
        song = data.get(position);

        try {
            holder.gallery_title.setText(song.get(KEY_ALBUM));
            holder.gallery_count.setText(song.get(KEY_COUNT));

            Glide.with(activity)
                    .load(new File(song.get(KEY_PATH))) // Uri of the picture
                    .into(holder.galleryImage);


        } catch (Exception e) {}
        return convertView;
    }
}
