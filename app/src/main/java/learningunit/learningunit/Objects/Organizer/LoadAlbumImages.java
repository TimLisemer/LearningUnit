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
import java.util.function.Function;

import static learningunit.learningunit.Objects.Organizer.Function.KEY_PATH;
import static learningunit.learningunit.Objects.Organizer.Function.KEY_TIMESTAMP;
import static learningunit.learningunit.Objects.Organizer.Function.converToTime;
import static learningunit.learningunit.Objects.Organizer.Function.mappingInbox;

public class LoadAlbumImages extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        organizer_album.imageList.clear();
    }

    protected String doInBackground(String... args) {
        String xml = "";

        String path = null;
        String album = null;
        String timestamp = null;
        Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };

        Cursor cursorExternal = organizer_album.apli.getContentResolver().query(uriExternal, projection, "bucket_display_name = \""+organizer_album.album_name+"\"", null, null);
        Cursor cursorInternal = organizer_album.apli.getContentResolver().query(uriInternal, projection, "bucket_display_name = \""+organizer_album.album_name+"\"", null, null);
        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});


        while (cursor.moveToNext()) {

            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));

            organizer_album.imageList.add(mappingInbox(album, path, timestamp, converToTime(timestamp), null));
        }
        cursor.close();
        Collections.sort(organizer_album.imageList, new MapComparator(KEY_TIMESTAMP, "dsc")); // Arranging photo album by timestamp decending
        return xml;
    }

    @Override
    protected void onPostExecute(String xml) {

        SingleAlbumAdapter adapter = new SingleAlbumAdapter(organizer_album.apli, organizer_album.imageList);
        galleryGridView.setAdapter(adapter);
        galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                Intent intent = new Intent(organizer_album.apli, GalleryPreview.class);
                intent.putExtra("path", organizer_album.imageList.get(+position).get(KEY_PATH));
                startActivity(intent);
            }
        });
    }
}








