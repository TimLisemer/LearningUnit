package learningunit.learningunit.menu.learn.formular;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

import learningunit.learningunit.Objects.Learn.Formular.Formular;
import learningunit.learningunit.Objects.Learn.Formular.FormularList;
import learningunit.learningunit.Objects.Learn.Formular.FormularMethods;
import learningunit.learningunit.Objects.PublicAPIs.AnalyticsApplication;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.ReadCsvVocList;
import learningunit.learningunit.R;

public class CreateFormList extends AppCompatActivity{
    private EditText Formular, formular1, listtext, languagetext, languagetext1;
    private TextView error1, error, Cancel1;
    private String language, language1;

    private EditText listtext1, languagetext11, languagetext21;
    private TextView error11;

    FormularList list;
    private static Context context;


    //Deklarieren der Knöpfe ( scrollview )
    private Button back, continue0;

    //Deklarieren der Knöpfe ( scrollview1 )
    private Button back1, next, finish;

    //Deklarieren der Knöpfe ( scrollview2 )
    private Button cancel, return0;

    //Deklarieren der Knöpfe ( scrollview3 )
    private Button finish1, return1;

    //Deklarieren der Knöpfe ( scrollview4 )
    private Button back11, continue1, continue11;

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

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voc_list);
        context = getApplicationContext();

        Formular = (EditText) findViewById(R.id.CreateFormList_formular);
        formular1 = (EditText) findViewById(R.id.CreateFormList_formular1);

        error1 = findViewById(R.id.CreateFormList_error1);
        error = findViewById(R.id.CreateFormList_error);
        Cancel1 = findViewById(R.id.CreateFormList_Cancel1);

        listtext = findViewById(R.id.CreateFormList_name_list);
        languagetext = findViewById(R.id.CreateFormList_name_language1);
        languagetext1 = findViewById(R.id.CreateFormList_name_language2);

        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview )
        back = (Button) findViewById(R.id.CreateFormList_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back();
            }
        });

        continue0 = (Button) findViewById(R.id.CreateFormList_continue);
        continue0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_continue0();
            }
        });



        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview1 )
        back1 = (Button) findViewById(R.id.CreateFormList_back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back1();
            }
        });

        next = (Button) findViewById(R.id.CreateFormList_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_next();
            }
        });

        finish = (Button) findViewById(R.id.CreateFormList_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_finish();
            }
        });



        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview2 )
        cancel = (Button) findViewById(R.id.CreateFormList_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_cancel();
            }
        });

        return0 = (Button) findViewById(R.id.CreateFormList_return);
        return0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_return();
            }
        });



        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview3 )
        finish1 = (Button) findViewById(R.id.CreateFormList_finish1);
        finish1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_finish1();
            }
        });

        return1 = (Button) findViewById(R.id.CreateFormList_return1);
        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_return1();
            }
        });

        //Initialisieren der Knöpfe und rufen der OnClick methode ( scrollview4 )
        listtext1 = (EditText) findViewById(R.id.CreateFormList_name_list1);
        languagetext11 = (EditText) findViewById(R.id.CreateFormList_name_language11);
        languagetext21 = (EditText) findViewById(R.id.CreateFormList_name_language21);
        error11 = (TextView) findViewById(R.id.CreateFormList_error11);
        back11 = (Button) findViewById(R.id.CreateFormList_back11);
        back11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_back();
            }
        });

        continue1 = (Button) findViewById(R.id.CreateFormList_continue1);
        continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        continue11 = (Button) findViewById(R.id.CreateFormList_continue11);
        continue11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContinue();
            }
        });


        openCreateList();
    }


    private void openCreateList(){
        if(FormularMethods.openCreateList == true){
            findViewById(R.id.CreateFormList_scrollview).setVisibility(View.VISIBLE);
            findViewById(R.id.CreateFormList_scrollview4).setVisibility(View.GONE);
        }else{
            findViewById(R.id.CreateFormList_scrollview4).setVisibility(View.VISIBLE);
            findViewById(R.id.CreateFormList_scrollview).setVisibility(View.GONE);
        }
    }


    //Buttton OnClick Methoden ( scrollview )
    public void open_back(){
        Intent intent = new Intent(this, Formeln.class);
        startActivity(intent);
    }

    public void open_continue0(){

        language = languagetext.getText().toString();
        language1 = languagetext1.getText().toString();

        if (listtext.length() <= 0){
            error.setText("Bitte gebe einen Namen für die Formelliste an.");
        }else if (languagetext.length() <= 0){
            error.setText("Bitte gebe einen Namen von Sprache 1 an.");
        }else if (languagetext1.length() <= 0){
            error.setText("Bitte gebe einen Namen von Sprache 2 an.");
        }else{
            if (!(languagetext.getText().toString().trim().equalsIgnoreCase(languagetext1.getText().toString().trim()))) {
                if(FormularMethods.nameavailable(listtext.getText().toString()) == true){
                    list = new FormularList(language, language1, listtext.getText().toString(), false, false);
                    FormularMethods.saveFormularList(list);
                    Formular.setHint("Original (" + language + ")");
                    formular1.setHint("Übersetzung (" + language1 + ")");

                    error.setText("");
                    listtext.setText("");
                    languagetext.setText("");
                    languagetext1.setText("");
                    findViewById(R.id.CreateFormList_scrollview).setVisibility(View.INVISIBLE);
                    findViewById(R.id.CreateFormList_scrollview1).setVisibility(View.VISIBLE);
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
        Formular.setText("");
        formular1.setText("");
        Formular.setHint("Original (" + language + ")");
        formular1.setHint("Original (" + language1 + ")");
        MainActivity.hideKeyboard(CreateFormList.this);
        if(list.getFormularlist().size() == 0) {
            Cancel1.setText("Wollen sie wirklich abbrechen? Es gehen dadurch KEINE Formeln verloren.");
        }else{
            Cancel1.setText("Wollen sie wirklich abbrechen? Dadurch gehen die letzten " + list.getFormularlist().size() + " eingegebeben Formeln verloren.");
        }
        findViewById(R.id.CreateFormList_scrollview1).setVisibility(View.INVISIBLE);
        findViewById(R.id.CreateFormList_scrollview2).setVisibility(View.VISIBLE);
    }

    public void open_next(){
        if (Formular.length() <= 0){
            error1.setText("Bitte gebe die Formel auf " + language + " an.");
        }else if (formular1.length() <= 0){
            error1.setText("Bitte gebe die Formel auf " + language1 + " an.");
        }else{
            Formular form = new Formular(Formular.getText().toString(), formular1.getText().toString(), language, language1, false);
            list.addFormular(form);
            Formular.setText("");
            formular1.setText("");
            Formular.setHint("Original (" + language + ")");
            formular1.setHint("Übersetzung (" + language1 + ")");

            Formular.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(Formular, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void open_finish(){

        if (!(Formular.length() <= 0) && (!(formular1.length() <= 0))){
            if (Formular.length() <= 0){
                error1.setText("Bitte gebe die Formel auf " + language + " an.");
                Formular.setText("");
                formular1.setText("");
                Formular.setHint("Original (" + language + ")");
                formular1.setHint("Übersetzung (" + language1 + ")");
            }else if (formular1.length() <= 0){
                error1.setText("Bitte gebe die Formel auf " + language1 + " an.");
                Formular.setText("");
                formular1.setText("");
                Formular.setHint("Original (" + language + ")");
                formular1.setHint("Übersetzung (" + language1 + ")");
            }else {
                MainActivity.hideKeyboard(this);
                Formular form = new Formular(Formular.getText().toString(), formular1.getText().toString(), language, language1, false);
                list.addFormular(form);

                findViewById(R.id.CreateFormList_scrollview1).setVisibility(View.INVISIBLE);
                findViewById(R.id.CreateFormList_scrollview3).setVisibility(View.VISIBLE);
            }
        }else{
            MainActivity.hideKeyboard(this);
            if(list.getFormularlist().size() == 0) {
                Cancel1.setText("Sie haben noch keine Formel eingegeben, wollen sie abbrechen?");
                findViewById(R.id.CreateFormList_scrollview1).setVisibility(View.INVISIBLE);
                findViewById(R.id.CreateFormList_scrollview2).setVisibility(View.VISIBLE);
            }else{
                findViewById(R.id.CreateFormList_scrollview1).setVisibility(View.INVISIBLE);
                findViewById(R.id.CreateFormList_scrollview3).setVisibility(View.VISIBLE);
            }

        }
    }



    //Buttton OnClick Methoden ( scrollview2 )
    public void open_cancel(){
        FormularMethods.removeFormularList(list);
        Intent intent = new Intent(this, Formeln.class);
        startActivity(intent);
    }

    public void open_return(){
        MainActivity.showKeyboard(this);
        findViewById(R.id.CreateFormList_scrollview2).setVisibility(View.INVISIBLE);
        findViewById(R.id.CreateFormList_scrollview1).setVisibility(View.VISIBLE);
    }


    //Buttton OnClick Methoden ( scrollview2 )
    public void open_finish1(){
        FormularMethods.saveFormularList(list);
        ManageData.uploadFormularList(list, context);
        Intent intent = new Intent(this, Formeln.class);
        startActivity(intent);
    }

    public void open_return1(){
        MainActivity.showKeyboard(this);
        findViewById(R.id.CreateFormList_scrollview3).setVisibility(View.INVISIBLE);
        findViewById(R.id.CreateFormList_scrollview1).setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed(){
        if(findViewById(R.id.CreateFormList_scrollview).getVisibility() == View.VISIBLE){
            open_back();
        }else if(findViewById(R.id.CreateFormList_scrollview1).getVisibility() == View.VISIBLE){
            open_back1();
        }else if(findViewById(R.id.CreateFormList_scrollview2).getVisibility() == View.VISIBLE){
            open_return();
        }else if(findViewById(R.id.CreateFormList_scrollview3).getVisibility() == View.VISIBLE){
            open_return1();
        }else if(findViewById(R.id.CreateFormList_scrollview3).getVisibility() == View.VISIBLE){
            open_return1();
        }else if(findViewById(R.id.CreateFormList_scrollview4).getVisibility() == View.VISIBLE){
            open_back();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void openContinue(){

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }else {
            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(1000)
                    .withFilter(Pattern.compile(".*\\.csv$")) // Filtering files and directories by file name using regexp
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1001: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Zugriff gestattet!", Toast.LENGTH_SHORT).show();
                    openContinue();
                }else{
                    Toast.makeText(this, "Zugriff verwehrt!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == RESULT_OK){
            try {
                String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                error11.setText(filePath);
                File csv = new File(filePath);
                continue1.setEnabled(true);
                final InputStream is = new FileInputStream(csv);
                continue1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openContinue1(is);
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void openContinue1(InputStream is){
        if(!(listtext1.getText().toString().trim().matches(""))) {
            if(FormularMethods.nameavailable(listtext1.getText().toString().trim())) {
                if(!(languagetext11.getText().toString().trim().matches(""))) {
                    if(!(languagetext21.getText().toString().trim().matches(""))) {
                        if(!(languagetext11.getText().toString().trim().equalsIgnoreCase(languagetext21.getText().toString().trim()))){
                            ReadCsvVocList rl = new ReadCsvVocList();
                            rl.ReadList(CreateFormList.this, is, languagetext11.getText().toString().trim(), languagetext21.getText().toString().trim(), listtext1.getText().toString().trim());
                            Intent intent = new Intent(this, Formeln.class);
                            startActivity(intent);
                        }else{
                            languagetext21.setError("Identisch zu Sprache 1");
                        }
                    }else{
                        languagetext21.setError("Bitte gebe einen Namen für diese Sprache ein");
                    }
                }else{
                    languagetext11.setError("Bitte gebe einen Namen für diese Sprache ein");
                }
            }else{
                listtext1.setError("Es existiert berreits eine Formelliste mit diesem Namen");
            }
        }else{
            listtext1.setError("Bitte gebe einen Namen für die Formelliste ein");
        }
    }








































}
