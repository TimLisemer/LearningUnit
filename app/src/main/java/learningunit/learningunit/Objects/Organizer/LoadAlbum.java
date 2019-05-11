package learningunit.learningunit.Objects.Organizer;

import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;

import java.util.Collections;




import static learningunit.learningunit.Objects.Organizer.Function.KEY_ALBUM;
import static learningunit.learningunit.Objects.Organizer.Function.KEY_TIMESTAMP;
import static learningunit.learningunit.Objects.Organizer.Function.converToTime;
import static learningunit.learningunit.Objects.Organizer.Function.getCount;
import static learningunit.learningunit.Objects.Organizer.Function.mappingInbox;

public class LoadAlbum extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        organizer_pictures.albumList.clear();
    }

    protected String doInBackground(String... args) {
        String xml = "";

        String path = null;
        String album = null;
        String timestamp = null;
        String countPhoto = null;
        Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };

        Cursor cursorExternal = organizer_album.apli.getContentResolver().query(uriExternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name", null, null);

        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal, cursorInternal});

        while (cursor.moveToNext()) {

            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
            countPhoto = getCount(organizer_album.apli.getApplicationContext(), album);

            organizer_pictures.albumList.add(mappingInbox(album, path, timestamp, converToTime(timestamp), countPhoto));
        }
        cursor.close();
        Collections.sort(organizer_pictures.albumList, new MapComparator(KEY_TIMESTAMP, "dsc")); // Arranging photo album by timestamp decending
        return xml;


        @Override
        protected void onPostExecute(String xml) {

            AlbumAdapter adapter = new AlbumAdapter(organizer_pictures.apli2, organizer_pictures.albumList);
            galleryGridView.setAdapter(adapter);
            galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    Intent intent = new Intent(organizer_pictures.apli2, organizer_album.class);
                    intent.putExtra("name", organizer_pictures.albumList.get(+position).get(KEY_ALBUM));
                    startActivity(intent);
                }
            });
        }

    }

}