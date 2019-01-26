package learningunit.learningunit.BeforeStart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.PublicAPIs.TinyDB;
import learningunit.learningunit.R;

public class FirstScreen extends AppCompatActivity {

    //Deklarieren der Knöpfe
    private Button continue1, continue2, login, register, back;
    public static TinyDB tinyDB;
    public static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(this);
        sharedPreferences = getSharedPreferences("variables", MODE_PRIVATE);
        ManageData.loadOfflineAccount();
        ManageData.loadOnlineAccount();
        if(ManageData.OfflineAccount == 0) {
            if(ManageData.Account == null) {
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
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


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

    public void open_login(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void open_register(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void open_back(){
        findViewById(R.id.first_scrollview).setVisibility(View.VISIBLE);
        findViewById(R.id.first_scrollview1).setVisibility(View.INVISIBLE);
    }




}
