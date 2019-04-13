package learningunit.learningunit.beforeStart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import learningunit.learningunit.Objects.PublicAPIs.AnalyticsApplication;
import learningunit.learningunit.Objects.Timetable.HourList;
import learningunit.learningunit.Objects.Timetable.Week;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.PublicAPIs.RequestHandler;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;
import learningunit.learningunit.R;

public class Register extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    //Deklarieren der Knöpfe
    private Button register, back, Back1, accept, decline;
    private CheckBox confirm;
    private EditText username, email, editpassword1, editpassword2;
    private int ready, ready1, ready2, ready3, ready4;
    private TextView error;
    private ConstraintLayout view1, view2;
    private int backLocation = 0;

    private void hide(){
        MainActivity.hideKeyboard(this);
    }

    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(ManageData.Account == null){
            ManageData.Account = new LinkedHashMap<String, String>();
        }
        ManageData.Account.clear();

        //Initialisieren der Knöpfe und rufen der OnClick methode
        Back1 = (Button) findViewById(R.id.register_back1);
        accept = (Button) findViewById(R.id.register_accept);
        decline = (Button) findViewById(R.id.register_decline);
        error = (TextView) findViewById(R.id.register_error);
        view1 = (ConstraintLayout) findViewById(R.id.register_ScrollView1);
        view2 = (ConstraintLayout) findViewById(R.id.register_ScrollView2);
        register = (Button) findViewById(R.id.register_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_register();
            }
        });

        back = (Button) findViewById(R.id.register_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back();
            }
        });

        confirm = (CheckBox) findViewById(R.id.register_checkBox);

        confirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hide();
                if(confirm.isChecked()){
                    ready4 = 1;
                }else{
                    ready4 = 0;
                }
                if(ready == 1 && ready1 == 1 && ready2 == 1 && ready3 == 1 && ready4 == 1){
                    register.setEnabled(true);
                }else{
                    register.setEnabled(false);
                }
            }
        });




        username = (EditText) findViewById(R.id.register_username);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inhalt = username.getText().toString().trim();
                editpassword1.setError(null);
                editpassword2.setError(null);
                username.setError(null);
                email.setError(null);
                if(!inhalt.isEmpty()){
                    ready = 1;
                }else{
                    ready = 0;
                }
                if(ready == 1 && ready1 == 1 && ready2 == 1 && ready3 == 1 && ready4 == 1){
                    register.setEnabled(true);
                }else{
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email = (EditText) findViewById(R.id.register_email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editpassword1.setError(null);
                editpassword2.setError(null);
                username.setError(null);
                email.setError(null);
                String inhalt = email.getText().toString().trim();
                if(!inhalt.isEmpty()){
                    ready1 = 1;
                }else{
                    ready1 = 0;
                }
                if(ready == 1 && ready1 == 1 && ready2 == 1 && ready3 == 1 && ready4 == 1){
                    register.setEnabled(true);
                }else{
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editpassword1 = (EditText) findViewById(R.id.register_password);
        editpassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editpassword1.setError(null);
                editpassword2.setError(null);
                username.setError(null);
                email.setError(null);
                String inhalt = editpassword1.getText().toString().trim();
                if(!inhalt.isEmpty()){
                    ready2 = 1;
                }else{
                    ready2 = 0;
                }
                if(ready == 1 && ready1 == 1 && ready2 == 1 && ready3 == 1 && ready4 == 1){
                    register.setEnabled(true);
                }else{
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editpassword2 = (EditText) findViewById(R.id.register_password1);
        editpassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editpassword1.setError(null);
                editpassword2.setError(null);
                username.setError(null);
                email.setError(null);
                String inhalt = editpassword2.getText().toString().trim();
                if(!inhalt.isEmpty()){
                    ready3 = 1;
                }else{
                    ready3 = 0;
                }
                if(ready == 1 && ready1 == 1 && ready2 == 1 && ready3 == 1 && ready4 == 1){
                    register.setEnabled(true);
                }else{
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static String md5(String passwordToHash) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    public int Register(){
        MainActivity.hideKeyboard(Register.this);
        String Username = username.getText().toString().trim(), Email = email.getText().toString().trim(), password1 = md5(editpassword1.getText().toString().trim()), password2 = md5(editpassword2.getText().toString().trim());
        if(password1.equals(password2)){
            if(confirm.isChecked() == true){

                LinkedHashMap<String, String> params = new LinkedHashMap <>();
                params.put("Benutzername", Username);
                params.put("Passwort", password1);
                params.put("Email", Email);
                params.put("Sprache", 1 + "");

                RequestHandler requestHandler = new RequestHandler();
                String sd = requestHandler.sendPostRequest(MainActivity.URL_CreateAccount, params);

                if(sd.contains("---")){
                    Log.d("sd", sd);
                    String[] parts = sd.split("---");
                    String error = parts[1];
                    if(parts[0].equals(" E")){
                        if (error.contains("Benutzernamen")){
                            username.setError(error);
                        }else if (error.contains("E-Mail")){
                            email.setError(error);
                        }else{
                            username.setError("Wohin gehörst du");
                            email.setError(error);
                        }
                        return 0;
                    }else{
                        Log.d("No", "E");
                        return 0;
                    }
                }else {
                    sd = sd.replaceAll(" ","");
                    ManageData.Account = new LinkedHashMap<>();
                    ArrayList list;
                    Gson gson = new Gson();
                    String json = requestHandler.sendGetRequest(MainActivity.URL_GetAccount + sd);
                    Type type = new TypeToken<ArrayList<String>>() {}.getType();
                    list = gson.fromJson(json, type);

                    ManageData.Account.put("id", sd);
                    ManageData.Account.put("Benutzername", list.get(1).toString());
                    ManageData.Account.put("Email", list.get(2).toString());
                    ManageData.Account.put("Sprache", list.get(3).toString());
                    ManageData.setOnlineAccount(true);
                    Log.d("Account", ManageData.Account + "");
                    return 1;
                }

            }else{
                Log.d("No", "AGBS");
                return 0;
            }
        }else{
            editpassword1.setError("Passwoerter stimmen nicht überein");
            editpassword2.setError("Passwoerter stimmen nicht überein");
            Log.d("No", "Passwoerter stimmen nicht überein");
            Log.d("Ps1", password1);
            Log.d("Ps2", password2);
            return 0;
        }
    }








    public void open_register(){
        try {
            ManageData.setOnlineAccount(true);
            ManageData.setOfflineAccount(2);
            error.setVisibility(View.GONE);
            if (ManageData.OfflineDataLeft() == false) {
                int con = Register();
                if(con > 0) {
                    open_next();
                }
            } else {
                MainActivity.hideKeyboard(this);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                backLocation = 1;

                Back1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.INVISIBLE);
                        open_back1();
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int con = Register();
                        if(con > 0) {
                            ManageData.setOnlineAccount(true);
                            ManageData.setOfflineAccount(2);
                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.INVISIBLE);
                            backLocation = 0;
                                    open_accept();
                            Log.d("Register", "Accept");
                        }else {
                            open_back1();
                            Log.d("No", "Registrierungs fehler");
                        }
                    }
                });

                decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int con = Register();
                        if(con > 0) {
                            ManageData.setOnlineAccount(true);
                            ManageData.setOfflineAccount(2);
                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.INVISIBLE);
                            VocabularyMethods.vocabularylists.clear();
                            FirstScreen.tinyDB.remove("VocLists");
                            ManageData.saveVocabularyLists();
                            backLocation = 0;
                            open_next();
                            Log.d("Register", "Decline");
                        }else {
                            open_back1();
                            Log.d("No", "Registrierungs fehler");
                        }
                    }
                });
            }
        }catch (Exception e){
            error.setVisibility(View.VISIBLE);
            Log.d("Register", "Kein Internet Verfügbar");
            error.setText("Es konnte keine Verbindung mit dem Internet hergestellt werden.");

            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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
                    open_register();
                }
            });
            builder.show();
        }
    }


    public void open_next(){
        ManageData.setOnlineAccount(true);
        ManageData.setOfflineAccount(2);
        VocabularyMethods.vocabularylists = null;
        FirstScreen.tinyDB.putString("WeekA", "");
        FirstScreen.tinyDB.putString("WeekB", "");
        error.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void open_accept(){
        ManageData.setOnlineAccount(true);
        ManageData.setOfflineAccount(2);
        for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
            VocabularyMethods.vocabularylists.get(i).setSource(true);
            ManageData.saveVocabularyLists();
            ManageData.uploadVocabularyList(VocabularyMethods.vocabularylists.get(i), Register.this);
        }
        VocabularyMethods.vocabularylists = null;

        Gson gson = new Gson();
        String jsona = FirstScreen.tinyDB.getString("WeekA");
        String jsonb = FirstScreen.tinyDB.getString("WeekA");
        Type type = new TypeToken<Week>() {}.getType();
        if (jsona.equalsIgnoreCase("")) {
            try {
                if (jsonb.equalsIgnoreCase("")) {
                    HourList.noConnection(true, Register.this, (Week) gson.fromJson(jsona, type), null, false);
                } else {
                    HourList.noConnection(false, Register.this, (Week) gson.fromJson(jsona, type), (Week) gson.fromJson(jsonb, type), false);
                }
            }catch(Exception e){}
        }

        error.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void open_back(){
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);
    }

    public void open_back1(){
        ManageData.RemoveOfflineData();
        ManageData.OfflineAccount = 0;
        ManageData.setOnlineAccount(false);
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
        backLocation = 0;
    }

    @Override
    public void onBackPressed(){
        if(backLocation == 1){
            open_back1();
        }else{
            open_back();
        }

    }
























}
