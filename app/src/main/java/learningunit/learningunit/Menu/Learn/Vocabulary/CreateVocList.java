package learningunit.learningunit.Menu.Learn.Vocabulary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.Vocabulary;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyList;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;
import learningunit.learningunit.R;

public class CreateVocList extends AppCompatActivity{
    private EditText vocabulary, vocabulary1, listtext, languagetext, languagetext1;
    private TextView error1, error, Cancel1;
    private String language, language1;

    VocabularyList list;
    private static Context context;


    //Deklarieren der Knöpfe ( scrollview )
    private Button back, continue0;

    //Deklarieren der Knöpfe ( scrollview1 )
    private Button back1, next, finish;

    //Deklarieren der Knöpfe ( scrollview2 )
    private Button cancel, return0;

    //Deklarieren der Knöpfe ( scrollview3 )
    private Button finish1, return1;

    @Override
    public void onPause() {
        super.onPause();
        MainActivity.onAppPause(context);
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity.onAppShutdown(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voc_list);
        context = getApplicationContext();

        vocabulary = (EditText) findViewById(R.id.CreateVocList_vocabulary);
        vocabulary1 = (EditText) findViewById(R.id.CreateVocList_vocabulary1);

        error1 = findViewById(R.id.CreateVocList_error1);
        error = findViewById(R.id.CreateVocList_error);
        Cancel1 = findViewById(R.id.CreateVocList_Cancel1);

        listtext = findViewById(R.id.CreateVocList_name_list);
        languagetext = findViewById(R.id.CreateVocList_name_language1);
        languagetext1 = findViewById(R.id.CreateVocList_name_language2);

        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview )
        back = (Button) findViewById(R.id.CreateVocList_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back();
            }
        });

        continue0 = (Button) findViewById(R.id.CreateVocList_continue);
        continue0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_continue0();
            }
        });



        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview1 )
        back1 = (Button) findViewById(R.id.CreateVocList_back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back1();
            }
        });

        next = (Button) findViewById(R.id.CreateVocList_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_next();
            }
        });

        finish = (Button) findViewById(R.id.CreateVocList_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_finish();
            }
        });



        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview2 )
        cancel = (Button) findViewById(R.id.CreateVocList_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_cancel();
            }
        });

        return0 = (Button) findViewById(R.id.CreateVocList_return);
        return0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_return();
            }
        });



        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview3 )
        finish1 = (Button) findViewById(R.id.CreateVocList_finish1);
        finish1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_finish1();
            }
        });

        return1 = (Button) findViewById(R.id.CreateVocList_return1);
        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_return1();
            }
        });

    }




    //Buttton OnClick Methoden ( scrollview )
    public void open_back(){
        Intent intent = new Intent(this, Vokabeln.class);
        startActivity(intent);
    }

    public void open_continue0(){

        language = languagetext.getText().toString();
        language1 = languagetext1.getText().toString();

        if (listtext.length() <= 0){
            error.setText("Bitte gebe einen Namen für die Vokabelliste an.");
        }else if (languagetext.length() <= 0){
            error.setText("Bitte gebe einen Namen von Sprache 1 an.");
        }else if (languagetext1.length() <= 0){
            error.setText("Bitte gebe einen Namen von Sprache 2 an.");
        }else{
            if (!(languagetext.getText().toString().trim().equalsIgnoreCase(languagetext1.getText().toString().trim()))) {
                if(VocabularyMethods.nameavailable(listtext.getText().toString()) == true){
                    list = new VocabularyList(language, language1, listtext.getText().toString(), false, false);
                    VocabularyMethods.saveVocabularyList(list);
                    vocabulary.setHint("Original (" + language + ")");
                    vocabulary1.setHint("Übersetzung (" + language1 + ")");

                    error.setText("");
                    listtext.setText("");
                    languagetext.setText("");
                    languagetext1.setText("");
                    findViewById(R.id.CreateVocList_scrollview).setVisibility(View.INVISIBLE);
                    findViewById(R.id.CreateVocList_scrollview1).setVisibility(View.VISIBLE);
                }else {
                    error.setText("Es existiert bereits eine Liste mit diesem Namen");
                }
            }else{
                error.setText("Sprachen identisch");
            }
        }
    }



    //Buttton OnClick Methoden ( scrollview1 )

    public void open_back1(){

        error1.setText("");
        vocabulary.setText("");
        vocabulary1.setText("");
        vocabulary.setHint("Original (" + language + ")");
        vocabulary1.setHint("Original (" + language1 + ")");
        MainActivity.hideKeyboard(this);
        if(list.getVocabularylist().size() == 0) {
            Cancel1.setText("Wollen sie wirklich abbrechen? Es gehen dadurch KEINE Vokabeln verloren.");
        }else{
            Cancel1.setText("Wollen sie wirklich abbrechen? Dadurch gehen die letzten " + list.getVocabularylist().size() + " eingegebeben Vokabeln verloren.");
        }
        findViewById(R.id.CreateVocList_scrollview1).setVisibility(View.INVISIBLE);
        findViewById(R.id.CreateVocList_scrollview2).setVisibility(View.VISIBLE);
    }

    public void open_next(){
        if (vocabulary.length() <= 0){
            error1.setText("Bitte gebe die Vokabel auf " + language + " an.");
        }else if (vocabulary1.length() <= 0){
            error1.setText("Bitte gebe die Vokabel auf " + language1 + " an.");
        }else{
            Vocabulary voc = new Vocabulary(vocabulary.getText().toString(), vocabulary1.getText().toString(), language, language1, false);
            list.addVocabulary(voc);
            vocabulary.setText("");
            vocabulary1.setText("");
            vocabulary.setHint("Original (" + language + ")");
            vocabulary1.setHint("Übersetzung (" + language1 + ")");

            vocabulary.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(vocabulary, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void open_finish(){

        if (!(vocabulary.length() <= 0) && (!(vocabulary1.length() <= 0))){
            if (vocabulary.length() <= 0){
                error1.setText("Bitte gebe die Vokabel auf " + language + " an.");
                vocabulary.setText("");
                vocabulary1.setText("");
                vocabulary.setHint("Original (" + language + ")");
                vocabulary1.setHint("Übersetzung (" + language1 + ")");
            }else if (vocabulary1.length() <= 0){
                error1.setText("Bitte gebe die Vokabel auf " + language1 + " an.");
                vocabulary.setText("");
                vocabulary1.setText("");
                vocabulary.setHint("Original (" + language + ")");
                vocabulary1.setHint("Übersetzung (" + language1 + ")");
            }else {
                MainActivity.hideKeyboard(this);
                Vocabulary voc = new Vocabulary(vocabulary.getText().toString(), vocabulary1.getText().toString(), language, language1, false);
                list.addVocabulary(voc);

                findViewById(R.id.CreateVocList_scrollview1).setVisibility(View.INVISIBLE);
                findViewById(R.id.CreateVocList_scrollview3).setVisibility(View.VISIBLE);
            }
        }else{
            MainActivity.hideKeyboard(this);
            if(list.getVocabularylist().size() == 0) {
                Cancel1.setText("Sie haben noch keine Vokabel eingegeben, wollen sie abbrechen?");
                findViewById(R.id.CreateVocList_scrollview1).setVisibility(View.INVISIBLE);
                findViewById(R.id.CreateVocList_scrollview2).setVisibility(View.VISIBLE);
            }else{
                findViewById(R.id.CreateVocList_scrollview1).setVisibility(View.INVISIBLE);
                findViewById(R.id.CreateVocList_scrollview3).setVisibility(View.VISIBLE);
            }

        }
    }



    //Buttton OnClick Methoden ( scrollview2 )
    public void open_cancel(){
        VocabularyMethods.removeVocabularyList(list);
        Intent intent = new Intent(this, Vokabeln.class);
        startActivity(intent);
    }

    public void open_return(){
        MainActivity.showKeyboard(this);
        findViewById(R.id.CreateVocList_scrollview2).setVisibility(View.INVISIBLE);
        findViewById(R.id.CreateVocList_scrollview1).setVisibility(View.VISIBLE);
    }


    //Buttton OnClick Methoden ( scrollview2 )
    public void open_finish1(){
        VocabularyMethods.saveVocabularyList(list);
        ManageData.uploadVocabularyList(list, context);
        Intent intent = new Intent(this, Vokabeln.class);
        startActivity(intent);
    }

    public void open_return1(){
        MainActivity.showKeyboard(this);
        findViewById(R.id.CreateVocList_scrollview3).setVisibility(View.INVISIBLE);
        findViewById(R.id.CreateVocList_scrollview1).setVisibility(View.VISIBLE);
    }





































}
