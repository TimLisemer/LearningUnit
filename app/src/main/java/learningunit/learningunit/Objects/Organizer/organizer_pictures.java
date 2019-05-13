package learningunit.learningunit.Objects.Organizer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.widget.Button;
import android.widget.ListView;

import learningunit.learningunit.R;
import learningunit.learningunit.menu.organizer.Organizer;


public class organizer_pictures extends AppCompatActivity {

    ArrayList<String> filepath = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_pictures);


        Button back = (Button) findViewById(R.id.organizer_picture_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(organizer_pictures.this, Organizer.class);
                startActivity(intent);
                HomeFragmentMethods.startCondition = 4;
            }
        });

        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File albumdir = new File (Environment.getExternalStorageDirectory() + File.separator + "LearningUnit" + File.separator + "Pictures");
            walkdir(albumdir);
        }

        if(filepath.size() > 0) {
            findViewById(R.id.organizer_picture_infolayout).setVisibility(View.GONE);
            findViewById(R.id.organizer_picture_viewlayout).setVisibility(View.VISIBLE);
            ListView simpleList = (ListView) findViewById(R.id.organizer_picture_listview);
            organizer_picture_CustomAdapter customAdapter = new organizer_picture_CustomAdapter(this, filepath);
            simpleList.setAdapter(customAdapter);
        }else{
            findViewById(R.id.organizer_picture_infolayout).setVisibility(View.VISIBLE);
            findViewById(R.id.organizer_picture_viewlayout).setVisibility(View.GONE);
        }


        Button newone = (Button) findViewById(R.id.organizer_picture_new);
        newone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(organizer_pictures.this, organizer_camera.class);
                startActivity(intent);
            }
        });

        Button create = (Button) findViewById(R.id.organizer_picture_infobutton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(organizer_pictures.this, organizer_camera.class);
                startActivity(intent);
            }
        });

    }

    public void walkdir(File albumdir) {
        File listFile[] = albumdir.listFiles();

        if (listFile != null){
            for (int i = 0; i < listFile.length; i++){
                if (listFile[i].isDirectory()) {
                    walkdir(listFile[i]);
                } else {
                    filepath.add(listFile[i].getAbsolutePath());
                }
            }
        }
    }

}
