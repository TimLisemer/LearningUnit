package learningunit.learningunit.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.Locale;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import learningunit.learningunit.Objects.API.AnalyticsApplication;
import learningunit.learningunit.Objects.API.RequestHandler;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;
import learningunit.learningunit.Objects.PublicAPIs.TinyDB;
import learningunit.learningunit.Objects.Timetable.Week;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.learn.vocabulary.Vokabeln;
import learningunit.learningunit.menu.organizer.Organizer;
import learningunit.learningunit.menu.organizer.timetable.Timetable;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.API.NewsFeed;
import learningunit.learningunit.R;
import learningunit.learningunit.menu.organizer.timetable.TimetableShowcase;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    public int backLocation = 0; // 0 = Main Menu; 1 = Lernen; 2 = Einstellungen, 3 = Sprachauswahl;

    //Datenbank
    private static final String ROOT_URL = "http://185.233.105.80/MySQL/v1/Api.php?apicall=";
    public static final String URL_CreateAccount = ROOT_URL + "CreateAccount";
    public static final String URL_LoginAccount = ROOT_URL + "Login";
    public static final String URL_GetAccount = ROOT_URL + "GetAccount&id=";
    public static final String URL_GetAccounts = ROOT_URL + "GetAccounts";
    public static final String URL_UpdateAccount = ROOT_URL + "UpdateAccount";
    public static final String URL_DeleteAccount = ROOT_URL + "DeleteAccount&id=";
    public static final String URL_DeleteVocList = ROOT_URL + "DeleteVocList&id=";
    public static final String URL_CreateVocList = ROOT_URL + "CreateVocList";
    public static final String URL_NewCreateVocList = ROOT_URL + "NewGetVocabLists&id=";
    public static final String URL_CreateVocabulary = ROOT_URL + "CreateVocabulary";
    public static final String URL_GetVocabLists = ROOT_URL + "GetVocabLists&id=";
    public static final String URL_GetVocabs = ROOT_URL + "GetVocabs&id=";
    public static final String URL_GetSharedLists = ROOT_URL + "GetSharedLists";
    public static final String URL_ListAvailable = ROOT_URL + "ListAvailable";
    public static final String URL_GetShared = ROOT_URL + "GetShared&id=";
    public static final String URL_changesShared = ROOT_URL + "changesShared&id=";
    public static final String URL_getFollow = ROOT_URL + "getFollow&VocID=";
    public static final String URL_Follow = ROOT_URL + "Follow";
    public static final String URL_FollowedLists = ROOT_URL + "GetFollowedVocabLists&id=";
    public static final String URL_insertWeek= ROOT_URL + "insertWeek";
    public static final String URL_getWeekbyUser= ROOT_URL + "getWeekbyUser&id=";
    public static final String URL_getWeekbyId= ROOT_URL + "getWeekbyId&id=";



    //Deklarieren der Knöpfe
    private Button logout, forum, learn, organizer, timetable, statistics, settings, settingsBack, darkMode, languageBack, german, english, changeLanguage, dellOfflineData;
    public static TextView news;
    private Button learnBack, learnVocab;
    Activity a = new Activity();
    Thread thread;

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        MainActivity.hideKeyboard(this);
        if(ManageData.OfflineAccount == 0){
            Intent intent = new Intent(this, FirstScreen.class);
            startActivity(intent);
        }else if(ManageData.OfflineAccount == 1){
            ManageData.loadOfflineAccount();
        }else if(ManageData.OfflineAccount == 2){
            ManageData.loadOnlineAccount();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisieren der Knöpfe und rufen der OnClick methode

        dellOfflineData = (Button) findViewById(R.id.main_settingsDeleteOfflineData);
        dellOfflineData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDelOfflineData();
            }
        });

        learnBack = (Button) findViewById(R.id.main_learn_back);
        learnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLearnback();
            }
        });

        settingsBack = (Button) findViewById(R.id.main_settingsBack);
        settingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsBack();
            }
        });

        darkMode = (Button) findViewById(R.id.main_settingsDarkMode);
        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDarkMode();
            }
        });

        learnVocab = (Button) findViewById(R.id.main_learn_vocabulary);
        learnVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_vocabulary();
            }
        });

        logout = (Button) findViewById(R.id.main_settingsLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_logout();
            }
        });

        forum = (Button) findViewById(R.id.main_forum);
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_forum();
            }
        });

        learn = (Button) findViewById(R.id.main_learn);
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backLocation = 1;
                news.setVisibility(View.GONE);
                findViewById(R.id.main_mainLayout).setVisibility(View.GONE);
                findViewById(R.id.main_learnLayout).setVisibility(View.VISIBLE);
            }
        });

        organizer = (Button) findViewById(R.id.main_organizer);
        organizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_organizer();
            }
        });

        timetable = (Button) findViewById(R.id.main_timetable);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_timetable();
            }
        });

        statistics = (Button) findViewById(R.id.main_statistics);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_statistics();
            }
        });

        settings = (Button) findViewById(R.id.main_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_settings();
            }
        });

        news = (TextView) findViewById(R.id.main_news);
        news.setSelected(true);

        changeLanguage = (Button) findViewById(R.id.main_settingsLanguage);
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backLocation = 3;
                findViewById(R.id.main_settingsLanguageLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.main_settingsLayout).setVisibility(View.GONE);
            }
        });

        languageBack = (Button) findViewById(R.id.main_settingsLanguageBack);
        languageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_languageBack();
            }
        });

        german = (Button) findViewById(R.id.main_settingsLanguageGerman);
        german.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.ChangeLanguageQuestion3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirstScreen.tinyDB.putString("appLanguage", "de-rDE");
                        setLocale("de-rDE");
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setTitle(R.string.ChangeLanguageQuestion);
                String msg0 = getResources().getString(R.string.ChangeLanguageQuestion1);
                String msg1 = getResources().getString(R.string.ChangeLanguageQuestion2);
                builder.setMessage(msg0 + german.getText() + msg1);
                builder.show();
            }
        });
        english = (Button) findViewById(R.id.main_settingsLanguageEnglish);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.ChangeLanguageQuestion3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirstScreen.tinyDB.putString("appLanguage", "en-rUS");
                        setLocale("en-rUS");
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setTitle(R.string.ChangeLanguageQuestion);
                String msg0 = getResources().getString(R.string.ChangeLanguageQuestion1);
                String msg1 = getResources().getString(R.string.ChangeLanguageQuestion2);
                builder.setMessage(msg0 + english.getText() + msg1);
                builder.show();
            }
        });

        /*
        thread = new Thread() {

            @Override
            public void run() {
                try {
                    do{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                NewsFeed.startTime();
                            }
                        });
                        Thread.sleep(1000);
                    }
                    while (!thread.isInterrupted());
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
        */
        /*
        if(FirstScreen.tinyDB != null) {
            String language = FirstScreen.tinyDB.getString("appLanguage");
            if (!(language.matches(""))) {
                String languageToLoad = FirstScreen.tinyDB.getString("en-US");// your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_main);
            }else{
                Log.d("Language", "Matches..............................");
            }
        }else{
            Log.d("Language", "Null..............................");
        }

        */
    }

    //Buttton OnClick Methoden
    public void open_logout(){
        ManageData.RemoveOfflineData();
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);
    }

    public void open_forum(){
        //Intent intent = new Intent(this, FirstScreen.class);
        //startActivity(intent);
    }

    public void open_vocabulary(){
        Intent intent = new Intent(this, Vokabeln.class);
        startActivity(intent);
    }

    public void open_organizer(){
        Intent intent = new Intent(this, Organizer.class);
        startActivity(intent);
    }

    public void open_timetable(){
        if(FirstScreen.tinyDB.getString("WeekA").equals("")) {

            if(ManageData.OfflineAccount == 2){
                if(ManageData.InternetAvailable(MainActivity.this)) {
                    if (ManageData.LoadTimetable(false, ManageData.getUserID(), MainActivity.this, false)) {
                        Intent intent = new Intent(this, TimetableShowcase.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(this, Timetable.class);
                        startActivity(intent);
                    }
                }else{
                    ManageData.hideKeyboard(MainActivity.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton(getResources().getString(R.string.TryAgain), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            open_timetable();
                        }
                    });

                    builder.setTitle(getResources().getString(R.string.NoNetworkConnection));
                    builder.setMessage(getResources().getString(R.string.NoNetworkInfo));
                    builder.show();
                }
            }else {
                Intent intent = new Intent(this, Timetable.class);
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(this, TimetableShowcase.class);
            startActivity(intent);
        }
    }

    public void open_statistics(){
        //Intent intent = new Intent(this, FirstScreen.class);
        //startActivity(intent);
    }

    public void open_settings(){
        findViewById(R.id.main_mainLayout0).setVisibility(View.GONE);
        findViewById(R.id.main_settingsLayout).setVisibility(View.VISIBLE);
        news.setVisibility(View.GONE);
        backLocation = 2;
    }

    public void openSettingsBack(){
        findViewById(R.id.main_mainLayout0).setVisibility(View.VISIBLE);
        findViewById(R.id.main_settingsLayout).setVisibility(View.GONE);
        news.setVisibility(View.VISIBLE);
        backLocation = 0;
    }

    public void openLearnback(){
        findViewById(R.id.main_mainLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.main_learnLayout).setVisibility(View.GONE);
        news.setVisibility(View.VISIBLE);
        backLocation = 0;
    }

    public void open_languageBack(){
        backLocation = 2;
        findViewById(R.id.main_settingsLanguageLayout).setVisibility(View.GONE);
        findViewById(R.id.main_settingsLayout).setVisibility(View.VISIBLE);
    }

    public void openDelOfflineData(){
        FirstScreen.tinyDB.putString("WeekA", "");
        FirstScreen.tinyDB.putString("WeekB", "");
    }

    public void startDarkMode(){
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL
        //SAAAAAAAAAAMMMMMMUUUUUUUUUUUUUUEEEEEEEEEEEEEELLLLLLLLLLLLLLLL

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    @Override
    public void onBackPressed(){
        if(backLocation == 0){
            finish();
            this.finishAffinity();
            finishAndRemoveTask();
            MainActivity.this.finish();
            System.exit(0);
        }else if (backLocation == 1){
            openLearnback();
        }else if (backLocation == 2){
            openSettingsBack();
        }else if (backLocation == 3) {
            open_languageBack();
        }
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInput(view, 0);
    }

    public static boolean InternetAvailable(Context ctx) {
        if(ManageData.OfflineAccount == 2) {
            ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if ((connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                    || (connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTED)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public static void onAppPause(Context ctx){
        ManageData.uploadDelayedVocabularyLists(ctx);
        ManageData.saveVocabularyLists();
    }
    public static void onAppShutdown(Context ctx){
        ManageData.uploadDelayedVocabularyLists(ctx);
        ManageData.saveVocabularyLists();
    }

    public static void NoNetworkAlert(Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(true);
        builder.setNegativeButton(ctx.getResources().getString(R.string.Back), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setTitle(ctx.getResources().getString(R.string.NoNetworkConnection));
        builder.setMessage(ctx.getResources().getString(R.string.NoNetworkInfo));
        builder.show();
    }





    public static Context getAppContext() {
        return MainActivity.context;
    }


}








