package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import learningunit.learningunit.Objects.API.OnBackPressedListener;
import learningunit.learningunit.R;
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
                        ConstraintLayout Gradesub = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSubSelection);
                        Graderoot.setVisibility(View.GONE);
                        Gradesub.setVisibility(View.VISIBLE);
                        Button Opt1 = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Gradefragment_organizer_home_GradeSubSelection_Opt1);
                        Button Opt2 = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Gradefragment_organizer_home_GradeSubSelection_Opt2);
                        Opt2.setText(activity.getResources().getString(R.string.New) + " " + activity.getResources().getString(R.string.Certificate));
                        Opt1.setText(activity.getResources().getString(R.string.Certificate) + " " + activity.getResources().getString(R.string.Overview));
                        back = 2;
                    }
                });


            }
        });

    }



































}
