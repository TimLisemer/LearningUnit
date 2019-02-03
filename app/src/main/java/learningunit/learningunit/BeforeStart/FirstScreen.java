package learningunit.learningunit.BeforeStart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import learningunit.learningunit.Menu.Learn.Vocabulary.Vokabeln;
import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.API.RequestHandler;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;
import learningunit.learningunit.R;
import learningunit.learningunit.Objects.PublicAPIs.TinyDB;

public class FirstScreen extends AppCompatActivity {

    Button button;

    //Deklarieren der Knöpfe
    private Button continue1, continue2, login, register, back;
    public static TinyDB tinyDB;
    public static SharedPreferences sharedPreferences;
    private Button Back, accept, decline;
    private EditText email, editpassword1;
    private int ready1, ready;
    private TextView errorview;
    private ConstraintLayout view1, view2;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(this);
        sharedPreferences = getSharedPreferences("variables", MODE_PRIVATE);
        if(ManageData.Account == null){
            ManageData.Account = new LinkedHashMap<String, String>();
        }
        ManageData.Account.clear();

        ManageData.loadOfflineAccount();
        ManageData.loadOnlineAccount();
        if(ManageData.OfflineAccount == 0) {
            setContentView(R.layout.activity_first_screen);

            //Initialisieren der Knöpfe und rufen der OnClick methode
            continue1 = (Button) findViewById(R.id.first_continue);
            continue1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    open_continue1();
                }
            });

            continue2 = (Button) findViewById(R.id.first_continue1);
            continue2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    open_continue2();
                }
            });

            login = (Button) findViewById(R.id.first_login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    open_login();
                }
            });

            register = (Button) findViewById(R.id.first_register);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    open_register();
                }
            });

            errorview = (TextView) findViewById(R.id.login_error);

            email = (EditText) findViewById(R.id.login_email);
            email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    email.setError(null);
                    editpassword1.setError(null);
                    ready = 1;
                    errorview.setVisibility(View.GONE);
                    String inhalt = email.getText().toString().trim();
                    if(!inhalt.isEmpty()){
                        ready = 1;
                    }else{
                        ready = 0;
                    }
                    if(ready == 1 && ready1 == 1){
                        login.setEnabled(true);
                    }else{
                        login.setEnabled(false);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            editpassword1 = (EditText) findViewById(R.id.login_password);
            editpassword1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    email.setError(null);
                    editpassword1.setError(null);
                    errorview.setVisibility(View.GONE);
                    String inhalt = editpassword1.getText().toString().trim();
                    if(!inhalt.isEmpty()){
                        ready1 = 1;
                    }else{
                        ready1 = 0;
                    }
                    if(ready == 1 && ready1 == 1){
                        login.setEnabled(true);
                    }else{
                        login.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



            back = (Button) findViewById(R.id.first_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    open_back();
                }
            });
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        context = getApplicationContext();
        view1 = (ConstraintLayout) findViewById(R.id.first_scrollview);
        view2 = (ConstraintLayout) findViewById(R.id.login_ScrollView2);
        Back = (Button) findViewById(R.id.login_back1);
        accept = (Button) findViewById(R.id.login_accept);
        decline = (Button) findViewById(R.id.login_decline);


        errorview = (TextView) findViewById(R.id.login_error);

    }


    public boolean login(){
        MainActivity.hideKeyboard(FirstScreen.this);
        LinkedHashMap<String, String> params = new LinkedHashMap <>();
        params.put("Benutzername", email.getText().toString().trim());
        params.put("Passwort", Register.md5(editpassword1.getText().toString().trim()));
        params.put("Email", email.getText().toString().trim());

        RequestHandler requestHandler = new RequestHandler();
        String sd = requestHandler.sendPostRequest(MainActivity.URL_LoginAccount, params);
        sd = sd.replaceAll(" ","");

        if(Integer.parseInt(sd) > 0) {
            errorview.setVisibility(View.INVISIBLE);
            ManageData.Account = new LinkedHashMap<>();
            ArrayList list;
            Gson gson = new Gson();
            String json = requestHandler.sendGetRequest(MainActivity.URL_GetAccount + sd);
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            list = gson.fromJson(json, type);

            ManageData.Account.put("id", sd);
            ManageData.Account.put("Benutzername", list.get(1).toString());
            ManageData.Account.put("Email", list.get(2).toString());
            ManageData.Account.put("Sprache", list.get(3).toString());
            ManageData.setOnlineAccount(true);
            Log.d("Account", ManageData.Account + "");
            return true;
        }else{
            errorview.setVisibility(View.VISIBLE);
            errorview.setText("Unter diesen Daten konnte kein Account gefunden werden");
            login.setEnabled(false);
            return false;
        }

    }


    //Buttton OnClick Methoden
    public void open_login(){

            try {
                if (ManageData.OfflineDataLeft() == false) {
                    boolean con = login();
                    if (con == true) {
                        open_next();
                    }
                } else {
                    boolean con = login();
                    if (con == true) {
                        MainActivity.hideKeyboard(this);
                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        Back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view1.setVisibility(View.VISIBLE);
                                view2.setVisibility(View.INVISIBLE);
                                open_back();
                            }
                        });

                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean con = login();
                                if (con == true) {
                                    ManageData.setOnlineAccount(true);
                                    ManageData.setOfflineAccount(2);
                                    view1.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.INVISIBLE);
                                    Log.d("Login", "Accept");
                                    open_accept();
                                } else {
                                    open_back();
                                }
                            }
                        });

                        decline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean con = login();
                                if (con == true) {
                                    ManageData.setOnlineAccount(true);
                                    ManageData.setOfflineAccount(2);
                                    view1.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.INVISIBLE);
                                    VocabularyMethods.vocabularylists.clear();
                                    FirstScreen.tinyDB.remove("VocLists");
                                    ManageData.saveVocabularyLists();
                                    open_next();
                                    Log.d("Login", "Decline");
                                } else {
                                    open_back();
                                }
                            }
                        });
                    } else {
                        open_back();
                    }
                }
            } catch (Exception e) {
                errorview.setVisibility(View.VISIBLE);
                Log.d("Login", "Kein Internet Verfügbar");
                errorview.setText("Es konnte keine Verbindung mit dem Internet hergestellt werden.");

                AlertDialog.Builder builder = new AlertDialog.Builder(FirstScreen.this);
                builder.setCancelable(true);
                builder.setNegativeButton("Zurück", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setTitle("Keine Netzwerkverbindung");
                builder.setMessage("Es konnte keine Verbindung mit dem Internet hergestellt werden.");
                builder.setPositiveButton("Erneut Versuchen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        open_login();
                    }
                });
                builder.show();
            }
    }

    public void open_next(){
        ManageData.setOnlineAccount(true);
        ManageData.setOfflineAccount(2);
        VocabularyMethods.vocabularylists = null;
        errorview.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void open_accept(){
        ManageData.setOnlineAccount(true);
        ManageData.setOfflineAccount(2);
        for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
            VocabularyMethods.vocabularylists.get(i).setSource(true);
            ManageData.saveVocabularyLists();
            ManageData.uploadVocabularyList(VocabularyMethods.vocabularylists.get(i), context);
        }
        VocabularyMethods.vocabularylists = null;
        errorview.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    //Buttton OnClick Methoden
    public void open_continue1(){
        findViewById(R.id.first_scrollview).setVisibility(View.INVISIBLE);
        findViewById(R.id.first_scrollview1).setVisibility(View.VISIBLE);
    }

    public void open_continue2(){
        Log.d("SetOfflineAccount", "1"+ "       --------------------------------------------------------");
        ManageData.setOfflineAccount(1);
        ManageData.setOnlineAccount(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void open_register(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void open_back(){
        ManageData.RemoveOfflineData();
        ManageData.OfflineAccount = 0;
        ManageData.setOnlineAccount(false);
        findViewById(R.id.first_scrollview1).setVisibility(View.GONE);
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
    }
}
