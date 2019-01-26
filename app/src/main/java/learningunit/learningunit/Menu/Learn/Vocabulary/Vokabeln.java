package learningunit.learningunit.Menu.Learn.Vocabulary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import learningunit.learningunit.BeforeStart.Register;
import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.API.RequestHandler;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.Vocabulary;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyList;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;
import learningunit.learningunit.R;


public class Vokabeln extends AppCompatActivity {

    private Button back, back1, create, base, train, all;
    private TextView lang1, lang2, original, translation, error, nolist;
    ConstraintLayout layout, layout1;
    ConstraintSet constraintSet, constraintSeto, constraintSett;

    private ArrayList<Vocabulary> vocabularylist;
    private ArrayList<Button> buttonlist = new ArrayList<Button>();
    private ArrayList<Button> buttonlanglist = new ArrayList<Button>();
    private TextView downoriginal[];
    private TextView downtranslation[];
    private Context context;

    //Lernbereich

    private Button learn_back, learn_enter, learn_showtranslation, learn_right, learn_wrong;

    private TextView learn_level0, learn_level1, learn_level2, learn_level3, learn_level4,
            learn_level0Score, learn_level1Score, learn_level2Score, learn_level3Score, learn_level4Score,
            learn_language, learn_original, learn_language1, learn_languages, learn_vocabularys, learn_masterTranslation;

    private EditText learn_translation;

    private TextInputLayout learn_input;

    //Lernbereich

    //Shared
    private Button shared, shared_back;
    //Shared

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        context = getApplicationContext();
        //Shared
        shared = (Button) findViewById(R.id.vocabulary_shared);
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShared();
            }
        });
        shared_back = (Button) findViewById(R.id.vocabulary_ShareBack);
        shared_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = (SharedButtonList0.length + SharedButtonList1.length + SharedButtonList2.length + SharedButtonList3.length) / 4;
                for(int i = 1; i < size; i++){
                    ShareLayout.removeView(SharedButtonList0[i]);
                    ShareLayout.removeView(SharedButtonList1[i]);
                    ShareLayout.removeView(SharedButtonList2[i]);
                    ShareLayout.removeView(SharedButtonList3[i]);
                }
                findViewById(R.id.vocabulary_scrollView).setVisibility(View.VISIBLE);
                findViewById(R.id.vocabulary_ShareMainView).setVisibility(View.GONE);
            }
        });

        ShareScrollBase0 = (Button) findViewById(R.id.vocabulary_ShareScrollBase0);
        ShareScrollBase1 = (Button) findViewById(R.id.vocabulary_ShareScrollBase1);
        ShareScrollBase2 = (Button) findViewById(R.id.vocabulary_ShareScrollBase2);
        ShareScrollBase3 = (Button) findViewById(R.id.vocabulary_ShareScrollBase3);

        ShareGetList = (EditText) findViewById(R.id.vocabulary_ShareGetList);
        ShareLayout = (ConstraintLayout) findViewById(R.id.vocabulary_ShareScrollMainLayout);
        ShareInfo = (TextView) findViewById(R.id.vocabulary_shareInfo);

        //Shared


        //Lernbereich

        learn_back = (Button) findViewById(R.id.vocabulary_learn_back);
        learn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_learn_back();
            }
        });

        learn_enter = (Button) findViewById(R.id.vocabulary_learn_enter);

        learn_showtranslation = (Button) findViewById(R.id.vocabulary_learn_showTranslation);
        learn_right = (Button) findViewById(R.id.vocabulary_learn_right);
        learn_wrong = (Button) findViewById(R.id.vocabulary_learn_wrong);



        learn_level0 = (TextView) findViewById(R.id.vocabulary_learn_level0);
        learn_level1 = (TextView) findViewById(R.id.vocabulary_learn_level1);
        learn_level2 = (TextView) findViewById(R.id.vocabulary_learn_level2);
        learn_level3 = (TextView) findViewById(R.id.vocabulary_learn_level3);
        learn_level4 = (TextView) findViewById(R.id.vocabulary_learn_level4);


        learn_level0Score = (TextView) findViewById(R.id.vocabulary_learn_level0count);
        learn_level1Score = (TextView) findViewById(R.id.vocabulary_learn_level1count);
        learn_level2Score = (TextView) findViewById(R.id.vocabulary_learn_level2count);
        learn_level3Score = (TextView) findViewById(R.id.vocabulary_learn_level3count);
        learn_level4Score = (TextView) findViewById(R.id.vocabulary_learn_level4count);

        learn_language = (TextView) findViewById(R.id.vocabulary_learn_language);
        learn_original = (TextView) findViewById(R.id.vocabulary_learn_original);
        learn_language1 = (TextView) findViewById(R.id.vocabulary_learn_language1);
        learn_languages = (TextView) findViewById(R.id.vocabulary_learn_languages);
        learn_vocabularys = (TextView) findViewById(R.id.vocabulary_learn_vocabularys);

        learn_masterTranslation = (TextView) findViewById(R.id.vocabulary_learn_masterTranslation);

        learn_input = (TextInputLayout) findViewById(R.id.vocabulary_learn_inputlayout);
        learn_translation = (EditText) findViewById(R.id.vocabulary_learn_editText);

        learn_translation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inhalt = learn_translation.getText().toString().trim();
                learn_enter.setEnabled(!inhalt.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Lernbereich


        layout = (ConstraintLayout) findViewById(R.id.vocabulary_scrollview4);
        layout1 = (ConstraintLayout) findViewById(R.id.vocabulary_constraintlayout1);
        ManageData.loadVocabularyLists(context);


        //Initialisieren der Knöpfe und rufen der OnClick methode

        base = (Button) findViewById(R.id.vocabulary_base);
        nolist = (TextView) findViewById(R.id.vocabulary_nolists);
        lang1 = (TextView) findViewById(R.id.vocabulary_language);
        lang2 = (TextView) findViewById(R.id.vocabulary_language1);
        original = (TextView) findViewById(R.id.vocabulary_original);
        translation = (TextView) findViewById(R.id.vocabulary_translation);

        all = (Button) findViewById(R.id.vocabulary_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_all();
            }
        });

        back1 = (Button) findViewById(R.id.vocabulary_back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back1();
            }
        });

        train = (Button) findViewById(R.id.vocabulary_train);

        back = (Button) findViewById(R.id.vocabulary_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back();
            }
        });

        create = (Button) findViewById(R.id.vocabulary_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_create();
            }
        });

        error = (TextView) findViewById(R.id.vocabulary_error);

        ShowLists();

        for (int i = 0; i < buttonlanglist.size(); i++){
            Button b = buttonlanglist.get(i);
            b.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ManageData.saveVocabularyLists();
    }

    @Override
    public void onStop() {
        super.onStop();
        ManageData.saveVocabularyLists();
    }

    public void ShowLists(){
        int d = 0;
        int e = 0;
        boolean f = true;

        if(VocabularyMethods.vocabularylists != null) {
            nolist.setVisibility(View.GONE);
            all.setEnabled(true);
            base.setVisibility(View.INVISIBLE);
            Button down[] = new Button[VocabularyMethods.vocabularylists.size()];
            learn_languages.setText("Sprache - Sprache");
            learn_vocabularys.setText("Original - Übersetzung");

            for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
                if (!(VocabularyMethods.vocabularylists.get(i).getName().contains("AllVoc_"))) {
                    if (!(base.getVisibility() == View.INVISIBLE)) {
                        down[i] = new Button(Vokabeln.this);

                        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        params.setMargins(8, 90, 8, 8);

                        down[i].setText(VocabularyMethods.vocabularylists.get(i).getName());
                        down[i].setId(i);


                        Log.d("i = " + i, "added button with name " + VocabularyMethods.vocabularylists.get(i).getName() + " to view");
                        layout.addView(down[i], params);


                        constraintSet = new ConstraintSet();
                        constraintSet.clone(layout);
                        if (f == true) {
                            constraintSet.connect(i, ConstraintSet.TOP, base.getId(), ConstraintSet.BOTTOM);

                            constraintSet.connect(i, ConstraintSet.RIGHT, base.getId(), ConstraintSet.RIGHT);
                            constraintSet.connect(i, ConstraintSet.LEFT, base.getId(), ConstraintSet.LEFT);
                            f = false;
                        } else {
                            constraintSet.connect(i, ConstraintSet.TOP, e - 1, ConstraintSet.BOTTOM);
                            constraintSet.connect(i, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline3).getId(), ConstraintSet.LEFT);
                            constraintSet.connect(i, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline4).getId(), ConstraintSet.RIGHT);
                        }

                        e = i + 1;
                        constraintSet.applyTo(layout);
                        openshowvocabularys(down[i], i);
                        buttonlist.add(down[i]);
                    } else {
                        base.setVisibility(View.VISIBLE);
                        base.setText(VocabularyMethods.vocabularylists.get(i).getName());
                        openshowvocabularys(base, i);
                        buttonlist.add(base);
                    }
                    d++;
                }else{
                    if (!(base.getVisibility() == View.INVISIBLE)) {
                        down[i] = new Button(Vokabeln.this);

                        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        params.setMargins(8, 90, 8, 8);

                        down[i].setText(VocabularyMethods.vocabularylists.get(i).getLanguageName1() + " - " + VocabularyMethods.vocabularylists.get(i).getLanguageName2());
                        down[i].setId(i);


                        Log.d("i = " + i, "added button with name " + VocabularyMethods.vocabularylists.get(i).getName() + " to view");
                        layout.addView(down[i], params);


                        constraintSet = new ConstraintSet();
                        constraintSet.clone(layout);
                        if (f == true) {
                            constraintSet.connect(i, ConstraintSet.TOP, base.getId(), ConstraintSet.BOTTOM);

                            constraintSet.connect(i, ConstraintSet.RIGHT, base.getId(), ConstraintSet.RIGHT);
                            constraintSet.connect(i, ConstraintSet.LEFT, base.getId(), ConstraintSet.LEFT);
                            f = false;
                        } else {
                            constraintSet.connect(i, ConstraintSet.TOP, e - 1, ConstraintSet.BOTTOM);
                            constraintSet.connect(i, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline3).getId(), ConstraintSet.LEFT);
                            constraintSet.connect(i, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline4).getId(), ConstraintSet.RIGHT);
                        }

                        e = i + 1;
                        constraintSet.applyTo(layout);
                        openshowvocabularys(down[i], i);
                        buttonlanglist.add(down[i]);
                    } else {
                        base.setVisibility(View.VISIBLE);
                        base.setText(VocabularyMethods.vocabularylists.get(i).getLanguageName1() + " - " + VocabularyMethods.vocabularylists.get(i).getLanguageName2());
                        openshowvocabularys(base, i);
                        buttonlanglist.add(base);
                    }
                    d++;
                }
            }
        }else{
            nolist.setVisibility(View.VISIBLE);
            base.setVisibility(View.INVISIBLE);
            all.setEnabled(false);
        }
    }

    //Buttton OnClick Methoden

    public void openshowvocabularys(Button b, final int id){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showvocabularys(id);
            }
        });
    }

    public void showvocabularys(final int id){
        VocabularyList vocabularyList;
        if(publiclist == false || sharedlist == null) {
            train.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start_train(id);
                }
            });
            vocabularyList = VocabularyMethods.vocabularylists.get(id);
            if(publiclist == false) {
                ShareInfo.setVisibility(View.GONE);
            }else{

                LinkedHashMap<String, String> params = new LinkedHashMap <>();
                params.put("id", ManageData.getUserID() + "");
                params.put("Titel", VocabularyMethods.vocabularylists.get(id).getName());

                RequestHandler requestHandler = new RequestHandler();
                String sd = requestHandler.sendPostRequest(MainActivity.URL_ListAvailable, params);
                sd = sd.substring(1, sd.length()-1);
                sd = sd.replaceAll("^\"|\"$", "");

                ShareInfo.setText("Diese Öffentliche Vokabelliste ist unter der ID: " + sd + " zu erreichen.");
                ShareInfo.setVisibility(View.VISIBLE);
            }
        }else{
            vocabularyList = sharedlist;
            ShareInfo.setVisibility(View.VISIBLE);
        }

        downoriginal  = new TextView[vocabularyList.size()];
        downtranslation = new TextView[vocabularyList.size() + 10000];

        if(vocabularyList.size() < 5){
            error.setVisibility(View.VISIBLE);
            error.setText("Es müssen mindestens 5 Vokabeln in der Vokabelliste vorhanden sein, um die Übung zu starten");
            train.setEnabled(false);
        }else{
            error.setVisibility(View.GONE);
            error.setText("");
            train.setEnabled(true);
        }

        boolean f = true;
        int e = 0;

        boolean f1 = true;
        int e1 = 0;
        //ssasadsasa

        findViewById(R.id.vocabulary_scrollView).setVisibility(View.INVISIBLE);
        findViewById(R.id.vocabulary_scrollview1).setVisibility(View.VISIBLE);

        lang1.setText(vocabularyList.getLanguageName1());
        lang2.setText(vocabularyList.getLanguageName2());

        for (int i = 1; i < vocabularyList.size(); i++) {
            downoriginal[i] = new TextView(Vokabeln.this);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 90, 8, 8);

            downoriginal[i].setText(vocabularyList.getVocabularylist().get(i).getOriginal());
            downoriginal[i].setId(i);
            downoriginal[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            layout1.addView(downoriginal[i], params);
            constraintSeto = new ConstraintSet();
            constraintSeto.clone(layout1);

            if(f == true){
                constraintSeto.connect(i, ConstraintSet.TOP, original.getId(), ConstraintSet.BOTTOM);
                constraintSeto.connect(i , ConstraintSet.RIGHT, original.getId(), ConstraintSet.RIGHT);
                constraintSeto.connect(i, ConstraintSet.LEFT, original.getId(), ConstraintSet.LEFT);
                f = false;
            }else{
                constraintSeto.connect(i, ConstraintSet.TOP, e - 1, ConstraintSet.BOTTOM);
                constraintSeto.connect(i, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline).getId(), ConstraintSet.LEFT);
                constraintSeto.connect(i, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline01).getId(), ConstraintSet.RIGHT);
            }

            e = i + 1;
            constraintSeto.applyTo(layout1);
        }
        original.setText(vocabularyList.getVocabularylist().get(0).getOriginal());
        learn_masterTranslation.setText(vocabularyList.getVocabularylist().get(0).getTranslation());



        for (int i = 1; i < vocabularyList.size(); i++) {
            downtranslation[i + 10000] = new TextView(Vokabeln.this);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 90, 8, 8);

            downtranslation[i + 10000].setText(vocabularyList.getVocabularylist().get(i).getTranslation());
            downtranslation[i + 10000].setId(i + 10000);
            downtranslation[i + 10000].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            layout1.addView(downtranslation[i + 10000], params);
            constraintSett = new ConstraintSet();
            constraintSett.clone(layout1);

            if(f1 == true){
                constraintSett.connect(i + 10000, ConstraintSet.TOP, original.getId(), ConstraintSet.BOTTOM);
                constraintSett.connect(i  + 10000, ConstraintSet.RIGHT, translation.getId(), ConstraintSet.RIGHT);
                constraintSett.connect(i + 10000, ConstraintSet.LEFT, translation.getId(), ConstraintSet.LEFT);
                f1 = false;
            }else{
                constraintSett.connect(i + 10000, ConstraintSet.TOP, e1-1 , ConstraintSet.BOTTOM);
                constraintSett.connect(i + 10000, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline02).getId(), ConstraintSet.LEFT);
                constraintSett.connect(i + 10000, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline).getId(), ConstraintSet.RIGHT);
            }

            e1 = i + 1;
            constraintSett.applyTo(layout1);
        }
        translation.setText(vocabularyList.getVocabularylist().get(0).getTranslation());
    }

    public void open_back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void open_back1(){
        for(int i = 0; i < downoriginal.length; i++){
            layout1.removeView(downoriginal[i]);
        }
        for(int i = 0; i < downtranslation.length; i++){
            layout1.removeView(downtranslation[i]);
        }
        if(publiclist == false) {
            findViewById(R.id.vocabulary_scrollView).setVisibility(View.VISIBLE);
            findViewById(R.id.vocabulary_scrollview1).setVisibility(View.INVISIBLE);
        }else{
            findViewById(R.id.vocabulary_ShareMainView).setVisibility(View.VISIBLE);
            findViewById(R.id.vocabulary_scrollview1).setVisibility(View.INVISIBLE);
        }
        publiclist = false;
    }

    public void open_create(){
        Intent intent = new Intent(this, CreateVocList.class);
        startActivity(intent);
    }


    //Lernbereich

    Vocabulary voc;
    int lastvocabulary;


    public void open_learn_back(){
        learn_translation.setText(null);
        findViewById(R.id.vocabulary_scrollView5).setVisibility(View.INVISIBLE);
        findViewById(R.id.vocabulary_scrollview1).setVisibility(View.VISIBLE);
    }


    public void start_train(final int id) {

        final ArrayList<Vocabulary> level0 = new ArrayList<Vocabulary>();
        final ArrayList<Vocabulary> level1 = new ArrayList<Vocabulary>();
        final ArrayList<Vocabulary> level2 = new ArrayList<Vocabulary>();
        final ArrayList<Vocabulary> level3 = new ArrayList<Vocabulary>();
        final ArrayList<Vocabulary> level4 = new ArrayList<Vocabulary>();

        vocabularylist = VocabularyMethods.vocabularylists.get(id).getVocabularylist();

        learn_languages.setText(VocabularyMethods.vocabularylists.get(id).getLanguageName1()+ " - " + VocabularyMethods.vocabularylists.get(id).getLanguageName2());
        learn_vocabularys.setText("Original - Übersetzung");

        findViewById(R.id.vocabulary_scrollView5).setVisibility(View.VISIBLE);
        findViewById(R.id.vocabulary_scrollview1).setVisibility(View.INVISIBLE);

        learn_input.setVisibility(View.VISIBLE);
        learn_translation.setVisibility(View.VISIBLE);
        learn_showtranslation.setVisibility(View.VISIBLE);
        learn_enter.setVisibility(View.VISIBLE);
        learn_masterTranslation.setVisibility(View.INVISIBLE);
        learn_right.setVisibility(View.INVISIBLE);
        learn_wrong.setVisibility(View.INVISIBLE);

        learn_level0Score.setText("" + vocabularylist.size());


        for (int i = 0; i < vocabularylist.size(); i++) {
            level0.add(vocabularylist.get(i));
        }

        open_train(id, level0, level1, level2, level3, level4);

    }

    public void open_train(final int id, final ArrayList<Vocabulary> level0, final ArrayList<Vocabulary> level1, final ArrayList<Vocabulary> level2, final ArrayList<Vocabulary> level3, final ArrayList<Vocabulary> level4){
        String s = learn_translation.getText().toString();
        learn_level0Score.setText(level0.size() + "");
        learn_level1Score.setText(level1.size() + "");
        learn_level2Score.setText(level2.size() + "");
        learn_level3Score.setText(level3.size() + "");
        learn_level4Score.setText(level4.size() + "");


        while(true){
            int d = new Random().nextInt(vocabularylist.size());

            Log.d("last", "Last: " + lastvocabulary);

            if(!(level4.contains(vocabularylist.get(d)))){

                if(d != lastvocabulary){

                    voc = vocabularylist.get(d);
                    lastvocabulary = d;
                    break;

                }else{

                    if((level0.size() == 1) && (level1.size() == 0) && (level2.size() == 0) && (level3.size() == 0)){
                        voc = level0.get(0);
                        lastvocabulary = d;
                        break;
                    }else if((level0.size() == 0) && (level1.size() == 1) && (level2.size() == 0) && (level3.size() == 0)){
                        voc = level1.get(0);
                        lastvocabulary = d;
                        break;
                    }else if((level0.size() == 0) && (level1.size() == 0) && (level2.size() == 1) && (level3.size() == 0)){
                        voc = level2.get(0);
                        lastvocabulary = d;
                        break;
                    }else if((level0.size() == 0) && (level1.size() == 0) && (level2.size() == 0) && (level3.size() == 1)) {
                        voc = level3.get(0);
                        lastvocabulary = d;
                        break;
                    }
                }
            }

        }

        if (vocabularylist.indexOf(voc) % 2 == 0){

            learn_language.setText(voc.getLanguageName1());
            learn_original.setText(voc.getOriginal());
            learn_masterTranslation.setText(voc.getTranslation());

            learn_language1.setText(voc.getLanguageName2());

            learn_showtranslation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    learn_input.setVisibility(View.INVISIBLE);
                    learn_translation.setVisibility(View.INVISIBLE);
                    learn_showtranslation.setVisibility(View.INVISIBLE);
                    learn_enter.setVisibility(View.INVISIBLE);
                    learn_masterTranslation.setVisibility(View.VISIBLE);
                    learn_right.setVisibility(View.VISIBLE);
                    learn_wrong.setVisibility(View.VISIBLE);
                    if(level0.contains(voc)) {
                        open_learn_showtranslation(true, level0, level1, level2, level3, level4, id);
                    }else if(level1.contains(voc)) {
                        open_learn_showtranslation(false, level0, level1, level2, level3, level4, id);
                    }else if(level2.contains(voc)) {
                        open_learn_showtranslation(true, level0, level1, level2, level3, level4, id);
                    }else if(level3.contains(voc)) {
                        open_learn_showtranslation(false, level0, level1, level2, level3, level4, id);
                    }
                }
            });


            learn_enter.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){

                    if (level0.contains(voc)) {
                        open_learn_enter(true, level0, level1, level2, level3, level4, id );
                    } else if (level1.contains(voc)) {
                        open_learn_enter(false, level0, level1, level2, level3, level4, id);
                    } else if (level2.contains(voc)) {
                        open_learn_enter(true, level0, level1, level2, level3, level4, id);
                    } else if (level3.contains(voc)) {
                        open_learn_enter(false, level0, level1, level2, level3, level4, id);
                    }
                }
            });

        } else {

            learn_language.setText(voc.getLanguageName2());
            learn_original.setText(voc.getTranslation());
            learn_masterTranslation.setText(voc.getOriginal());
            learn_language1.setText(voc.getLanguageName1());

            learn_showtranslation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    learn_input.setVisibility(View.INVISIBLE);
                    learn_translation.setVisibility(View.INVISIBLE);
                    learn_showtranslation.setVisibility(View.INVISIBLE);
                    learn_enter.setVisibility(View.INVISIBLE);
                    learn_masterTranslation.setVisibility(View.VISIBLE);
                    learn_right.setVisibility(View.VISIBLE);
                    learn_wrong.setVisibility(View.VISIBLE);

                    if(level0.contains(voc)) {
                        open_learn_showtranslation(false, level0, level1, level2, level3, level4, id);
                    }else if(level1.contains(voc)) {
                        open_learn_showtranslation(true, level0, level1, level2, level3, level4, id);
                    }else if(level2.contains(voc)) {
                        open_learn_showtranslation(false, level0, level1, level2, level3, level4, id);
                    }else if(level3.contains(voc)) {
                        open_learn_showtranslation(true, level0, level1, level2, level3, level4, id);
                    }
                }
            });


            learn_enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(level0.contains(voc)) {
                        open_learn_enter(false, level0, level1, level2, level3, level4, id);
                    }else if(level1.contains(voc)) {
                        open_learn_enter(true, level0, level1, level2, level3, level4, id);
                    }else if(level2.contains(voc)) {
                        open_learn_enter(false, level0, level1, level2, level3, level4, id);
                    }else if(level3.contains(voc)) {
                        open_learn_enter(true, level0, level1, level2, level3, level4, id);
                    }
                }

            });
        }
    }

    public void open_learn_enter(boolean gerade, ArrayList<Vocabulary> level0, ArrayList<Vocabulary> level1, ArrayList<Vocabulary> level2, ArrayList<Vocabulary> level3, ArrayList<Vocabulary> level4, int id){

        MainActivity.hideKeyboard(this);
        vocabularylist =  VocabularyMethods.vocabularylists.get(id).getVocabularylist();

        if(gerade == false) {
            learn_languages.setText(voc.getLanguageName1() + " - " + voc.getLanguageName2());
            learn_vocabularys.setText(voc.getOriginal() + " - " + voc.getTranslation());


            if(!(learn_translation.getText().toString().equalsIgnoreCase(voc.getOriginal()))) {

                if(level4.size() == vocabularylist.size()){
                    openfinish();
                }else {

                    if(level1.contains(voc)){
                        level0.add(voc);
                        level1.remove(voc);
                    }else if(level2.contains(voc)){
                        level1.add(voc);
                        level2.remove(voc);
                    }else if(level3.contains(voc)) {
                        level2.add(voc);
                        level3.remove(voc);
                    }

                    Log.d("Wrong", "Next Vocabulary, Ungerade");
                    Log.d("Wrong", voc.getTranslation() + " != " + learn_translation.getText().toString());
                    open_train(id, level0, level1, level2, level3, level4);
                }
            }else{

                if(level0.contains(voc)){
                    level0.remove(voc);
                    level1.add(voc);
                }else if(level1.contains(voc)){
                    level1.remove(voc);
                    level2.add(voc);
                }else if(level2.contains(voc)){
                    level2.remove(voc);
                    level3.add(voc);
                }else if(level3.contains(voc)) {
                    level3.remove(voc);
                    level4.add(voc);
                }


                if(level4.size() == vocabularylist.size()){
                    Log.d("Right", "Finish");
                    openfinish();
                }else {
                    Log.d("Right", "Next Vocabulary, ID: Ungerade");
                    Log.d("Right", voc.getTranslation() + " == " + learn_translation.getText().toString());
                    open_train(id, level0, level1, level2, level3, level4);
                }
            }

        }else {


            learn_languages.setText(voc.getLanguageName2() + " - " + voc.getLanguageName1());
            learn_vocabularys.setText(voc.getTranslation() + " - " + voc.getOriginal());


            if(!(learn_translation.getText().toString().equalsIgnoreCase(voc.getTranslation()))) {

                if(level4.size() == vocabularylist.size()){
                    openfinish();
                }else {

                    if(level1.contains(voc)){
                        level0.add(voc);
                        level1.remove(voc);
                    }else if(level2.contains(voc)){
                        level1.add(voc);
                        level2.remove(voc);
                    }else if(level3.contains(voc)) {
                        level2.add(voc);
                        level3.remove(voc);
                    }

                    Log.d("Wrong", "Next Vocabulary, ID: Gerade");
                    Log.d("Wrong", voc.getOriginal() + " != " + learn_translation.getText().toString());
                    open_train(id, level0, level1, level2, level3, level4);
                }
            }else{

                if(level0.contains(voc)){
                    level0.remove(voc);
                    level1.add(voc);
                }else if(level1.contains(voc)){
                    level1.remove(voc);
                    level2.add(voc);
                }else if(level2.contains(voc)){
                    level2.remove(voc);
                    level3.add(voc);
                }else if(level3.contains(voc)) {
                    level3.remove(voc);
                    level4.add(voc);
                }

                if(level4.size() == vocabularylist.size()){
                    Log.d("Right", "Finish");
                    openfinish();
                }else {
                    Log.d("Right", "Next Vocabulary, Gerade");
                    Log.d("Right", voc.getOriginal() + " == " + learn_translation.getText().toString());
                    open_train(id, level0, level1, level2, level3, level4);
                }
            }

        }

        learn_translation.setText(null);

    }


    public void openfinish(){
        learn_level0Score.setText("0");
        learn_level1Score.setText("0");
        learn_level2Score.setText("0");
        learn_level3Score.setText("0");
        learn_level4Score.setText("0");
        learn_translation.setText(null);
        findViewById(R.id.vocabulary_scrollView5).setVisibility(View.INVISIBLE);
        findViewById(R.id.vocabulary_scrollview1).setVisibility(View.VISIBLE);
    }









    public void open_learn_showtranslation(final boolean gerade, final ArrayList<Vocabulary> level0, final ArrayList<Vocabulary> level1, final ArrayList<Vocabulary> level2, final ArrayList<Vocabulary> level3, final ArrayList<Vocabulary> level4, final int id){

        MainActivity.hideKeyboard(this);
        vocabularylist =  VocabularyMethods.vocabularylists.get(id).getVocabularylist();


        learn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                learn_input.setVisibility(View.VISIBLE);
                learn_translation.setVisibility(View.VISIBLE);
                learn_showtranslation.setVisibility(View.VISIBLE);
                learn_enter.setVisibility(View.VISIBLE);
                learn_masterTranslation.setVisibility(View.INVISIBLE);
                learn_right.setVisibility(View.INVISIBLE);
                learn_wrong.setVisibility(View.INVISIBLE);

                if(gerade == false) {

                    learn_masterTranslation.setText(vocabularylist.get(lastvocabulary).getTranslation());

                    learn_languages.setText(voc.getLanguageName1() + " - " + voc.getLanguageName2());
                    learn_vocabularys.setText(voc.getOriginal() + " - " + voc.getTranslation());

                    if(level4.size() == vocabularylist.size()){
                        openfinish();
                    }else {

                        if(level1.contains(voc)){
                            level0.add(voc);
                            level1.remove(voc);
                        }else if(level2.contains(voc)){
                            level1.add(voc);
                            level2.remove(voc);
                        }else if(level3.contains(voc)) {
                            level2.add(voc);
                            level3.remove(voc);
                        }

                        Log.d("Right", "Next Vocabulary, Ungerade");
                        Log.d("Right", voc.getTranslation() + " != " + learn_translation.getText().toString());
                        open_train(id, level0, level1, level2, level3, level4);
                    }

                }else{

                    //learn_masterTranslation.setText(vocabularylist.get(lastvocabulary).getOriginal());
                    learn_masterTranslation.setText("Hallo");

                    learn_languages.setText(voc.getLanguageName2() + " - " + voc.getLanguageName1());
                    learn_vocabularys.setText(voc.getTranslation() + " - " + voc.getOriginal());

                    if(level4.size() == vocabularylist.size()){
                        openfinish();
                    }else {

                        if(level1.contains(voc)){
                            level0.add(voc);
                            level1.remove(voc);
                        }else if(level2.contains(voc)){
                            level1.add(voc);
                            level2.remove(voc);
                        }else if(level3.contains(voc)) {
                            level2.add(voc);
                            level3.remove(voc);
                        }

                        Log.d("Right", "Next Vocabulary, ID: Gerade");
                        Log.d("Right", voc.getOriginal() + " != " + learn_translation.getText().toString());
                        open_train(id, level0, level1, level2, level3, level4);
                    }

                }

            }


        });


        learn_wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                learn_input.setVisibility(View.VISIBLE);
                learn_translation.setVisibility(View.VISIBLE);
                learn_showtranslation.setVisibility(View.VISIBLE);
                learn_enter.setVisibility(View.VISIBLE);
                learn_masterTranslation.setVisibility(View.INVISIBLE);
                learn_right.setVisibility(View.INVISIBLE);
                learn_wrong.setVisibility(View.INVISIBLE);

                if(gerade == false) {

                    learn_languages.setText(voc.getLanguageName1() + " - " + voc.getLanguageName2());
                    learn_vocabularys.setText(voc.getOriginal() + " - " + voc.getTranslation());

                    if(level0.contains(voc)){
                        level0.remove(voc);
                        level1.add(voc);
                    }else if(level1.contains(voc)){
                        level1.remove(voc);
                        level2.add(voc);
                    }else if(level2.contains(voc)){
                        level2.remove(voc);
                        level3.add(voc);
                    }else if(level3.contains(voc)) {
                        level3.remove(voc);
                        level4.add(voc);
                    }


                    if(level4.size() == vocabularylist.size()){
                        Log.d("Wrong", "Finish");
                        openfinish();
                    }else {
                        Log.d("Wrong", "Next Vocabulary, ID: Ungerade");
                        Log.d("Wrong", voc.getTranslation() + " == " + learn_translation.getText().toString());
                        open_train(id, level0, level1, level2, level3, level4);
                    }

                }else{

                    learn_masterTranslation.setText(voc.getOriginal());
                    learn_languages.setText(voc.getLanguageName2() + " - " + voc.getLanguageName1());
                    learn_vocabularys.setText(voc.getTranslation() + " - " + voc.getOriginal());

                    if(level0.contains(voc)){
                        level0.remove(voc);
                        level1.add(voc);
                    }else if(level1.contains(voc)){
                        level1.remove(voc);
                        level2.add(voc);
                    }else if(level2.contains(voc)){
                        level2.remove(voc);
                        level3.add(voc);
                    }else if(level3.contains(voc)) {
                        level3.remove(voc);
                        level4.add(voc);
                    }

                    if(level4.size() == vocabularylist.size()){
                        Log.d("Wrong", "Finish");
                        openfinish();
                    }else {
                        Log.d("Wrong", "Next Vocabulary, Gerade");
                        Log.d("Wrong", voc.getOriginal() + " == " + learn_translation.getText().toString());
                        open_train(id, level0, level1, level2, level3, level4);
                    }

                }

            }

        });

        learn_translation.setText(null);


    }



    //Lernbereich


    public void open_all(){

        boolean created = false;

        all.setVisibility(View.INVISIBLE);
        base.setVisibility(View.INVISIBLE);
        create.setVisibility(View.INVISIBLE);

        for (int i = 0; i < buttonlist.size(); i++){
            Button b = buttonlist.get(i);
        }

        for (int i = 0; i < buttonlist.size(); i++){
            Button b = buttonlist.get(i);
            b.setVisibility(View.GONE);
        }
        for (int i = 0; i < buttonlanglist.size(); i++){
            Button b = buttonlanglist.get(i);
            b.setVisibility(View.VISIBLE);
        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < buttonlist.size(); i++){
                    Button b = buttonlist.get(i);
                    b.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < buttonlanglist.size(); i++){
                    Button b = buttonlanglist.get(i);
                    b.setVisibility(View.GONE);
                }


                all.setVisibility(View.VISIBLE);
                create.setVisibility(View.VISIBLE);

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        open_back();
                    }
                });
            }
        });

    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Shared

    private Button ShareScrollBase0, ShareScrollBase1, ShareScrollBase2, ShareScrollBase3, ShareSearch;
    private TextView ShareInfo;
    private EditText ShareGetList;
    private ConstraintLayout ShareLayout;
    private ConstraintSet SharedConstraintSet;
    private Button SharedButtonList0[], SharedButtonList1[], SharedButtonList2[], SharedButtonList3[];
    public static boolean publiclist;
    public static VocabularyList sharedlist;
    public static int sharedID;




    public void openShared(){
        findViewById(R.id.vocabulary_ShareMainView).setVisibility(View.VISIBLE);
        findViewById(R.id.vocabulary_scrollView).setVisibility(View.GONE);

        ShareSearch = findViewById(R.id.vocabulary_ShareSearch);
        ShareGetList.setActivated(false);
        MainActivity.hideKeyboard(this);

        RequestHandler requestHandler = new RequestHandler();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
        String json = requestHandler.sendGetRequest(MainActivity.URL_GetSharedLists);
        ArrayList<ArrayList<String>> SharedLists = gson.fromJson(json, type);
        final ArrayList<String> SharedList = SharedLists.get(0);

        ShareScrollBase0.setText("Name: " + SharedList.get(1));
        ShareScrollBase1.setText(SharedList.get(2) + "-" + SharedList.get(3));
        ShareScrollBase2.setText(SharedList.get(5) + " Vokabeln");
        ShareScrollBase3.setText("Ersteller: " + SharedList.get(4));

        ShareScrollBase0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSharedList(SharedList);
            }
        });
        ShareScrollBase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSharedList(SharedList);
            }
        });
        ShareScrollBase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSharedList(SharedList);
            }
        });
        ShareScrollBase3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSharedList(SharedList);
            }
        });

        SharedButtonList0 = new Button[SharedLists.size()];
        SharedButtonList1 = new Button[SharedLists.size()];
        SharedButtonList2 = new Button[SharedLists.size()];
        SharedButtonList3 = new Button[SharedLists.size()];

        SharedButtonList0[0] = ShareScrollBase0;
        SharedButtonList1[0] = ShareScrollBase1;
        SharedButtonList2[0] = ShareScrollBase2;
        SharedButtonList3[0] = ShareScrollBase3;

        setSharedButtons(SharedButtonList0, SharedButtonList2, SharedLists, (Guideline) findViewById(R.id.vocabulary_ShareGuideline3), (Guideline) findViewById(R.id.vocabulary_ShareGuideline2), 0, 20000, true);
        setSharedButtons(SharedButtonList1, SharedButtonList3, SharedLists, (Guideline) findViewById(R.id.vocabulary_ShareGuideline4), (Guideline) findViewById(R.id.vocabulary_ShareGuideline3), 1, 30000, true);
        setSharedButtons(SharedButtonList2, SharedButtonList0, SharedLists, (Guideline) findViewById(R.id.vocabulary_ShareGuideline3), (Guideline) findViewById(R.id.vocabulary_ShareGuideline2), 2, 40000, false);
        setSharedButtons(SharedButtonList3, SharedButtonList1, SharedLists, (Guideline) findViewById(R.id.vocabulary_ShareGuideline4), (Guideline) findViewById(R.id.vocabulary_ShareGuideline3), 3, 50000, false);

        for (int i = 0; i < SharedLists.size(); i ++){
            setButtonHeight(SharedButtonList0[i], SharedButtonList1[i]);
            setButtonHeight(SharedButtonList2[i], SharedButtonList3[i]);
        }

    }

    public void setSharedButtons(Button[] Creator, Button[] Placor, final ArrayList<ArrayList<String>> SharedLists, Guideline right, Guideline left, int namechoice, int offset, boolean line){
        for (int i = 1; i < SharedLists.size(); i ++){
            final ArrayList<String> SharedList = SharedLists.get(i);
            Creator[i] = new Button(Vokabeln.this);


            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if(line == true) {
                params.setMargins(0, 240, 0, 0);
            }else{
                if(i == SharedLists.size() - 1) {
                    params.setMargins(0, 0, 0, 100);
                }else{
                    params.setMargins(0, 0, 0, 0);
                }
            }

            if(namechoice == 0) {
                Creator[i].setText("Name: " + SharedLists.get(i).get(1));
            }else if(namechoice == 1) {
                Creator[i].setText(SharedLists.get(i).get(2) + "-" + SharedLists.get(i).get(3));
            }else if(namechoice == 2) {
                Creator[i].setText(SharedLists.get(i).get(5) + " Vokabeln");
            }else if(namechoice == 3) {
                Creator[i].setText("Ersteller: " + SharedLists.get(i).get(4));
            }

            Creator[i].setId(i + offset);
            Creator[i].setPadding(25, 25, 25, 0);
            Creator[i].setBackgroundColor(Color.parseColor("#D8D8D8"));
            Creator[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            ShareLayout.addView(Creator[i], params);
            SharedConstraintSet = new ConstraintSet();
            SharedConstraintSet.clone(ShareLayout);

            if(i == 1){
                if(line == true) {
                    if(namechoice == 0) {
                        SharedConstraintSet.connect(i + offset, ConstraintSet.TOP, SharedButtonList0[0].getId(), ConstraintSet.BOTTOM);
                    }else{
                        SharedConstraintSet.connect(i + offset, ConstraintSet.TOP, SharedButtonList1[0].getId(), ConstraintSet.BOTTOM);
                    }
                    SharedConstraintSet.connect(i + offset, ConstraintSet.RIGHT, right.getId(), ConstraintSet.LEFT);
                    SharedConstraintSet.connect(i + offset, ConstraintSet.LEFT, left.getId(), ConstraintSet.RIGHT);
                }else{
                    SharedConstraintSet.connect(i + offset, ConstraintSet.TOP, Placor[1].getId(), ConstraintSet.BOTTOM);
                    SharedConstraintSet.connect(i + offset, ConstraintSet.RIGHT, Placor[1].getId(), ConstraintSet.RIGHT);
                    SharedConstraintSet.connect(i + offset, ConstraintSet.LEFT, Placor[1].getId(), ConstraintSet.LEFT);
                }
            }else{
                if(line == true) {
                    SharedConstraintSet.connect(i + offset, ConstraintSet.TOP, i - 1 + offset, ConstraintSet.BOTTOM);
                    SharedConstraintSet.connect(i + offset, ConstraintSet.RIGHT, right.getId(), ConstraintSet.LEFT);
                    SharedConstraintSet.connect(i + offset, ConstraintSet.LEFT, left.getId(), ConstraintSet.RIGHT);
                }else {
                    SharedConstraintSet.connect(i + offset, ConstraintSet.TOP, Placor[i].getId(), ConstraintSet.BOTTOM);
                    SharedConstraintSet.connect(i + offset, ConstraintSet.RIGHT, right.getId(), ConstraintSet.LEFT);
                    SharedConstraintSet.connect(i + offset, ConstraintSet.LEFT, left.getId(), ConstraintSet.RIGHT);
                    if(i == SharedLists.size() - 1){
                        SharedConstraintSet.connect(i + offset, ConstraintSet.BOTTOM, ShareLayout.getId(), ConstraintSet.BOTTOM);
                    }
                }
            }
            SharedConstraintSet.applyTo(ShareLayout);
            Creator[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openSharedList(SharedList);
                }
            });
        }
    }

    public void openSharedList(ArrayList<String> SharedList){
        publiclist = true;
        findViewById(R.id.vocabulary_ShareMainView).setVisibility(View.GONE);
        if(Integer.parseInt(SharedList.get(6)) == ManageData.getUserID()) {
            for(VocabularyList list : VocabularyMethods.vocabularylists){
                if(list.getName().equals(SharedList.get(1))){
                    sharedlist = null;
                    showvocabularys(VocabularyMethods.vocabularylists.indexOf(list));
                    break;
                }
            }
        }else{
            sharedID = Integer.parseInt(SharedList.get(6));
            Log.d("SharedID", sharedID + "      --------------------------------------------------------------------------------------------------------------");
            VocabularyList list = new VocabularyList(SharedList.get(2), SharedList.get(3), SharedList.get(1), true);
            sharedlist = list;
            ManageData.DownloadVocabularys(list.getName(), context);
            ShareInfo.setText("Diese Öffentliche Vokabelliste ist unter der ID: " + SharedList.get(7) + " zu erreichen.");
            showvocabularys(VocabularyMethods.vocabularylists.indexOf(list));
        }
    }


    public void setButtonHeight(final Button Button0,final Button Button1){
        ViewTreeObserver observer = Button0.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(Button1.getHeight() > Button0.getHeight()){
                            Button0.setHeight(Button1.getHeight());
                        }else if(Button1.getHeight() < Button0.getHeight()){
                            Button1.setHeight(Button0.getHeight());
                        }
                    }
                });
    }















}