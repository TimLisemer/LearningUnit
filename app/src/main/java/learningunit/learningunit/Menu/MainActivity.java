package learningunit.learningunit.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import learningunit.learningunit.BeforeStart.FirstScreen;
import learningunit.learningunit.Menu.Learn.Vocabulary.Vokabeln;
import learningunit.learningunit.Menu.Organizer.Organizer;
import learningunit.learningunit.Menu.Organizer.Timetable.Timetable;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.API.NewsFeed;
import learningunit.learningunit.R;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    //Datenbank
    private static final String ROOT_URL = "http://185.233.105.80/MySQL/v1/Api.php?apicall=";

    public static final String URL_CreateAccount = ROOT_URL + "CreateAccount";
    public static final String URL_LoginAccount = ROOT_URL + "Login";
    public static final String URL_GetAccount = ROOT_URL + "GetAccount&id=";
    public static final String URL_GetAccounts = ROOT_URL + "GetAccounts";
    public static final String URL_UpdateAccount = ROOT_URL + "UpdateAccount";
    public static final String URL_DeleteAccount = ROOT_URL + "DeleteAccount&id=";
    public static final String URL_CreateVocList = ROOT_URL + "CreateVocList";
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



    //Deklarieren der Knöpfe
    private Button logout, forum, learn, organizer, timetable, statistics, settings;
    public static TextView news;
    private Button learnBack, learnVocab;
    Activity a = new Activity();

    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        learnBack = (Button) findViewById(R.id.main_learn_back);
        learnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.main_mainLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.main_learnLayout).setVisibility(View.GONE);
                news.setVisibility(View.VISIBLE);
                logout.setVisibility(View.VISIBLE);
            }
        });

        learnVocab = (Button) findViewById(R.id.main_learn_vocabulary);
        learnVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_vocabulary();
            }
        });

        logout = (Button) findViewById(R.id.main_logout);
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
                news.setVisibility(View.GONE);
                logout.setVisibility(View.GONE);
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
        Intent intent = new Intent(this, Timetable.class);
        startActivity(intent);
    }

    public void open_statistics(){
        //Intent intent = new Intent(this, FirstScreen.class);
        //startActivity(intent);
    }

    public void open_settings(){
        //Intent intent = new Intent(this, FirstScreen.class);
        //startActivity(intent);
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


    public static Context getAppContext() {
        return MainActivity.context;
    }


}








