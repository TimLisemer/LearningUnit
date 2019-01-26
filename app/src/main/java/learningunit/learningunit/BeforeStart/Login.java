package learningunit.learningunit.BeforeStart;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.API.RequestHandler;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;
import learningunit.learningunit.R;

public class Login extends AppCompatActivity {

    //Deklarieren der Knöpfe
    private Button login, back, Back, accept, decline;
    private EditText email, editpassword1;
    private int ready1, ready, ready2;
    private TextView errorview;
    private ConstraintLayout view1, view2;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ManageData.Account = null;
        context = getApplicationContext();
        view1 = (ConstraintLayout) findViewById(R.id.login_ScrollView1);
        view2 = (ConstraintLayout) findViewById(R.id.login_ScrollView2);
        Back = (Button) findViewById(R.id.login_back1);
        accept = (Button) findViewById(R.id.login_accept);
        decline = (Button) findViewById(R.id.login_decline);

        //Initialisieren der Knöpfe und rufen der OnClick methode
        login = (Button) findViewById(R.id.login_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_login();
            }
        });

        back = (Button) findViewById(R.id.login_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back();
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
                ready2 = 1;
                String inhalt = email.getText().toString().trim();
                if(!inhalt.isEmpty()){
                    ready = 1;
                }else{
                    ready = 0;
                }
                if(ready == 1 && ready1 == 1 && ready2 == 1){
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
                ready2 = 1;
                String inhalt = editpassword1.getText().toString().trim();
                if(!inhalt.isEmpty()){
                    ready1 = 1;
                }else{
                    ready1 = 0;
                }
                if(ready == 1 && ready1 == 1 && ready2 == 1){
                    login.setEnabled(true);
                }else{
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public boolean login(){

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
            ready2 = 0;
            login.setEnabled(false);
            return false;
        }

    }


    //Buttton OnClick Methoden
    public void open_login(){

        try{
            boolean con = login();
            if (con == true) {
                if(ManageData.OfflineDataLeft() == false){
                    open_next();
                }else{
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
                            ManageData.setOnlineAccount(true);
                            ManageData.setOfflineAccount(2);
                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.INVISIBLE);
                            open_accept();
                        }
                    });

                    decline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.INVISIBLE);
                            VocabularyMethods.vocabularylists = null;
                            open_next();
                        }
                    });
                }
            }
        }catch (Exception e){
            errorview.setVisibility(View.VISIBLE);
            Log.d("Login", "Kein Internet Verfügbar");
            errorview.setText("Es konnte keine Verbindung mit dem Internet hergestellt werden.");
            Log.d("Error", e.toString());
        }
    }

    public void open_next(){
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

    public void open_back(){
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);
    }
}
