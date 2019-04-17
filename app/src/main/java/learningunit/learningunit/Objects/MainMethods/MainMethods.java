package learningunit.learningunit.Objects.MainMethods;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.PublicAPIs.RequestHandler;
import learningunit.learningunit.R;
import learningunit.learningunit.menu.MainActivity;

public class MainMethods {

    public static void open_RemoveAds(final Activity activity){
        Button RemoveAds = (Button) activity.findViewById(R.id.main_accountPremium);
        RemoveAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.findViewById(R.id.main_accountLayout).setVisibility(View.GONE);
                activity.findViewById(R.id.main_premiumLayout).setVisibility(View.VISIBLE);
                final TextView error = (TextView) activity.findViewById(R.id.main_premiumError);
                error.setVisibility(View.GONE);
                MainActivity.backLocation = 5;
                Button premiumBack = (Button) activity.findViewById(R.id.main_premiumBack);
                Button premiumContinue = (Button) activity.findViewById(R.id.main_premiumContinue);
                final EditText editPremium = (EditText) activity.findViewById(R.id.main_premiumEditText);
                editPremium.setText("");
                premiumBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.findViewById(R.id.main_accountLayout).setVisibility(View.VISIBLE);
                        activity.findViewById(R.id.main_premiumLayout).setVisibility(View.GONE);
                    }
                });
                premiumContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ManageData.InternetAvailable(activity)){
                            if(editPremium.getText().toString().trim().equalsIgnoreCase(ManageData.getUserAdID() + "")){
                                error.setVisibility(View.VISIBLE);
                                error.setText(activity.getResources().getString(R.string.OwnAdID));
                            }else {
                                MainActivity.hideKeyboard(activity);
                                RequestHandler requestHandler = new RequestHandler();
                                String sd = requestHandler.sendGetRequest(MainActivity.URL_InsertPremium + ManageData.getUserID() + "&shareid=" + editPremium.getText().toString().trim());
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                                ArrayList<String> eg = gson.fromJson(sd, type);
                                if(eg.get(0).equalsIgnoreCase("True")){
                                    ManageData.setPremium(true);
                                    Intent intent = new Intent(activity, MainActivity.class);
                                    activity.startActivity(intent);
                                }else if(eg.get(0).equalsIgnoreCase("Not available")){
                                    error.setVisibility(View.VISIBLE);
                                    error.setText(activity.getResources().getString(R.string.AdIDAEntered));
                                }else if(eg.get(0).equalsIgnoreCase("no id")){
                                    error.setVisibility(View.VISIBLE);
                                    error.setText(activity.getResources().getString(R.string.AdIDNotFound));
                                }else{
                                    error.setVisibility(View.VISIBLE);
                                    error.setText("sever error");
                                }
                            }
                        }else{
                            error.setText(activity.getResources().getString(R.string.NoNetworkConnection));
                        }
                    }
                });
            }
        });
    }
}
