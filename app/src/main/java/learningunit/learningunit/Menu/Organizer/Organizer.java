package learningunit.learningunit.Menu.Organizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.R;

public class Organizer extends AppCompatActivity {


    //Deklarieren der Knöpfe
    private Button back, homework, notes, formulary, pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        //Initialisieren der Knöpfe und rufen der OnClick methode
        back = (Button) findViewById(R.id.organizer_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back();
            }
        });

        homework = (Button) findViewById(R.id.organizer_homework);
        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_homework();
            }
        });

        notes = (Button) findViewById(R.id.organizer_notes);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_notes();
            }
        });

        formulary = (Button) findViewById(R.id.organizer_formulary);
        formulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_formulary();
            }
        });

        pictures = (Button) findViewById(R.id.organizer_pictures);
        pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_pictures();
            }
        });

    }



    //Buttton OnClick Methoden
    public void open_back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void open_homework(){
        //Intent intent = new Intent(this, Vok.class);
        //startActivity(intent);
    }

    public void open_notes(){
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }

    public void open_formulary(){
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }

    public void open_pictures(){
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }
}
