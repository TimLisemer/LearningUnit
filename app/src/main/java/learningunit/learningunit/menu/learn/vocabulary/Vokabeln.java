package learningunit.learningunit.menu.learn.vocabulary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import learningunit.learningunit.Objects.PublicAPIs.AnalyticsApplication;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.PublicListCustomAdapter;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.PublicAPIs.RequestHandler;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.Vocabulary;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyList;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.WriteCsvVocList;
import learningunit.learningunit.R;


public class Vokabeln extends AppCompatActivity {

    private static Button back, back1, create, train, all, follow, rate, settings, yourBase, followbase, allBase, nolistButton, importList;
    private static TextView lang1, lang2, original, translation, error, nolist;
    private static ConstraintLayout layout, layout0, layout00, layout1, bottom;
    private static ConstraintSet constraintSet, constraintSeto, constraintSett;
    private static boolean direction = true;
    private static ArrayList<Vocabulary> vocabularylist;
    private static ArrayList<String> yourlistsString = new ArrayList<String>();
    private static ArrayList<String> followedlistsString = new ArrayList<String>();
    private static ArrayList<String> languageslistsString = new ArrayList<String>();
    private static TextView downtranslation[];
    private static TextView downoriginal[];
    private static Button downyourLists[];
    private static Button downfollowedLists[];
    private static Context context;

    //Lernbereich

    private static Button learn_back, learn_enter, learn_showtranslation, learn_right, learn_wrong;

    private static TextView learn_level0Score, learn_level1Score, learn_level2Score, learn_level3Score, learn_level4Score,
            learn_language, learn_original, learn_language1, learn_languages, learn_vocabularys, learn_masterTranslation;

    private static EditText learn_translation;
    private static TextInputLayout learn_input;

    //Lernbereich

    //Shared
    private static Button shared, shared_back;
    //Shared
    private PublisherAdView VocabularyTrainer_AdView;
    private static PublisherInterstitialAd VocabularyTrainingInterstitialAd;

    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        MobileAds.initialize(this, "ca-app-pub-2182452775939631~7797227952");

        VocabularyTrainer_AdView = (PublisherAdView) findViewById(R.id.VocabularyTrainer_AdView);
        VocabularyTrainer_AdView.setAdSizes(AdSize.FULL_BANNER);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build();
        VocabularyTrainer_AdView.loadAd(adRequest);

        VocabularyTrainingInterstitialAd = new PublisherInterstitialAd(Vokabeln.this);
        VocabularyTrainingInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");


        context = getApplicationContext();
        //Shared
        shared = (Button) findViewById(R.id.vocabulary_shared);
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedClick(Vokabeln.this);
            }
        });
        shared_back = (Button) findViewById(R.id.vocabulary_ShareBack);
        shared_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShareBack();
            }
        });

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
        layout0 = (ConstraintLayout) findViewById(R.id.vocabulary_scrollview40);
        layout00 = (ConstraintLayout) findViewById(R.id.vocabulary_scrollview400);
        layout1 = (ConstraintLayout) findViewById(R.id.vocabulary_constraintlayout1);

        ManageData.loadVocabularyLists(context);

        //Initialisieren der Knöpfe und rufen der OnClick methode
        nolistButton = (Button) findViewById(R.id.vocabulary_nolistsButton);
        followbase = (Button) findViewById(R.id.vocabulary_followBase);
        yourBase = (Button) findViewById(R.id.vocabulary_yourBase);
        allBase = (Button) findViewById(R.id.vocabulary_allBase);
        followbase = (Button) findViewById(R.id.vocabulary_followBase);
        bottom = (ConstraintLayout) findViewById(R.id.vocabulary_showvocabularybottom);
        settings = (Button) findViewById(R.id.vocabulary_edit);
        rate = (Button) findViewById(R.id.vocabulary_rate);
        follow = (Button) findViewById(R.id.vocabulary_follow);
        nolist = (TextView) findViewById(R.id.vocabulary_nolists);
        lang1 = (TextView) findViewById(R.id.vocabulary_language);
        lang2 = (TextView) findViewById(R.id.vocabulary_language1);
        original = (TextView) findViewById(R.id.vocabulary_original);
        translation = (TextView) findViewById(R.id.vocabulary_translation);

        importList = (Button) findViewById(R.id.vocabulary_import);
        importList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImport();
            }
        });

        nolistButton = (Button) findViewById(R.id.vocabulary_nolistsButton);
        nolistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_create();
            }
        });

        all = (Button) findViewById(R.id.vocabulary_all);

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

        ShowLists(Vokabeln.this);
    }

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

    public void ShowLists(final Activity act){
        if(ManageData.InternetAvailable(context)) {
            VocabularyMethods.vocabularylists.clear();
            FirstScreen.tinyDB.remove("VocLists");
            ManageData.saveVocabularyLists();
            ManageData.NewDownloadVocabularyLists(context, true);
            ManageData.DownloadFollowedVocabularyLists(context, true);
        }
        if(VocabularyMethods.vocabularylists.size() > 0) {
            nolist.setVisibility(View.GONE);
            nolistButton.setVisibility(View.GONE);

            yourlistsString.clear();
            followedlistsString.clear();
            languageslistsString.clear();

            boolean yourfirst = true, followedfirst = true, allfirst = true;
            List<VocabularyList> c = new ArrayList<VocabularyList>();
            List<VocabularyList> d =  new ArrayList<VocabularyList>();
            for (VocabularyList e : VocabularyMethods.vocabularylists){
                d .add(e);
            }
            for(VocabularyList list : d){
                if(ManageData.InternetAvailable(context)){
                    Vokabeln.publiclist = false;
                    Vokabeln.sharedlist = null;
                    Vokabeln.sharedID = 0;
                    Vokabeln.sharedListID = 0;
                }
                if(list.getVocabularylist().size() == -5){
                    c.add(list);
                }else {
                    if (list.getName().contains("AllVoc_")) {
                        languageslistsString.add(list.getName());
                    } else {
                        if (list.getFollowed() == false) {
                            list.setCreatorID(ManageData.getUserID());
                            yourlistsString.add(list.getName());
                        } else {
                            followedlistsString.add(list.getName());
                        }
                    }
                }
            }
            for(VocabularyList remover : c){
                VocabularyMethods.vocabularylists.remove(c);
            }
            if(yourlistsString.size() > 0) {
                layout.setVisibility(View.VISIBLE);
                yourBase.setText(yourlistsString.get(0));
                downyourLists = new Button[yourlistsString.size()];
                for (int i = 1; i < yourlistsString.size(); i++) {
                    downyourLists[i] = new Button(Vokabeln.this);

                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(0, 90, 0, 8);

                    downyourLists[i].setText(yourlistsString.get(i));
                    downyourLists[i].setId(i);
                    downyourLists[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    layout.addView(downyourLists[i], params);
                    constraintSet = new ConstraintSet();
                    constraintSet.clone(layout);

                    if (yourfirst) {
                        constraintSet.connect(i, ConstraintSet.TOP, yourBase.getId(), ConstraintSet.BOTTOM);
                        constraintSet.connect(i, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline3).getId(), ConstraintSet.LEFT);
                        constraintSet.connect(i, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline4).getId(), ConstraintSet.RIGHT);
                        yourfirst = false;
                    } else {
                        constraintSet.connect(i, ConstraintSet.TOP, i - 1, ConstraintSet.BOTTOM);
                        constraintSet.connect(i, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline3).getId(), ConstraintSet.LEFT);
                        constraintSet.connect(i, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline4).getId(), ConstraintSet.RIGHT);
                    }
                    constraintSet.applyTo(layout);
                    final String s = yourlistsString.get(i);

                    downyourLists[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                showvocabularys(s, act);
                            }catch (Exception e){}
                        }
                    });
                }
                final String s = yourlistsString.get(0);
                yourBase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            showvocabularys(s, act);
                        }catch (Exception e){}
                    }
                });
            }else{
                layout.setVisibility(View.GONE);
            }

            if(followedlistsString.size() > 0){
                layout0.setVisibility(View.VISIBLE);
                followbase.setText(followedlistsString.get(0));

                int offset = yourlistsString.size();
                downfollowedLists = new Button[followedlistsString.size()];
                for (int i = 1; i < followedlistsString.size(); i++) {
                    downfollowedLists[i] = new Button(Vokabeln.this);

                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(0, 90, 0, 8);
                    downfollowedLists[i].setText(followedlistsString.get(i));
                    downfollowedLists[i].setId(i + offset);
                    downfollowedLists[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    layout0.addView(downfollowedLists[i], params);
                    constraintSet = new ConstraintSet();
                    constraintSet.clone(layout0);
                    if(followedfirst){
                        constraintSet.connect(i + offset, ConstraintSet.TOP, followbase.getId(), ConstraintSet.BOTTOM);
                        constraintSet.connect(i + offset, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline40).getId(), ConstraintSet.LEFT);
                        constraintSet.connect(i + offset, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline30).getId(), ConstraintSet.RIGHT);
                        followedfirst = false;
                    }else{
                        constraintSet.connect(i + offset, ConstraintSet.TOP, i - 1 + offset, ConstraintSet.BOTTOM);
                        constraintSet.connect(i + offset, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline40).getId(), ConstraintSet.LEFT);
                        constraintSet.connect(i + offset, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline30).getId(), ConstraintSet.RIGHT);
                    }
                    constraintSet.applyTo(layout0);
                    final String s = followedlistsString.get(i);
                    downfollowedLists[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VocabularyList list = VocabularyMethods.getVocabularyList0(s);
                            sharedlist = list;
                            publiclist = true;
                            list.setSource(true);
                            list.setShared(true);
                            sharedListID = list.getID();
                            sharedID = list.getCreatorID();
                            ShareInfo.setText("Diese Öffentliche Vokabelliste ist unter der ID: " + sharedListID + " zu erreichen.");
                            direction = false;
                            if(ManageData.InternetAvailable(context)){
                                ManageData.DownloadVocabularys(s, context);
                            }
                            if(list.getVocabularylist().size() > 0) {
                                try {
                                    showvocabularys(list.getName(), act);
                                }catch (Exception e){}
                            }
                        }
                    });
                }
                final String s = followedlistsString.get(0);
                followbase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VocabularyList list = VocabularyMethods.getVocabularyList0(s);
                        sharedlist = list;
                        publiclist = true;
                        sharedListID = list.getID();
                        sharedID = list.getCreatorID();
                        ShareInfo.setText("Diese Öffentliche Vokabelliste ist unter der ID: " + sharedListID + " zu erreichen.");
                        if(ManageData.InternetAvailable(context)){
                            ManageData.DownloadVocabularys(s, context);
                        }
                        direction = false;
                        if(list.getVocabularylist().size() > 0) {
                            try {
                                showvocabularys(list.getName(), act);
                            }catch (Exception e){ }
                        }
                    }
                });
            }else{
                layout0.setVisibility(View.GONE);
            }

            if(languageslistsString.size() > 0){
                allBase.setText(languageslistsString.get(0).replace("AllVoc_", ""));

                int offset = languageslistsString.size() + 3597874;
                Button[] downallLists = new Button[languageslistsString.size()];
                for (int i = 1; i < languageslistsString.size(); i++) {
                    downallLists[i] = new Button(Vokabeln.this);

                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(0, 90, 0, 8);
                    downallLists[i].setText(languageslistsString.get(i).replace("AllVoc_", ""));
                    downallLists[i].setId(i + offset);
                    downallLists[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    layout00.addView(downallLists[i], params);
                    constraintSet = new ConstraintSet();
                    constraintSet.clone(layout00);
                    if(allfirst){
                        constraintSet.connect(i + offset, ConstraintSet.TOP, allBase.getId(), ConstraintSet.BOTTOM);
                        constraintSet.connect(i + offset, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline300).getId(), ConstraintSet.LEFT);
                        constraintSet.connect(i + offset, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline400).getId(), ConstraintSet.RIGHT);
                        allfirst = false;
                    }else{
                        constraintSet.connect(i + offset, ConstraintSet.TOP, i - 1 + offset, ConstraintSet.BOTTOM);
                        constraintSet.connect(i + offset, ConstraintSet.RIGHT, findViewById(R.id.vocabulary_guideline300).getId(), ConstraintSet.LEFT);
                        constraintSet.connect(i + offset, ConstraintSet.LEFT, findViewById(R.id.vocabulary_guideline400).getId(), ConstraintSet.RIGHT);
                    }
                    constraintSet.applyTo(layout00);
                    layout00.setVisibility(View.GONE);
                    final String s = languageslistsString.get(i);
                    downallLists[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {

                                showvocabularys(s, act);
                            }catch (Exception e){ Log.d("ALL", e.toString());}
                        }
                    });
                }
                final String s = languageslistsString.get(0);
                allBase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            showvocabularys(s, act);
                        }catch (Exception e){}
                    }
                });
                all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openALL();
                    }
                });
            }else{
                layout00.setVisibility(View.GONE);
                all.setEnabled(false);
            }

        }else{
            layout.setVisibility(View.GONE);
            nolistButton.setVisibility(View.VISIBLE);
            nolist.setVisibility(View.VISIBLE);
            all.setEnabled(false);
        }
    }

    //Buttton OnClick Methoden


    private static void showvocabularys(final String ListName, final Activity act){
        final int listiD;
        final VocabularyList showvocablist;
        final VocabularyList vocabularyList;
        if(publiclist == false) {
            showvocablist = VocabularyMethods.getVocabularyList0(ListName);
        }else{
            showvocablist = sharedlist;
        }

        vocabularyList = showvocablist;

        if(showvocablist.getShared() == false || showvocablist.getCreatorID() == ManageData.getUserID()) {
            train.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start_train(showvocablist, act);
                }
            });

            if(showvocablist.getID() == 0) {
                if (MainActivity.InternetAvailable(context)) {
                    try {
                        LinkedHashMap<String, String> params = new LinkedHashMap<>();
                        params.put("id", ManageData.getUserID() + "");
                        params.put("Titel", showvocablist.getName());
                        RequestHandler requestHandler = new RequestHandler();
                        String sd = requestHandler.sendPostRequest(MainActivity.URL_ListAvailable, params);
                        sd = sd.substring(1, sd.length() - 1);
                        sd = sd.replaceAll("^\"|\"$", "");
                        listiD = Integer.parseInt(sd);
                        showvocablist.setID(listiD);

                        settings.setText("Einstellungen");
                        settings.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openSettings(listiD, showvocablist, act);
                            }
                        });
                    }catch(Exception e) {
                        settings.setText("Einstellungen");
                    }
                }
            }else{
                listiD = showvocablist.getID();
                settings.setText("Einstellungen");
                settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openSettings(listiD, showvocablist, act);
                    }
                });
            }

            if(showvocablist.getShared() == false) {
                bottom.setVisibility(View.GONE);
            }else {
                if (showvocablist.getCreatorID() == ManageData.getUserID()) {
                    bottom.setVisibility(View.VISIBLE);
                    follow.setText("Platzhalter");
                    follow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
                    rate.setText("Platzhalter");
                } else {
                    bottom.setVisibility(View.GONE);
                }
            }
        }else{
            train.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start_train(showvocablist, act);
                }
            });
            bottom.setVisibility(View.VISIBLE);
            if(MainActivity.InternetAvailable(context)) {
                RequestHandler requestHandler = new RequestHandler();
                String json = requestHandler.sendGetRequest(MainActivity.URL_getFollow + showvocablist.getID() + "&UserID=" + ManageData.getUserID());
                Gson gson = new Gson();
                Type type = new TypeToken<String>() {
                }.getType();
                String a = gson.fromJson(json, type);

                final int followStatus = Integer.parseInt(a);
                if (followStatus == 0) {
                    follow.setText("Folgen");
                } else {
                    follow.setText("Gefolgt");
                }
            }else{
                follow.setText("OFFLINE");
            }
            settings.setText("Liste Melden");
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Liste", "Gemeldet");
                }
            });
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFollow(showvocablist, follow, act);
                }
            });
            rate.setText("Bewerten");
        }

        try{
            if(showvocablist.getShared() == true){
                ShareInfo.setText("Diese Öffentliche Vokabelliste ist unter der ID: " + showvocablist.getID() + " zu erreichen.");
                ShareInfo.setVisibility(View.VISIBLE);
            }else{
                ShareInfo.setVisibility(View.GONE);
            }
        }catch (Exception e){
            ShareInfo.setVisibility(View.GONE);
        }

        downoriginal  = new TextView[showvocablist.size()];
        downtranslation = new TextView[showvocablist.size() + 10000];

        if(showvocablist.size() < 5){
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

        act.findViewById(R.id.vocabulary_scrollView).setVisibility(View.INVISIBLE);
        act.findViewById(R.id.vocabulary_scrollview1).setVisibility(View.VISIBLE);

        lang1.setText(showvocablist.getLanguageName1());
        lang2.setText(showvocablist.getLanguageName2());

        for (int i = 1; i < showvocablist.size(); i++) {
            downoriginal[i] = new TextView(act);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 90, 8, 8);

            downoriginal[i].setText(showvocablist.getVocabularylist().get(i).getOriginal());
            downoriginal[i].setId(i);
            downoriginal[i].setTextSize(18);
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
                constraintSeto.connect(i, ConstraintSet.RIGHT, act.findViewById(R.id.vocabulary_guideline).getId(), ConstraintSet.LEFT);
                constraintSeto.connect(i, ConstraintSet.LEFT, act.findViewById(R.id.vocabulary_guideline01).getId(), ConstraintSet.RIGHT);
            }

            e = i + 1;
            constraintSeto.applyTo(layout1);
        }
        original.setText(showvocablist.getVocabularylist().get(0).getOriginal());
        learn_masterTranslation.setText(showvocablist.getVocabularylist().get(0).getTranslation());



        for (int i = 1; i < vocabularyList.size(); i++) {
            downtranslation[i + 10000] = new TextView(act);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 90, 8, 8);

            downtranslation[i + 10000].setText(vocabularyList.getVocabularylist().get(i).getTranslation());
            downtranslation[i + 10000].setTextSize(18);
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
                constraintSett.connect(i + 10000, ConstraintSet.RIGHT, act.findViewById(R.id.vocabulary_guideline02).getId(), ConstraintSet.LEFT);
                constraintSett.connect(i + 10000, ConstraintSet.LEFT, act.findViewById(R.id.vocabulary_guideline).getId(), ConstraintSet.RIGHT);
            }

            e1 = i + 1;
            constraintSett.applyTo(layout1);
        }
        translation.setText(vocabularyList.getVocabularylist().get(0).getTranslation());

    }

    @Override
    public void onBackPressed(){
        if(findViewById(R.id.vocabulary_scrollview1).getVisibility() == View.VISIBLE) {
            open_back1();
        }else if(findViewById(R.id.vocabulary_scrollView5).getVisibility() == View.VISIBLE) {
            open_learn_back();
        }else if(findViewById(R.id.vocabulary_ShareMainView).getVisibility() == View.VISIBLE) {
            openShareBack();
        }else if(findViewById(R.id.vocabulary_scrollView).getVisibility() == View.VISIBLE) {
            open_back();
        }
    }

    public void openShareBack(){
        Intent intent = new Intent(Vokabeln.this, Vokabeln.class);
        startActivity(intent);
    }

    public void open_back(){
        if(layout00.getVisibility() == View.VISIBLE){
            all.setText("Alle Vokabeln");
            layout00.setVisibility(View.GONE);
            if(yourlistsString.size() > 0){
                layout.setVisibility(View.VISIBLE);
            }
            if(followedlistsString.size() > 0){
                layout0.setVisibility(View.VISIBLE);
            }
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void open_back1(){

        for(int i = 0; i < downoriginal.length; i++){
            layout1.removeView(downoriginal[i]);
        }
        for(int i = 0; i < downtranslation.length; i++){
            layout1.removeView(downtranslation[i]);
        }
        if(direction == false) {
            Intent intent = new Intent(Vokabeln.this, Vokabeln.class);
            startActivity(intent);
            direction = true;
        }else{
            if(publicback){
                findViewById(R.id.vocabulary_ShareMainView).setVisibility(View.VISIBLE);
                findViewById(R.id.vocabulary_scrollview1).setVisibility(View.INVISIBLE);
                publicback = false;
            }else{
                if (publiclist == false) {
                    findViewById(R.id.vocabulary_scrollView).setVisibility(View.VISIBLE);
                    findViewById(R.id.vocabulary_scrollview1).setVisibility(View.INVISIBLE);
                } else {
                    findViewById(R.id.vocabulary_ShareMainView).setVisibility(View.VISIBLE);
                    findViewById(R.id.vocabulary_scrollview1).setVisibility(View.INVISIBLE);
                }
            }
        }
        publiclist = false;
    }

    public void open_create(){
        VocabularyMethods.openCreateList = true;
        Intent intent = new Intent(this, CreateVocList.class);
        startActivity(intent);
    }

    public void openImport(){
        VocabularyMethods.openCreateList = false;
        Intent intent = new Intent(this, CreateVocList.class);
        startActivity(intent);
    }


    //Lernbereich

    private static Vocabulary voc;
    private static int lastvocabulary;


    public void open_learn_back(){
        learn_translation.setText(null);
        findViewById(R.id.vocabulary_scrollView5).setVisibility(View.INVISIBLE);
        findViewById(R.id.vocabulary_scrollview1).setVisibility(View.VISIBLE);
    }


    private static void start_train(final VocabularyList trainlist, final Activity act) {
        VocabularyTrainingInterstitialAd.loadAd(new PublisherAdRequest.Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build());

        final ArrayList<Vocabulary> level0 = new ArrayList<Vocabulary>();
        final ArrayList<Vocabulary> level1 = new ArrayList<Vocabulary>();
        final ArrayList<Vocabulary> level2 = new ArrayList<Vocabulary>();
        final ArrayList<Vocabulary> level3 = new ArrayList<Vocabulary>();
        final ArrayList<Vocabulary> level4 = new ArrayList<Vocabulary>();

        learn_languages.setText(trainlist.getLanguageName1()+ " - " + trainlist.getLanguageName2());
        learn_vocabularys.setText("Original - Übersetzung");

        act.findViewById(R.id.vocabulary_scrollView5).setVisibility(View.VISIBLE);
        act.findViewById(R.id.vocabulary_scrollview1).setVisibility(View.INVISIBLE);

        learn_input.setVisibility(View.VISIBLE);
        learn_translation.setVisibility(View.VISIBLE);
        learn_showtranslation.setVisibility(View.VISIBLE);
        learn_enter.setVisibility(View.VISIBLE);
        learn_masterTranslation.setVisibility(View.INVISIBLE);
        learn_right.setVisibility(View.INVISIBLE);
        learn_wrong.setVisibility(View.INVISIBLE);

        learn_level0Score.setText("" + trainlist.size());


        for (int i = 0; i < trainlist.size(); i++) {
            level0.add(trainlist.getVocabularylist().get(i));
        }

        open_train(trainlist, level0, level1, level2, level3, level4, act);

    }

    public static void open_train(final VocabularyList trainlist, final ArrayList<Vocabulary> level0, final ArrayList<Vocabulary> level1, final ArrayList<Vocabulary> level2, final ArrayList<Vocabulary> level3, final ArrayList<Vocabulary> level4, final Activity act){
        String s = learn_translation.getText().toString();
        learn_level0Score.setText(level0.size() + "");
        learn_level1Score.setText(level1.size() + "");
        learn_level2Score.setText(level2.size() + "");
        learn_level3Score.setText(level3.size() + "");
        learn_level4Score.setText(level4.size() + "");
        voc = null;
        lastvocabulary = 0;


        while(true){
            int d = new Random().nextInt(trainlist.size());
            if(!(level4.contains(trainlist.getVocabularylist().get(d)))){

                if(d != lastvocabulary){

                    voc = trainlist.getVocabularylist().get(d);
                    lastvocabulary = d;
                    break;

                }else{

                    if((level0.size() == 1) && (level1.size() == 0) && (level2.size() == 0) && (level3.size() == 0)){
                        voc = level0.get(d);
                        lastvocabulary = d;
                        break;
                    }else if((level0.size() == 0) && (level1.size() == 1) && (level2.size() == 0) && (level3.size() == 0)){
                        voc = level1.get(d);
                        lastvocabulary = d;
                        break;
                    }else if((level0.size() == 0) && (level1.size() == 0) && (level2.size() == 1) && (level3.size() == 0)){
                        voc = level2.get(d);
                        lastvocabulary = d;
                        break;
                    }else if((level0.size() == 0) && (level1.size() == 0) && (level2.size() == 0) && (level3.size() == 1)) {
                        voc = level3.get(d);
                        lastvocabulary = d;
                        break;
                    }
                }
            }
        }

        if (trainlist.getVocabularylist().indexOf(voc) % 2 == 0){

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
                        open_learn_showtranslation(trainlist,true, level0, level1, level2, level3, level4, act);
                    }else if(level1.contains(voc)) {
                        open_learn_showtranslation(trainlist,true, level0, level1, level2, level3, level4, act);
                    }else if(level2.contains(voc)) {
                        open_learn_showtranslation(trainlist,true, level0, level1, level2, level3, level4, act);
                    }else if(level3.contains(voc)) {
                        open_learn_showtranslation(trainlist,true, level0, level1, level2, level3, level4, act);
                    }
                }
            });


            learn_enter.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){

                    if (level0.contains(voc)) {
                        open_learn_enter(trainlist,true, level0, level1, level2, level3, level4, act);
                    } else if (level1.contains(voc)) {
                        open_learn_enter(trainlist,true, level0, level1, level2, level3, level4, act);
                    } else if (level2.contains(voc)) {
                        open_learn_enter(trainlist,true, level0, level1, level2, level3, level4, act);
                    } else if (level3.contains(voc)) {
                        open_learn_enter(trainlist,true, level0, level1, level2, level3, level4, act);
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
                        open_learn_showtranslation(trainlist,false, level0, level1, level2, level3, level4, act);
                    }else if(level1.contains(voc)) {
                        open_learn_showtranslation(trainlist,false, level0, level1, level2, level3, level4, act);
                    }else if(level2.contains(voc)) {
                        open_learn_showtranslation(trainlist,false, level0, level1, level2, level3, level4, act);
                    }else if(level3.contains(voc)) {
                        open_learn_showtranslation(trainlist,false, level0, level1, level2, level3, level4, act);
                    }
                }
            });


            learn_enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(level0.contains(voc)) {
                        open_learn_enter(trainlist, false, level0, level1, level2, level3, level4, act);
                    }else if(level1.contains(voc)) {
                        open_learn_enter(trainlist,false, level0, level1, level2, level3, level4, act);
                    }else if(level2.contains(voc)) {
                        open_learn_enter(trainlist,false, level0, level1, level2, level3, level4, act);
                    }else if(level3.contains(voc)) {
                        open_learn_enter(trainlist,false, level0, level1, level2, level3, level4, act);
                    }
                }

            });
        }
    }

    private static void open_learn_enter(final VocabularyList showvocablist, boolean gerade, ArrayList<Vocabulary> level0, ArrayList<Vocabulary> level1, ArrayList<Vocabulary> level2, ArrayList<Vocabulary> level3, ArrayList<Vocabulary> level4, final Activity act){

        MainActivity.hideKeyboard(act);
        vocabularylist =  showvocablist.getVocabularylist();

        if(gerade == false) {
            learn_languages.setText(voc.getLanguageName1() + " - " + voc.getLanguageName2());
            learn_vocabularys.setText(voc.getOriginal() + " - " + voc.getTranslation());


            if(!(learn_translation.getText().toString().equalsIgnoreCase(voc.getOriginal()))) {

                if(level4.size() == vocabularylist.size()){
                    openfinish(act);
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

                    open_train(showvocablist, level0, level1, level2, level3, level4, act);
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
                    openfinish(act);
                }else {
                    open_train(showvocablist, level0, level1, level2, level3, level4, act);
                }
            }

        }else {


            learn_languages.setText(voc.getLanguageName2() + " - " + voc.getLanguageName1());
            learn_vocabularys.setText(voc.getTranslation() + " - " + voc.getOriginal());


            if(!(learn_translation.getText().toString().equalsIgnoreCase(voc.getTranslation()))) {

                if(level4.size() == vocabularylist.size()){
                    openfinish(act);
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

                    open_train(showvocablist, level0, level1, level2, level3, level4, act);
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
                    openfinish(act);
                }else {
                    open_train(showvocablist, level0, level1, level2, level3, level4, act);
                }
            }

        }

        learn_translation.setText(null);

    }


    private static void openfinish(final Activity act){
        learn_level0Score.setText("0");
        learn_level1Score.setText("0");
        learn_level2Score.setText("0");
        learn_level3Score.setText("0");
        learn_level4Score.setText("0");
        learn_translation.setText(null);
        VocabularyTrainingInterstitialAd.loadAd(new PublisherAdRequest.Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build());
        VocabularyTrainingInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                VocabularyTrainingInterstitialAd.loadAd(new PublisherAdRequest.Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build());
            }
        });
        if (VocabularyTrainingInterstitialAd.isLoaded()) {
            VocabularyTrainingInterstitialAd.show();
        } else {
            Log.d("TAG", "The VocabularyTrainingInterstitialAd wasn't loaded yet.");
        }
        act.findViewById(R.id.vocabulary_scrollView5).setVisibility(View.INVISIBLE);
        act.findViewById(R.id.vocabulary_scrollview1).setVisibility(View.VISIBLE);
    }









    private static void open_learn_showtranslation(final VocabularyList showvocablist, final boolean gerade, final ArrayList<Vocabulary> level0, final ArrayList<Vocabulary> level1, final ArrayList<Vocabulary> level2, final ArrayList<Vocabulary> level3, final ArrayList<Vocabulary> level4, final Activity act){

        MainActivity.hideKeyboard(act);
        vocabularylist =  showvocablist.getVocabularylist();


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
                        openfinish(act);
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

                        open_train(showvocablist, level0, level1, level2, level3, level4, act);
                    }

                }else{

                    //learn_masterTranslation.setText(vocabularylist.get(lastvocabulary).getOriginal());

                    learn_languages.setText(voc.getLanguageName2() + " - " + voc.getLanguageName1());
                    learn_vocabularys.setText(voc.getTranslation() + " - " + voc.getOriginal());

                    if(level4.size() == vocabularylist.size()){
                        openfinish(act);
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
                        open_train(showvocablist, level0, level1, level2, level3, level4, act);
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
                        openfinish(act);
                    }else {
                        open_train(showvocablist, level0, level1, level2, level3, level4, act);
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
                        openfinish(act);
                    }else {
                        open_train(showvocablist, level0, level1, level2, level3, level4, act);
                    }

                }

            }

        });

        learn_translation.setText(null);


    }



    //Lernbereich


    public void openALL(){
        ManageData.DownloadFollowedVocabularyLists(context, true);
        ManageData.NewDownloadVocabularyLists(context, true);
        if(layout00.getVisibility() == View.GONE){
            all.setText("Alle Listen");
            layout00.setVisibility(View.VISIBLE);
            if(layout.getVisibility() == View.VISIBLE){
                layout.setVisibility(View.GONE);
            }
            if(layout0.getVisibility() == View.VISIBLE){
                layout0.setVisibility(View.GONE);
            }
        }else{
            all.setText("Alle Vokabeln");
            layout00.setVisibility(View.GONE);
            if(yourlistsString.size() > 0){
                layout.setVisibility(View.VISIBLE);
            }
            if(followedlistsString.size() > 0){
                layout0.setVisibility(View.VISIBLE);
            }
        }
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openALL();
            }
        });

    }




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Shared

    private static Button ShareScrollBase0, ShareScrollBase1, ShareScrollBase2, ShareScrollBase3, ShareSearch;
    private static TextView ShareInfo;
    private static EditText ShareGetList;
    private static ConstraintLayout ShareLayout;
    private static ConstraintSet SharedConstraintSet;
    private static Button SharedButtonList0[], SharedButtonList1[], SharedButtonList2[], SharedButtonList3[];
    public static boolean publiclist;
    private static boolean publicback;
    public static VocabularyList sharedlist;
    public static int sharedID, sharedListID;


    private static void sharedClick(final Activity act){
        if(ManageData.OfflineAccount == 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setCancelable(true);
            builder.setTitle(act.getResources().getString(R.string.AccountNeeded));
            builder.setMessage(act.getResources().getString(R.string.PublicVocabInfo));
            builder.setNegativeButton(act.getResources().getString(R.string.Back), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton(act.getResources().getString(R.string.LoginRegister), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ManageData.RemoveOfflineData();
                    Intent intent = new Intent(act, FirstScreen.class);
                    act.startActivity(intent);
                    act.findViewById(R.id.vocabulary_buttonView).setVisibility(View.VISIBLE);
                }
            });
            builder.show();
        }else {
            if(ManageData.InternetAvailable(context) == true) {
                openShared(act);
                act.findViewById(R.id.vocabulary_buttonView).setVisibility(View.VISIBLE);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setCancelable(true);
                builder.setNegativeButton(act.getResources().getString(R.string.Back), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setTitle(act.getResources().getString(R.string.NoNetworkConnection));
                builder.setMessage(act.getResources().getString(R.string.NoNetworkInfo));
                builder.setPositiveButton(act.getResources().getString(R.string.TryAgain), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedClick(act);
                    }
                });
                builder.show();
            }
        }
    }


    private static void openShared(Activity act){
        act.findViewById(R.id.vocabulary_ShareMainView).setVisibility(View.VISIBLE);
        act.findViewById(R.id.vocabulary_scrollView).setVisibility(View.GONE);

        ShareSearch = act.findViewById(R.id.vocabulary_ShareSearch);
        ShareGetList.setActivated(false);
        MainActivity.hideKeyboard(act);

        RequestHandler requestHandler = new RequestHandler();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
        String json = requestHandler.sendGetRequest(MainActivity.URL_GetSharedLists);
        ArrayList<ArrayList<String>> SharedLists = gson.fromJson(json, type);

        ListView shareListView = (ListView) act.findViewById(R.id.vocabulary_ShareListView);
        shareListView.setAdapter(new PublicListCustomAdapter(act, SharedLists));

    }

    public static void openSharedList(ArrayList<String> SharedList, Activity act){
        publiclist = true;
        publicback = true;
        act.findViewById(R.id.vocabulary_ShareMainView).setVisibility(View.GONE);
        if(Integer.parseInt(SharedList.get(6)) == ManageData.getUserID()) {
            for(VocabularyList list : VocabularyMethods.vocabularylists){
                if(list.getName().equals(SharedList.get(1))){
                    sharedlist = null;
                    publiclist = false;
                    list.setSource(true);
                    list.setShared(true);
                    showvocabularys(list.getName(), act);
                    break;
                }
            }
        }else{
            sharedID = Integer.parseInt(SharedList.get(6));
            sharedListID = Integer.parseInt(SharedList.get(0));
            VocabularyList list = new VocabularyList(SharedList.get(2), SharedList.get(3), SharedList.get(1), true, true, sharedListID, sharedID, true);
            VocabularyMethods.vocabularylists.add(list);
            direction = true;
            sharedlist = list;
            publiclist = true;
            Vokabeln.sharedlist = list;
            ManageData.DownloadVocabularys(list.getName(), act);
            showvocabularys(list.getName(), act);
            Vokabeln.publiclist = false;
            Vokabeln.sharedlist = null;
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



    private static void openFollow(VocabularyList flist, Button follow, Activity act){
        if(MainActivity.InternetAvailable(context)) {
            RequestHandler requestHandler = new RequestHandler();
            String json = requestHandler.sendGetRequest(MainActivity.URL_getFollow + flist.getID() + "&UserID=" + ManageData.getUserID());
            Gson gson = new Gson();
            Type type = new TypeToken<String>() {
            }.getType();
            String a = gson.fromJson(json, type);
            if(a.equalsIgnoreCase("s")){
                throw new IllegalArgumentException("Cannot cast String a to int: a == " + a);
            }
            final int followStatus = Integer.parseInt(a);

            if(follow.getText().toString().equalsIgnoreCase("Offline")){
                if (followStatus == 1) {
                    follow.setText(act.getResources().getString(R.string.Followed));
                } else {
                    follow.setText(act.getResources().getString(R.string.Follow));
                }
            }else {
                LinkedHashMap<String, String> params = new LinkedHashMap<>();
                params.put("VocID", flist.getID() + "");
                params.put("UserID", ManageData.getUserID() + "");
                params.put("UserID", ManageData.getUserID() + "");
                if (followStatus == 0) {
                    params.put("State", 1 + "");
                    follow.setText(act.getResources().getString(R.string.Followed));
                } else {
                    params.put("State", 0 + "");
                    follow.setText(act.getResources().getString(R.string.Follow));

                    VocabularyList g = null;
                    VocabularyMethods.removeFollowedVocabularys(flist);
                    for (VocabularyList list : VocabularyMethods.vocabularylists) {
                        if (list.getName().equals(flist.getName())) {
                            g = list;
                            break;
                        }
                    }
                    if (g != null) {
                        VocabularyMethods.vocabularylists.remove(g);
                    }

                }
                requestHandler.sendPostRequest(MainActivity.URL_Follow, params);
            }
        }else{
            follow.setText("OFFLINE");
        }
    }


    private static void openSettings(final int vocID, final VocabularyList list, final Activity act){
        PopupMenu popup = new PopupMenu(act, settings);
        MenuInflater settings = act.getMenuInflater();
        settings.inflate(R.menu.vocabulary_settings_menu, popup.getMenu());
        MenuItem ShareMenuItem = popup.getMenu().findItem(R.id.vocabulary_open_share);
        if (list.getShared() == false) {
            ShareMenuItem.setTitle(act.getResources().getString(R.string.PublishList));
        } else {
            ShareMenuItem.setTitle(act.getResources().getString(R.string.SetListPrivate));
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.vocabulary_open_share:
                        if(MainActivity.InternetAvailable(context)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(act);
                            builder.setCancelable(true);
                            builder.setNegativeButton(act.getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            if(list.getShared() == false) {
                                builder.setPositiveButton(act.getResources().getString(R.string.Publish), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        openShare(vocID, list, act);
                                    }
                                });
                                builder.setTitle(act.getResources().getString(R.string.PublishVocabList));
                                builder.setMessage(act.getResources().getString(R.string.ConfirmPublication));
                            }else{
                                builder.setPositiveButton(act.getResources().getString(R.string.SetPrivate), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        openShare(vocID, list, act);
                                    }
                                });
                                builder.setTitle(act.getResources().getString(R.string.SetVocabularyListPrivate));
                                builder.setMessage(act.getResources().getString(R.string.ConfirmUnPublication));
                            }
                            builder.show();
                            return true;
                        }else{
                            MainActivity.NoNetworkAlert(act);
                            return false;
                        }

                    case R.id.vocabulary_open_export:

                        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && act.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            act.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1002);
                            exportlist = list;
                        }else {
                            WriteCsvVocList writelist = new WriteCsvVocList();
                            writelist.WriteFile(list, act);
                        }

                        return true;

                    case R.id.vocabulary_open_delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(act);
                        publicback = false;
                        if(list.getSource() == false){
                            builder.setCancelable(true);
                            builder.setNegativeButton(act.getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.setPositiveButton(act.getResources().getString(R.string.Ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    VocabularyMethods.vocabularylists.remove(list);
                                    FirstScreen.tinyDB.remove("VocLists");
                                    ManageData.saveVocabularyLists();
                                }
                            });
                            builder.setTitle(act.getResources().getString(R.string.DeleteVocabList));
                            builder.setMessage(act.getResources().getString(R.string.DeleteVocabListConfirmation));
                            builder.show();
                            return true;
                        }else if(MainActivity.InternetAvailable(context)) {

                            builder.setCancelable(true);
                            builder.setNegativeButton(act.getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.setPositiveButton(act.getResources().getString(R.string.Ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RequestHandler requestHandler = new RequestHandler();
                                    VocabularyMethods.vocabularylists.remove(list);
                                    FirstScreen.tinyDB.remove("VocLists");
                                    ManageData.saveVocabularyLists();
                                    String json = requestHandler.sendGetRequest(MainActivity.URL_DeleteVocList + vocID);
                                    Intent intent = new Intent(act, Vokabeln.class);
                                    act.startActivity(intent);
                                }
                            });
                            builder.setTitle(act.getResources().getString(R.string.DeleteVocabList));
                            builder.setMessage(act.getResources().getString(R.string.DeleteVocabListConfirmation));
                            builder.show();
                            return true;
                        }else{
                            MainActivity.NoNetworkAlert(act);
                            return false;
                        }
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private static VocabularyList exportlist;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1002: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, getResources().getString(R.string.AccesGranted), Toast.LENGTH_SHORT).show();
                    WriteCsvVocList writelist = new WriteCsvVocList();
                    writelist.WriteFile(exportlist, Vokabeln.this);
                }else{
                    Toast.makeText(this, getResources().getString(R.string.AccessDenied), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

    }


    private static void openShare(int vocID, VocabularyList list, final Activity act){
        if(ManageData.InternetAvailable(context)) {
            RequestHandler requestHandler = new RequestHandler();
            list.setID(vocID);

            String json = requestHandler.sendGetRequest(MainActivity.URL_GetShared + vocID);
            Gson gson = new Gson();
            Type type = new TypeToken<String>() {
            }.getType();
            String a = gson.fromJson(json, type);

            final int sharedStatus = Integer.parseInt(a);
            if(sharedStatus == 0){
                list.setShared(false);
            }else{
                list.setShared(true);
            }

            if (list.getShared() == false) {
                requestHandler.sendGetRequest(MainActivity.URL_changesShared + vocID + "&State=1");
                ShareInfo.setText("Diese Öffentliche Vokabelliste ist unter der ID: " + vocID + " zu erreichen.");
                ShareInfo.setVisibility(View.VISIBLE);
                list.setShared(true);
                VocabularyMethods.saveVocabularyList(list);
                Intent intent = new Intent(act, Vokabeln.class);
                act.startActivity(intent);
            } else {
                requestHandler.sendGetRequest(MainActivity.URL_changesShared + vocID + "&State=0");
                ShareInfo.setVisibility(View.GONE);
                list.setShared(true);
                VocabularyMethods.saveVocabularyList(list);
                Intent intent = new Intent(act, Vokabeln.class);
                act.startActivity(intent);
            }
        }else{
            ShareInfo.setText("Internet nicht Verfügbar");
            ShareInfo.setVisibility(View.VISIBLE);
        }
    }


























}