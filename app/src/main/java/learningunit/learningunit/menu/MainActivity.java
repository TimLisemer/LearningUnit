package learningunit.learningunit.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import learningunit.learningunit.Objects.MainMethods.MainMethods;
import learningunit.learningunit.Objects.PublicAPIs.AnalyticsApplication;
import learningunit.learningunit.Objects.PublicAPIs.RequestHandler;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.learn.formular.Formeln;
import learningunit.learningunit.menu.learn.vocabulary.Vokabeln;
import learningunit.learningunit.menu.organizer.Organizer;
import learningunit.learningunit.menu.organizer.timetable.Timetable;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.R;
import learningunit.learningunit.menu.organizer.timetable.TimetableShowcase;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    public static int backLocation = 0; // 0 = Main Menu; 1 = Lernen; 2 = Einstellungen, 3 = Sprachauswahl;

    //Datenbank
    private static final String ROOT_URL = "https://learningunit.de/MySQL/v1/Api.php?apicall=";
    public static final String URL_CreateAccount = ROOT_URL + "CreateAccount";
    public static final String URL_LoginAccount = ROOT_URL + "Login";
    public static final String URL_GetAccount = ROOT_URL + "GetAccount&id=";
    public static final String URL_GetAccounts = ROOT_URL + "GetAccounts";
    public static final String URL_UpdateAccount = ROOT_URL + "UpdateAccount";
    public static final String URL_DeleteAccount = ROOT_URL + "DeleteAccount&id=";
    public static final String URL_InsertPremium = ROOT_URL + "InsertPremium&id=";
    public static final String URL_GetPremium = ROOT_URL + "Premium&id=";
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



    public static final String URL_DeleteFormList = ROOT_URL + "DeleteFormList&id=";
    public static final String URL_CreateFormList = ROOT_URL + "CreateFormList";
    public static final String URL_NewCreateFormList = ROOT_URL + "NewCreateFormList&id=";
    public static final String URL_CreateFormular = ROOT_URL + "CreateFormular";
    public static final String URL_GetFormLists = ROOT_URL + "GetFormLists&id=";
    public static final String URL_GetFormulars = ROOT_URL + "GetFormulars&id=";
    public static final String URL_GetFormularSharedLists = ROOT_URL + "GetFormularSharedLists";
    public static final String URL_FormListAvailable = ROOT_URL + "FormListAvailable";
    public static final String URL_GetSharedForm = ROOT_URL + "GetSharedForm&id=";
    public static final String URL_changesSharedForm = ROOT_URL + "changesSharedForm &id=";
    public static final String URL_getFollowForm = ROOT_URL + "getFollowForm&VocID=";
    public static final String URL_FollowForm = ROOT_URL + "FollowForm";
    public static final String URL_FollowedFormLists = ROOT_URL + "GetFollowedFormLists&id=";




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
    private PublisherAdView MainAdView;
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainAdView = (PublisherAdView) findViewById(R.id.MainActivity_AdView);

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
            loadPremium();
        }


        if(!(ManageData.hasPremium())) {
            KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
                @Override
                public void onVisibilityChanged(boolean isOpen) {
                    try {
                        ScrollView scrollpremium = (ScrollView) findViewById(R.id.main_premiumScrollView);
                        Button continuepremium = (Button) findViewById(R.id.main_premiumContinue);
                        if (isOpen) {
                            MainAdView.setVisibility(View.GONE);
                            scrollpremium.setScrollY(continuepremium.getHeight() + 20 + 32);
                        } else {
                            MainAdView.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e){}
                }
            });
            MainAdView.setVisibility(View.VISIBLE);
            MobileAds.initialize(this, "ca-app-pub-2182452775939631~7797227952");
            rewardedAd = createAndLoadRewardedAd();
            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
            MainAdView.loadAd(adRequest);
            MainAdView.setAdSizes(AdSize.SMART_BANNER);

            Button support = (Button) findViewById(R.id.main_support);
            support.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rewardedAd.isLoaded()) {
                        RewardedAdCallback adCallback = new RewardedAdCallback() {
                            @Override
                            public void onRewardedAdOpened() {
                                // Ad opened.
                            }

                            @Override
                            public void onRewardedAdClosed() {
                                rewardedAd = createAndLoadRewardedAd();
                            }

                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem reward) {
                                // User earned reward.
                            }

                            @Override
                            public void onRewardedAdFailedToShow(int errorCode) {
                                // Ad failed to display
                            }
                        };
                        rewardedAd.show(MainActivity.this, adCallback);
                    } else {
                        Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                    }
                }
            });
        }else{
            MainAdView.setVisibility(View.GONE);
        }

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

        Button formular = (Button) findViewById(R.id.main_learn_formular);
        formular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_formular();
            }
        });

        Button accountBack = (Button)findViewById(R.id.main_accountBack);
        accountBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.main_accountLayout).setVisibility(View.GONE);
                findViewById(R.id.main_settingsLayout).setVisibility(View.VISIBLE);
            }
        });

        settingsBack = (Button) findViewById(R.id.main_settingsBack);
        settingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsBack();
            }
        });

        Button account = (Button)findViewById(R.id.main_settingsAccount);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backLocation = 4;
                findViewById(R.id.main_accountLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.main_settingsLayout).setVisibility(View.GONE);
                if(ManageData.hasPremium()){
                    settingsBack.setText(getResources().getString(R.string.AdButtonSettings));
                }else{
                    settingsBack.setText(getResources().getString(R.string.AdButtonRemove));
                    MainMethods.open_RemoveAds(MainActivity.this);
                }
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
    }

    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                "ca-app-pub-2182452775939631/5070637044");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                Log.d("RewardedAD", "AD Loaded");
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                Log.d("RewardedAD", "Failed to Load Ad");
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }





    public void loadPremium() {
        if (ManageData.InternetAvailable(MainActivity.this)){
            RequestHandler requestHandler = new RequestHandler();
            String pre = requestHandler.sendGetRequest(MainActivity.URL_GetPremium + ManageData.getUserID());
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            ArrayList<Integer> i = gson.fromJson(pre, type);
            ManageData.Account.put("SharedID", i.get(1) + "");
            if (i.get(0) == 1) {
                ManageData.setPremium(true);
            } else {
                ManageData.setPremium(false);
            }
        }
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

    public void open_formular(){
        Intent intent = new Intent(this, Formeln.class);
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
        }else if(backLocation == 4){
            findViewById(R.id.main_accountLayout).setVisibility(View.GONE);
            findViewById(R.id.main_settingsLayout).setVisibility(View.VISIBLE);
        }else if(backLocation == 5){
            findViewById(R.id.main_accountLayout).setVisibility(View.VISIBLE);
            findViewById(R.id.main_premiumLayout).setVisibility(View.GONE);
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








