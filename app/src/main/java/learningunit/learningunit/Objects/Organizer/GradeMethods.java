package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import learningunit.learningunit.Objects.API.OnBackPressedListener;
import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.menu.organizer.Organizer;

public class GradeMethods extends AppCompatActivity {

    private int back = 0;

    public GradeMethods(final View fragmentView, final Activity activity){
        GradeClick(fragmentView, activity);
    }

    private void Back(View fragmentView, Activity activity){
        ConstraintLayout root = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
        ConstraintLayout Graderoot = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSelection);
        ConstraintLayout Gradesub = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSubSelection);

        if(back == 0){
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }else if(back == 1){
            root.setVisibility(View.VISIBLE);
            Graderoot.setVisibility(View.GONE);
            back = 0;
        }else if(back == 2) {
            Graderoot.setVisibility(View.VISIBLE);
            Gradesub.setVisibility(View.GONE);
            fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificateScrollView).setVisibility(View.GONE);
            EditText edit1 = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_edit1);
            EditText edit2 = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_edit2);
            edit1.setText("");
            edit2.setText("");
            back = 1;
        }
    }


    public void GradeClick(final View fragmentView, final Activity activity){
        final Button Grade = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Grade);
        Grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Back(fragmentView, activity);
                    }
                });

                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Back(fragmentView, activity);
                    }
                });



                ConstraintLayout root = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
                final ConstraintLayout Graderoot = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSelection);
                root.setVisibility(View.GONE);
                Graderoot.setVisibility(View.VISIBLE);
                back = 1;

                Button cert = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Grade_Certificate);
                cert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ConstraintLayout Gradesub = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSubSelection);
                        Graderoot.setVisibility(View.GONE);
                        Gradesub.setVisibility(View.VISIBLE);
                        Button Opt1 = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Gradefragment_organizer_home_GradeSubSelection_Opt1);
                        Button Opt2 = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Gradefragment_organizer_home_GradeSubSelection_Opt2);
                        Opt2.setText(activity.getResources().getString(R.string.New) + " " + activity.getResources().getString(R.string.Certificate));
                        Opt1.setText(activity.getResources().getString(R.string.Certificate) + " " + activity.getResources().getString(R.string.Overview));
                        back = 2;

                        Opt2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final ScrollView newCert = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificateScrollView);
                                final EditText edit1 = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_edit1);
                                final EditText edit2 = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_edit2);
                                TextView newCertHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_heading);
                                newCert.setVisibility(View.VISIBLE);
                                Gradesub.setVisibility(View.GONE);
                                newCertHeading.setText(activity.getResources().getString(R.string.Certificate) + " " + activity.getResources().getString(R.string.Overview));

                                Button finish = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_finish);
                                finish.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(edit1.getText().toString().trim().equals("")){
                                            edit1.setError(activity.getResources().getString(R.string.EmptyField));
                                        }else if(edit2.getText().toString().trim().equals("")){
                                            edit2.setError(activity.getResources().getString(R.string.EmptyField));
                                        }else{
                                            Certificate cert = new Certificate(edit1.getText().toString(), edit2.getText().toString());
                                            newCert.setVisibility(View.GONE);
                                            Gradesub.setVisibility(View.VISIBLE);

                                            Gson gson = new Gson();
                                            String json1 = FirstScreen.tinyDB.getString("Certificates");
                                            ArrayList<Certificate> certlist = new ArrayList<Certificate>();
                                            if(json1.equals("")){
                                                certlist = new ArrayList<Certificate>();
                                            }else {
                                                Type type = new TypeToken<ArrayList<Certificate>>() {
                                                }.getType();
                                                certlist = gson.fromJson(json1, type);
                                            }

                                            certlist.add(cert);
                                            FirstScreen.tinyDB.putString("Certificates", gson.toJson(certlist));
                                        }
                                    }
                                });

                                Button back = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_back);
                                back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        edit1.setText("");
                                        edit2.setText("");
                                        newCert.setVisibility(View.GONE);
                                        Gradesub.setVisibility(View.VISIBLE);
                                    }
                                });

                            }
                        });
                    }
                });


            }
        });

    }



































}
