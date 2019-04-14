package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.API.OnBackPressedListener;
import learningunit.learningunit.Objects.Timetable.Day;
import learningunit.learningunit.Objects.Timetable.Hour;
import learningunit.learningunit.Objects.Timetable.Week;
import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.menu.organizer.Organizer;

public class HomeFragmentMethods extends AppCompatActivity {

    public static int startCondition = 0; //0 = normal 1 = home 2 = exam 3 = pres
    int fday, fmonth, fyear;
    Hour Selected;
    public static Boolean HourChosen = false;
    public static Button downHour[];
    public static Button downHour1[];

    public HomeFragmentMethods(final View fragmentView, final Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Invalid activity --> Null");
        }
        start(fragmentView, activity);
        if(startCondition == 1) {
            HomeworkClick(fragmentView, activity);
        }else if(startCondition == 2) {
            ExamClick(fragmentView, activity);
        }else if(startCondition == 3) {
            PresentationClick(fragmentView, activity);
        }

        Button Opt1 = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Gradefragment_organizer_home_GradeSubSelection_Opt1);
        Opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Overview(fragmentView, activity, 2, false);
            }
        });
    }



    private void start(final View fragmentView, final Activity activity){
        MainActivity.hideKeyboard(activity);
        GradeClick(fragmentView, activity);
        Button pre = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Presentation);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PresentationClick(fragmentView, activity);
            }
        });

        Button exam = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_arbeiten);
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamClick(fragmentView, activity);
            }
        });

        Button homework = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_hausaufgaben);
        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeworkClick(fragmentView, activity);
            }
        });

        Button GradeExam = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Grade_Exam);
        GradeExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Overview(fragmentView, activity, 1, true);
            }
        });

        Button GradePresentation = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Grade_Presentation);
        GradePresentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Overview(fragmentView, activity, 3, true);
            }
        });
    }



    private void PresentationClick(final View fragmentView, final Activity activity){
        startCondition = 0;
        MainActivity.hideKeyboard(activity);
        final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
        final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);

        Button overview = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_OverviewSelection);
        Button newOne = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_CreateSelection);
        overview.setText(activity.getResources().getString(R.string.PresentationOverview));
        newOne.setText(activity.getResources().getString(R.string.CreateNewPresentation));

        newOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewEventPresentation(fragmentView, activity);
            }
        });

        Selection.setVisibility(View.VISIBLE);
        MainLayout.setVisibility(View.GONE);

        final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
        TopBack.setText(activity.getResources().getString(R.string.Back));
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                Selection.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });


        Button ExamOverview = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_OverviewSelection);
        ExamOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Overview(fragmentView, activity, 3, false);
            }
        });
    }








    private void ExamClick(final View fragmentView, final Activity activity){
        startCondition = 0;
        MainActivity.hideKeyboard(activity);
        final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
        final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);

        Button overview = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_OverviewSelection);
        Button newOne = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_CreateSelection);
        overview.setText(activity.getResources().getString(R.string.ExamOverview));
        newOne.setText(activity.getResources().getString(R.string.EnterNewExam));

        newOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewEventExam(fragmentView, activity);
            }
        });

        Selection.setVisibility(View.VISIBLE);
        MainLayout.setVisibility(View.GONE);

        final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
        TopBack.setText(activity.getResources().getString(R.string.Back));
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                Selection.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });


        Button ExamOverview = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_OverviewSelection);
        ExamOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Overview(fragmentView, activity, 1, false);
            }
        });

    }


    private void HomeworkClick(final View fragmentView, final Activity activity){
        startCondition = 0;
        MainActivity.hideKeyboard(activity);
        final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
        final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
        Selection.setVisibility(View.VISIBLE);
        MainLayout.setVisibility(View.GONE);

        Button overview = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_OverviewSelection);
        Button newOne = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_CreateSelection);
        overview.setText(activity.getResources().getString(R.string.HomeworkOverview));
        newOne.setText(activity.getResources().getString(R.string.EnterNewHomework));

        final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
        TopBack.setText(activity.getResources().getString(R.string.Back));
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                Selection.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Button OverviewHomework = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_OverviewSelection);
        OverviewHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Overview(fragmentView, activity, 0, false);
            }
        });

        newOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewEventHomework(fragmentView, activity);
            }
        });
    }

    private void ContinueMethod(final View fragmentView, Homework home, Exam exam, Presentation pre, final Activity activity, final int Case){ //Case = 0 --> Homework 1 --> Exam

        HourChosen = false;
        Selected = null;
        final TextView hourinfo = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);
        final ImageView hourimage = (ImageView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourSelection_ImageView);
        hourimage.setVisibility(View.GONE);
        hourinfo.setText(activity.getResources().getString(R.string.NoHourNameChosen));

        if(ManageData.OfflineAccount == 2){
            if(ManageData.InternetAvailable(activity)) {
                ManageData.LoadTimetable(false, ManageData.getUserID(), activity, false);
            }else{
                Toast.makeText(activity, activity.getResources().getString(R.string.NoNetworkConnection), Toast.LENGTH_SHORT).show();
            }
        }

        final ArrayList<Hour> hourList = new ArrayList<Hour>();
        if(!(FirstScreen.tinyDB.getString("WeekA").equals(""))) {
            Gson gson = new Gson();
            String json = FirstScreen.tinyDB.getString("WeekA");
            Type type = new TypeToken<Week>() {
            }.getType();

            Week weekA = gson.fromJson(json, type);
            weekA.getDayList().get(0).getHourList();
            for (Day d : weekA.getDayList()) {
                for (Hour h : d.getHourList()) {
                    boolean multi = false;
                    for (Hour hour : hourList) {
                        if (hour.getName().equals(h.getName()) && hour.getColorCode().equals(h.getColorCode())) {
                            multi = true;
                            break;
                        }
                    }
                    if (!multi) {
                        hourList.add(h);
                    }
                }
            }

            if (!(FirstScreen.tinyDB.getString("WeekB").equals(""))) {
                json = FirstScreen.tinyDB.getString("WeekB");
                Week weekB = gson.fromJson(json, type);
                weekB.getDayList().get(0).getHourList();
                for (Day d : weekB.getDayList()) {
                    for (Hour h : d.getHourList()) {
                        boolean multi = false;
                        for (Hour hour : hourList) {
                            if (hour.getName().equals(h.getName()) && hour.getColorCode().equals(h.getColorCode())) {
                                multi = true;
                                break;
                            }
                        }
                        if (!multi) {
                            hourList.add(h);
                        }
                    }
                }
            }
        }

        fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
        TextView heading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Heading);
        TextView titelheading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Titel);
        TextView descheading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Info);

        EditText edittitel = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_TitelInput);
        EditText editdesc = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_DescriptionInput);
        TextView DateShowcase = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Date);

        if(Case == 0){
            heading.setText(activity.getResources().getString(R.string.EnterNewHomework));
            titelheading.setText(activity.getResources().getString(R.string.Titel));
            descheading.setText(activity.getResources().getString(R.string.Description));
            edittitel.setHint(activity.getResources().getString(R.string.Titel));
            editdesc.setHint(activity.getResources().getString(R.string.Description));
            DateShowcase.setText(home.getDay() + "." + home.getMonth() + "." + home.getYear());
        }else if(Case == 1){
            heading.setText(activity.getResources().getString(R.string.EnterNewExam));
            titelheading.setText(activity.getResources().getString(R.string.Titel));
            descheading.setText(activity.getResources().getString(R.string.Description));
            edittitel.setHint(activity.getResources().getString(R.string.Titel));
            editdesc.setHint(activity.getResources().getString(R.string.Description));
            DateShowcase.setText(exam.getDay() + "." + exam.getMonth() + "." + exam.getYear());
        }else if(Case == 3){
            heading.setText(activity.getResources().getString(R.string.CreateNewPresentation));
            titelheading.setText(activity.getResources().getString(R.string.Titel));
            descheading.setText(activity.getResources().getString(R.string.Description));
            edittitel.setHint(activity.getResources().getString(R.string.Titel));
            editdesc.setHint(activity.getResources().getString(R.string.Description));
            DateShowcase.setText(pre.getDay() + "." + pre.getMonth() + "." + pre.getYear());
        }

        final EditText titel = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_TitelInput);
        final EditText description = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_DescriptionInput);
        titel.setText("");
        description.setText("");

        Button HourSelection = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourSelection);
        Button Back = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
                fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                if(Case == 0) {
                    NewEventHomework(fragmentView, activity);
                }else if(Case == 1) {
                    NewEventExam(fragmentView, activity);
                }else if(Case == 3){
                    NewEventPresentation(fragmentView, activity);
                }
            }
        });


        hourinfo.setError(null);
        titel.setError(null);
        description.setError(null);

        final Button Next = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
                if(!HourChosen){
                    hourinfo.setError(activity.getResources().getString(R.string.NoHourNameChosen));
                }else if(titel.getText().toString().trim().equals("")){
                    titel.setError(activity.getResources().getString(R.string.EmptyField));
                }else if(description.getText().toString().trim().equals("")){
                    description.setError(activity.getResources().getString(R.string.EmptyField));
                }else{

                }
            }
        });

        if(Case == 0) {
            HourSelection(hourList, Case, fragmentView, activity, HourSelection, home, null, null);
        }else if(Case == 1){
            HourSelection(hourList, Case, fragmentView, activity, HourSelection, null, exam, null);
        }else if(Case == 3){
            HourSelection(hourList, Case, fragmentView, activity, HourSelection, null, null, pre);
        }
    }


    private void HourSelection(final ArrayList<Hour> hourList, final int Case, final View fragmentView, final Activity activity, Button HourSelection, final Homework home, final Exam e, final Presentation pre){ //Case: 0 = Homework, 1 = Exam, 2 = Certificate, 3 = Presentation
        final TextView hourinfo = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);
        final ImageView hourimage = (ImageView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourSelection_ImageView);
        final EditText Custom = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_TextInput);
        MainActivity.hideKeyboard(activity);
        Custom.setText("");
        HourSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
                downHour = null;
                downHour1 = null;

                if (Case == 0) {
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.VISIBLE);
                }else if(Case == 1){
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.VISIBLE);
                }else if(Case == 3){
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.VISIBLE);
                }

                Button Base1 = fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN_Base1);
                Button Base2 = fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN_Base2);

                if (hourList.isEmpty()) {
                    Base1.setVisibility(View.GONE);
                    Base2.setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN_timetableInfo).setVisibility(View.VISIBLE);
                } else {
                    Base1.setVisibility(View.VISIBLE);
                    Base2.setVisibility(View.VISIBLE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN_timetableInfo).setVisibility(View.GONE);

                    downHour = new Button[hourList.size()];
                    downHour1 = new Button[hourList.size()];

                    Base1.setText(hourList.get(0).getName());
                    ColorDrawable c = new ColorDrawable();
                    c.setColor(android.graphics.Color.parseColor(hourList.get(0).getColorCode()));
                    Base1.setBackground(c);
                    if (hourList.get(0).getColorCode().equals("#000000")) {
                        Base1.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                    } else {
                        Base1.setTextColor(android.graphics.Color.parseColor("#000000"));
                    }
                    Base2.setText(hourList.get(1).getName());
                    ColorDrawable c1 = new ColorDrawable();
                    c1.setColor(android.graphics.Color.parseColor(hourList.get(1).getColorCode()));
                    Base2.setBackground(c1);
                    if (hourList.get(1).getColorCode().equals("#000000")) {
                        Base2.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                    } else {
                        Base2.setTextColor(android.graphics.Color.parseColor("#000000"));
                    }

                    ConstraintSet constraintSet1, constraintSet;
                    Boolean l = true, l1 = true;
                    ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);

                    int h = 0;

                    for (int ii = 2; ii < hourList.size(); ii++) {
                        if (ii % 2 == 0) {
                            if (downHour[ii] == null) {
                                downHour[ii] = new Button(activity);
                            }

                            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                            params.setMargins(24, 16, 24, 8);

                            downHour[ii].setText(hourList.get(ii).getName());
                            downHour[ii].setId(ii);

                            ColorDrawable c2 = new ColorDrawable();
                            c2.setColor(android.graphics.Color.parseColor(hourList.get(ii).getColorCode()));
                            downHour[ii].setBackground(c2);
                            if (hourList.get(ii).getColorCode().equals("#000000")) {
                                downHour[ii].setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                            } else {
                                downHour[ii].setTextColor(android.graphics.Color.parseColor("#000000"));
                            }


                            layout.addView(downHour[ii], params);

                            constraintSet = new ConstraintSet();
                            constraintSet.clone(layout);
                            if (l == true) {
                                constraintSet.connect(ii, ConstraintSet.TOP, Base1.getId(), ConstraintSet.BOTTOM);

                                constraintSet.connect(ii, ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                                constraintSet.connect(ii, ConstraintSet.LEFT, fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Guideline2).getId(), ConstraintSet.RIGHT);
                                l = false;
                            } else {
                                constraintSet.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                constraintSet.connect(ii, ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                                constraintSet.connect(ii, ConstraintSet.LEFT, fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Guideline2).getId(), ConstraintSet.RIGHT);
                            }

                            constraintSet.applyTo(layout);
                            h = ii + 1;
                            final int gh = ii;

                            downHour[ii].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                                    for (int i = 0; i < downHour.length; i++) {
                                        layout.removeView(downHour[i]);
                                    }
                                    for (int i = 0; i < downHour1.length; i++) {
                                        layout.removeView(downHour1[i]);
                                    }

                                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                                    Selected = hourList.get(gh);
                                    HourChosen = true;
                                    hourinfo.setText(Selected.getName());
                                    hourimage.setVisibility(View.VISIBLE);
                                    ColorDrawable ic = new ColorDrawable();
                                    ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                                    hourimage.setBackground(ic);
                                    if(Case == 0){
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        if(Selected != null) {
                                            Finish(fragmentView, activity, home,null, null, Case );
                                        }
                                    }else if(Case == 1){
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        if(Selected != null) {
                                            Finish(fragmentView, activity,null, e, null, Case );
                                        }
                                    }else if(Case == 3){
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        if(Selected != null) {
                                            Finish(fragmentView, activity,null, e, pre, Case );
                                        }
                                    }
                                }
                            });

                        } else {

                            if (downHour1[ii] == null) {
                                downHour1[ii] = new Button(activity);
                            }
                            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                            params.setMargins(24, 16, 24, 8);

                            downHour1[ii].setText(hourList.get(ii).getName());
                            downHour1[ii].setId(ii);

                            ColorDrawable c2 = new ColorDrawable();
                            c2.setColor(android.graphics.Color.parseColor(hourList.get(ii).getColorCode()));
                            downHour1[ii].setBackground(c2);
                            if (hourList.get(ii).getColorCode().equals("#000000")) {
                                downHour1[ii].setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                            } else {
                                downHour1[ii].setTextColor(android.graphics.Color.parseColor("#000000"));
                            }

                            layout.addView(downHour1[ii], params);


                            constraintSet1 = new ConstraintSet();
                            constraintSet1.clone(layout);
                            if (l1 == true) {
                                constraintSet1.connect(ii, ConstraintSet.TOP, Base2.getId(), ConstraintSet.BOTTOM);

                                constraintSet1.connect(ii, ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                                constraintSet1.connect(ii, ConstraintSet.RIGHT, fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Guideline2).getId(), ConstraintSet.LEFT);
                                l1 = false;
                            } else {
                                constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                constraintSet1.connect(ii, ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                                constraintSet1.connect(ii, ConstraintSet.RIGHT, fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Guideline2).getId(), ConstraintSet.LEFT);
                            }

                            constraintSet1.applyTo(layout);
                            h = ii + 1;
                            final int gh = ii;

                            downHour1[ii].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                                    for (int i = 0; i < downHour.length; i++) {
                                        layout.removeView(downHour[i]);
                                    }
                                    for (int i = 0; i < downHour1.length; i++) {
                                        layout.removeView(downHour1[i]);
                                    }

                                    if (Case == 0) {
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                                    }else if(Case == 1){
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                                    }else if(Case == 3){
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                                    }
                                    Selected = hourList.get(gh);
                                    HourChosen = true;
                                    hourinfo.setText(Selected.getName());
                                    hourimage.setVisibility(View.VISIBLE);
                                    ColorDrawable ic = new ColorDrawable();
                                    ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                                    hourimage.setBackground(ic);
                                    if(Case == 0){
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        if(Selected != null) {
                                            Finish(fragmentView, activity, home,null, null, Case );
                                        }
                                    }else if(Case == 1){
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        if(Selected != null) {
                                            Finish(fragmentView, activity,null, e, null, Case );
                                        }
                                    }else if(Case == 3){
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        if(Selected != null) {
                                            Finish(fragmentView, activity,null, e, pre, Case );
                                        }
                                    }
                                }
                            });
                        }

                    }

                }

                Button Back = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Back);
                Button Next = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Next);

                Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                        for (int i = 0; i < downHour.length; i++) {
                            layout.removeView(downHour[i]);
                        }
                        for (int i = 0; i < downHour1.length; i++) {
                            layout.removeView(downHour1[i]);
                        }

                        if (Case == 0) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }else if(Case == 1){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }else if(Case == 3){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }
                        HourChosen = false;
                        hourinfo.setText(activity.getResources().getString(R.string.NoHourNameChosen));
                        hourimage.setVisibility(View.GONE);
                        Selected = null;
                    }
                });

                MainActivity.hideKeyboard(activity);

                Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                        if(downHour != null) {
                            for (int i = 0; i < downHour.length; i++) {
                                layout.removeView(downHour[i]);
                            }
                        }
                        if(downHour1 != null) {
                            for (int i = 0; i < downHour1.length; i++) {
                                layout.removeView(downHour1[i]);
                            }
                        }

                        if (Case == 0) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }else if(Case == 1){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }else if(Case == 3){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }

                        if (Custom.getText().toString().trim().equals("")) {
                            Custom.setError(activity.getResources().getString(R.string.EmptyField));
                        } else {
                            Selected = new Hour(Custom.getText().toString(), "#D8D8D8");
                            HourChosen = true;
                            hourinfo.setText(Selected.getName());
                            hourimage.setVisibility(View.VISIBLE);
                            ColorDrawable ic = new ColorDrawable();
                            ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                            hourimage.setBackground(ic);
                            if(Case == 0){
                                fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                if(Selected != null) {
                                    Finish(fragmentView, activity, home,null, null, Case );
                                }
                            }else if(Case == 1){
                                fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                if(Selected != null) {
                                    Finish(fragmentView, activity,null, e, null, Case );
                                }
                            }else if(Case == 3){
                                fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                if(Selected != null) {
                                    Finish(fragmentView, activity,null, e, pre, Case );
                                }
                            }
                        }
                    }
                });


                Base1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                        for (int i = 0; i < downHour.length; i++) {
                            layout.removeView(downHour[i]);
                        }
                        for (int i = 0; i < downHour1.length; i++) {
                            layout.removeView(downHour1[i]);
                        }

                        if (Case == 0) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }else if(Case == 1){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }else if(Case == 3){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }
                        Selected = hourList.get(0);
                        HourChosen = true;
                        hourinfo.setText(Selected.getName());
                        hourimage.setVisibility(View.VISIBLE);
                        ColorDrawable ic = new ColorDrawable();
                        ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                        hourimage.setBackground(ic);
                        if(Case == 0){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            if(Selected != null) {
                                Finish(fragmentView, activity, home,null, null, Case );
                            }
                        }else if(Case == 1){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            if(Selected != null) {
                                Finish(fragmentView, activity,null, e, null, Case );
                            }
                        }else if(Case == 3){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            if(Selected != null) {
                                Finish(fragmentView, activity,null, e, pre, Case );
                            }
                        }
                    }
                });

                Base2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                        for (int i = 0; i < downHour.length; i++) {
                            layout.removeView(downHour[i]);
                        }
                        for (int i = 0; i < downHour1.length; i++) {
                            layout.removeView(downHour1[i]);
                        }

                        if (Case == 0) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }else if(Case == 1){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }else if(Case == 3){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }
                        Selected = hourList.get(1);
                        HourChosen = true;
                        hourinfo.setText(Selected.getName());
                        hourimage.setVisibility(View.VISIBLE);
                        ColorDrawable ic = new ColorDrawable();
                        ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                        hourimage.setBackground(ic);
                        if(Case == 0){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            if(Selected != null) {
                                Finish(fragmentView, activity, home,null, null, Case );
                            }
                        }else if(Case == 1){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            if(Selected != null) {
                                Finish(fragmentView, activity,null, e, null, Case );
                            }
                        }else if(Case == 3){
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            if(Selected != null) {
                                Finish(fragmentView, activity,null, e, pre, Case );
                            }
                        }
                    }
                });

            }
        });
    }


    private void Finish(final View fragmentView, final Activity activity, final Homework h, final Exam e, final Presentation pre, final int Case){

        final Button Next = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Next);
        final EditText titel = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_TitelInput);
        final EditText description = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_DescriptionInput);
        final TextView hourinfo = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
                if(!HourChosen){
                    hourinfo.setError(activity.getResources().getString(R.string.NoHourNameChosen));
                }else if(titel.getText().toString().trim().equals("")){
                    titel.setError(activity.getResources().getString(R.string.EmptyField));
                }else if(description.getText().toString().trim().equals("")){
                    description.setError(activity.getResources().getString(R.string.EmptyField));
                }else{

                    TextView Date = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_DateText);
                    TextView Hour = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_HourText);
                    TextView Titel = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Titel);
                    TextView Description = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Text);
                    TextView OverviewHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Heading);
                    ImageView colour = (ImageView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_HourPic);

                    if(Case == 0) {
                        h.setHour(Selected);
                        h.setTitle(titel.getText().toString().trim());
                        h.setDescription(description.getText().toString().trim());
                        Titel.setText(h.getTitle());
                        Description.setText(h.getDescription());
                        Date.setText(h.getDay() + "." + h.getMonth() + "." + h.getYear());
                        OverviewHeading.setText(activity.getResources().getString(R.string.HomeworkOverview));
                    }else if(Case == 1){
                        e.setHour(Selected);
                        e.setTitle(titel.getText().toString().trim());
                        e.setDescription(description.getText().toString().trim());
                        Titel.setText(e.getTitle());
                        Description.setText(e.getDescription());
                        Date.setText(e.getDay() + "." + e.getMonth() + "." + e.getYear());
                        OverviewHeading.setText(activity.getResources().getString(R.string.ExamOverview));
                    }else if(Case == 3){
                        pre.setHour(Selected);
                        pre.setTitle(titel.getText().toString().trim());
                        pre.setDescription(description.getText().toString().trim());
                        Titel.setText(pre.getTitle());
                        Description.setText(pre.getDescription());
                        Date.setText(pre.getDay() + "." + pre.getMonth() + "." + pre.getYear());
                        OverviewHeading.setText(activity.getResources().getString(R.string.PresentationOverview));
                    }

                    fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView).setVisibility(View.VISIBLE);
                    Hour.setText(Selected.getName());
                    colour.setBackgroundColor(android.graphics.Color.parseColor(Selected.getColorCode()));

                    Button back = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Back);
                    Button finish = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Finish);

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView).setVisibility(View.GONE);
                        }
                    });

                    finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Case == 0){
                                FinishHomework(fragmentView, activity, h);
                            }else if(Case == 1){
                                FinishExam(fragmentView, activity, e);
                            }else if(Case == 3){
                                FinishPresentation(fragmentView, activity, pre);
                            }
                        }
                    });
                }
            }
        });

    }


    private void FinishPresentation (final View fragmentView, final Activity activity, Presentation event){
        fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout).setVisibility(View.VISIBLE);

        Gson gson = new Gson();
        ArrayList<Presentation> eventlist;
        String json1 = FirstScreen.tinyDB.getString("Presentation");
        if(json1.equals("")){
            eventlist = new ArrayList<Presentation>();
        }else {
            Type type = new TypeToken<ArrayList<Presentation>>() {
            }.getType();
            eventlist = gson.fromJson(json1, type);
        }

        eventlist.add(event);
        FirstScreen.tinyDB.putString("Presentation", gson.toJson(eventlist));

        if(FirstScreen.tinyDB.getString("WeekA").equals("")) {

        }else {
            final ArrayList<Hour> hourList = new ArrayList<Hour>();
            String json = FirstScreen.tinyDB.getString("WeekA");
            Type type = new TypeToken<Week>() {
            }.getType();

            Week weekA = gson.fromJson(json, type);
            Week NWeekA = new Week(weekA.getDayList().size());
            weekA.getDayList().get(0).getHourList();
            for (Day d : weekA.getDayList()) {
                Day day = new Day(d.getName());
                for (Hour h : d.getHourList()) {
                    if (h.getName().equals(Selected.getName()) && h.getColorCode().equals(h.getColorCode())) {
                        day.addHour(Selected);
                    } else {
                        day.addHour(h);
                    }
                }
                NWeekA.addDay(day);
            }

            FirstScreen.tinyDB.putString("WeekA", gson.toJson(NWeekA));

            if (!(FirstScreen.tinyDB.getString("WeekB").equals(""))) {
                json = FirstScreen.tinyDB.getString("WeekB");
                Week weekB = gson.fromJson(json, type);
                Week NWeekB = new Week(weekB.getDayList().size());
                weekB.getDayList().get(0).getHourList();
                for (Day d : weekB.getDayList()) {
                    Day day = new Day(d.getName());
                    for (Hour h : d.getHourList()) {
                        if (h.getName().equals(Selected.getName()) && h.getColorCode().equals(h.getColorCode())) {
                            day.addHour(Selected);
                        } else {
                            day.addHour(h);
                        }
                    }
                    NWeekB.addDay(day);
                }

                FirstScreen.tinyDB.putString("WeekB", gson.toJson(NWeekB));

            }
        }
        Overview(fragmentView, activity, 3, false);
    }


    private void FinishHomework(final View fragmentView, final Activity activity, Homework event){
        fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout).setVisibility(View.VISIBLE);

        Gson gson = new Gson();
        ArrayList<Homework> eventlist;
        String json1 = FirstScreen.tinyDB.getString("Homework");
        if(json1.equals("")){
            eventlist = new ArrayList<Homework>();
        }else {
            Type type = new TypeToken<ArrayList<Homework>>() {
            }.getType();
            eventlist = gson.fromJson(json1, type);
        }

        eventlist.add(event);
        FirstScreen.tinyDB.putString("Homework", gson.toJson(eventlist));

        if(FirstScreen.tinyDB.getString("WeekA").equals("")) {

        }else {
            final ArrayList<Hour> hourList = new ArrayList<Hour>();
            String json = FirstScreen.tinyDB.getString("WeekA");
            Type type = new TypeToken<Week>() {
            }.getType();

            Week weekA = gson.fromJson(json, type);
            Week NWeekA = new Week(weekA.getDayList().size());
            weekA.getDayList().get(0).getHourList();
            for (Day d : weekA.getDayList()) {
                Day day = new Day(d.getName());
                for (Hour h : d.getHourList()) {
                    if (h.getName().equals(Selected.getName()) && h.getColorCode().equals(h.getColorCode())) {
                        day.addHour(Selected);
                    } else {
                        day.addHour(h);
                    }
                }
                NWeekA.addDay(day);
            }

            FirstScreen.tinyDB.putString("WeekA", gson.toJson(NWeekA));

            if (!(FirstScreen.tinyDB.getString("WeekB").equals(""))) {
                json = FirstScreen.tinyDB.getString("WeekB");
                Week weekB = gson.fromJson(json, type);
                Week NWeekB = new Week(weekB.getDayList().size());
                weekB.getDayList().get(0).getHourList();
                for (Day d : weekB.getDayList()) {
                    Day day = new Day(d.getName());
                    for (Hour h : d.getHourList()) {
                        if (h.getName().equals(Selected.getName()) && h.getColorCode().equals(h.getColorCode())) {
                            day.addHour(Selected);
                        } else {
                            day.addHour(h);
                        }
                    }
                    NWeekB.addDay(day);
                }

                FirstScreen.tinyDB.putString("WeekB", gson.toJson(NWeekB));

            }
        }
        Overview(fragmentView, activity, 0, false);
    }


    private void FinishExam(final View fragmentView, final Activity activity, Exam event){
        fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout).setVisibility(View.VISIBLE);
        
        Gson gson = new Gson();
        ArrayList<Exam> eventlist;
        String json1 = FirstScreen.tinyDB.getString("Exam");
        if(json1.equals("")){
            eventlist = new ArrayList<Exam>();
        }else {
            Type type = new TypeToken<ArrayList<Exam>>() {
            }.getType();
            eventlist = gson.fromJson(json1, type);
        }

        eventlist.add(event);
        FirstScreen.tinyDB.putString("Exam", gson.toJson(eventlist));

        if(FirstScreen.tinyDB.getString("WeekA").equals("")) {

        }else {
            final ArrayList<Hour> hourList = new ArrayList<Hour>();
            String json = FirstScreen.tinyDB.getString("WeekA");
            Type type = new TypeToken<Week>() {
            }.getType();

            Week weekA = gson.fromJson(json, type);
            Week NWeekA = new Week(weekA.getDayList().size());
            weekA.getDayList().get(0).getHourList();
            for (Day d : weekA.getDayList()) {
                Day day = new Day(d.getName());
                for (Hour h : d.getHourList()) {
                    if (h.getName().equals(Selected.getName()) && h.getColorCode().equals(h.getColorCode())) {
                        day.addHour(Selected);
                    } else {
                        day.addHour(h);
                    }
                }
                NWeekA.addDay(day);
            }

            FirstScreen.tinyDB.putString("WeekA", gson.toJson(NWeekA));

            if (!(FirstScreen.tinyDB.getString("WeekB").equals(""))) {
                json = FirstScreen.tinyDB.getString("WeekB");
                Week weekB = gson.fromJson(json, type);
                Week NWeekB = new Week(weekB.getDayList().size());
                weekB.getDayList().get(0).getHourList();
                for (Day d : weekB.getDayList()) {
                    Day day = new Day(d.getName());
                    for (Hour h : d.getHourList()) {
                        if (h.getName().equals(Selected.getName()) && h.getColorCode().equals(h.getColorCode())) {
                            day.addHour(Selected);
                        } else {
                            day.addHour(h);
                        }
                    }
                    NWeekB.addDay(day);
                }

                FirstScreen.tinyDB.putString("WeekB", gson.toJson(NWeekB));

            }
        }
        Overview(fragmentView, activity, 1, false);
    }



    private void Overview(final View fragmentView, final Activity activity, final int Case, final boolean Grade) {

        Gson gson = new Gson();
        Type type;
        List eventlist;
        String json;
        if (Case == 0) {
            json = FirstScreen.tinyDB.getString("Homework");
            type = new TypeToken<ArrayList<Homework>>() {}.getType();
        } else if (Case == 1) {
            json = FirstScreen.tinyDB.getString("Exam");
            type = new TypeToken<ArrayList<Exam>>() {}.getType();
        } else if (Case == 2){
            json = FirstScreen.tinyDB.getString("Certificates");
            type = new TypeToken<ArrayList<Certificate>>() {}.getType();
        }else{
            json = FirstScreen.tinyDB.getString("Presentation");
            type = new TypeToken<ArrayList<Presentation>>() {}.getType();
        }
        eventlist = gson.fromJson(json, type);
        Calendar ca = Calendar.getInstance();
        Date d = ca.getTime();
        DateTime dateTime = new DateTime(d);
        Event currentTimeEvent = new Event(dateTime.getDayOfMonth(), dateTime.getMonthOfYear(), dateTime.getYear());

        final ConstraintLayout OverviewView = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout);
        final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
        final ConstraintLayout GradeSelection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSelection);
        OverviewView.setVisibility(View.VISIBLE);
        Selection.setVisibility(View.GONE);
        GradeSelection.setVisibility(View.GONE);
        final TextView OverviewHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_Overview_ListViewHeading);
        final TextView infoView = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_Overview_Info);
        final Button infoButton = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_Overview_CreateNow);
        final Button TopNew = (Button) activity.findViewById(R.id.organizer_new);

        if(Case == 0) {
            OverviewHeading.setText(activity.getResources().getString(R.string.HomeworkOverview));
            infoView.setText(activity.getResources().getString(R.string.NoHomeworkEntered));
            infoButton.setText(activity.getResources().getString(R.string.CreateNow));

            if (FirstScreen.tinyDB.getString("Homework").equals("")) {
                infoView.setVisibility(View.VISIBLE);
                infoButton.setVisibility(View.VISIBLE);
                infoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OverviewView.setVisibility(View.GONE);
                        NewEventHomework(fragmentView, activity);
                    }
                });
            } else {
                infoView.setVisibility(View.GONE);
                infoButton.setVisibility(View.GONE);
                ListView listView = (ListView) activity.findViewById(R.id.fragment_organizer_home_Homework_Overview_ListView);
                HomeCustomAdapter customAdapter;
                if(Grade == false) {
                    ArrayList<Homework> halist = new ArrayList<Homework>();
                    if (eventlist.size() > 0) {
                        for (Homework ha : (ArrayList<Homework>) eventlist) {
                            if (EventMethods.isYounger((Event) ha, currentTimeEvent)) {
                                halist.add(ha);
                            }
                        }
                    }
                    customAdapter = new HomeCustomAdapter(activity, halist);
                }else{
                    customAdapter = new HomeCustomAdapter(activity, (ArrayList<Homework>) eventlist);
                }
                listView.setAdapter(customAdapter);
            }
            TopNew.setVisibility(View.VISIBLE);
            TopNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewEventHomework(fragmentView, activity);
                }
            });
        }else if(Case == 1){
            OverviewHeading.setText(activity.getResources().getString(R.string.ExamOverview));
            infoView.setText(activity.getResources().getString(R.string.NoExamEntered));
            infoButton.setText(activity.getResources().getString(R.string.CreateNow));

            if (FirstScreen.tinyDB.getString("Exam").equals("")) {
                infoView.setVisibility(View.VISIBLE);
                infoButton.setVisibility(View.VISIBLE);
                infoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OverviewView.setVisibility(View.GONE);
                        NewEventExam(fragmentView, activity);
                    }
                });
            } else {
                infoView.setVisibility(View.GONE);
                infoButton.setVisibility(View.GONE);
                ListView listView = (ListView) activity.findViewById(R.id.fragment_organizer_home_Homework_Overview_ListView);
                HomeCustomAdapter customAdapter;
                if(Grade == false){
                    ArrayList<Exam> exlist = new ArrayList<Exam>();
                    if(eventlist.size() > 0) {
                        for (Exam exam : (ArrayList<Exam>) eventlist) {
                            if(EventMethods.isYounger((Event) exam, currentTimeEvent)){
                                exlist.add(exam);
                            }
                        }
                    }
                    customAdapter = new HomeCustomAdapter(exlist, activity);
                }else{
                    customAdapter = new HomeCustomAdapter((ArrayList<Exam>) eventlist, activity);
                }
                listView.setAdapter(customAdapter);
            }
            TopNew.setVisibility(View.VISIBLE);
            TopNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewEventExam(fragmentView, activity);
                }
            });
        }else if(Case == 2){
            final ConstraintLayout Gradesub = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSubSelection);
            Gradesub.setVisibility(View.GONE);
            OverviewHeading.setText(activity.getResources().getString(R.string.CertificatesOverview));
            infoView.setText(activity.getResources().getString(R.string.NoCertificateEntered));
            infoButton.setText(activity.getResources().getString(R.string.CreateNow));

            if (FirstScreen.tinyDB.getString("Certificates").equals("")) {
                infoView.setVisibility(View.VISIBLE);
                infoButton.setVisibility(View.VISIBLE);
                infoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OverviewView.setVisibility(View.GONE);
                        ScrollView newCert = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificateScrollView);
                        newCert.setVisibility(View.GONE);
                        Gradesub.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                infoView.setVisibility(View.GONE);
                infoButton.setVisibility(View.GONE);

                ListView listView = (ListView) activity.findViewById(R.id.fragment_organizer_home_Homework_Overview_ListView);
                HomeCustomAdapter customAdapter = new HomeCustomAdapter(activity, (ArrayList<Certificate>) eventlist, 1);
                listView.setAdapter(customAdapter);

                for(int l = 0; l < eventlist.size(); l++){
                    Certificate c = (Certificate) eventlist.get(l);
                }
            }
        }else if(Case == 3){

                OverviewHeading.setText(activity.getResources().getString(R.string.PresentationOverview));
                infoView.setText(activity.getResources().getString(R.string.NoPresentationEntered));
                infoButton.setText(activity.getResources().getString(R.string.CreateNow));

                if (FirstScreen.tinyDB.getString("Presentation").equals("")) {
                    infoView.setVisibility(View.VISIBLE);
                    infoButton.setVisibility(View.VISIBLE);
                    infoButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NewEventPresentation(fragmentView, activity);
                        }
                    });
                } else {
                    infoView.setVisibility(View.GONE);
                    infoButton.setVisibility(View.GONE);
                    ListView listView = (ListView) activity.findViewById(R.id.fragment_organizer_home_Homework_Overview_ListView);
                    HomeCustomAdapter customAdapter;
                    if(Grade == false) {
                        ArrayList<Presentation> prlist = new ArrayList<Presentation>();
                        if (eventlist.size() > 0) {
                            for (Presentation pa : (ArrayList<Presentation>) eventlist) {
                                if (EventMethods.isYounger((Event) pa, currentTimeEvent)) {
                                    prlist.add(pa);
                                }
                            }
                        }
                        customAdapter = new HomeCustomAdapter(prlist, activity, 88);
                    }else{
                        customAdapter = new HomeCustomAdapter((ArrayList<Presentation>) eventlist, activity, 88);
                    }
                    listView.setAdapter(customAdapter);
                }
                TopNew.setVisibility(View.VISIBLE);
                TopNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewEventPresentation(fragmentView, activity);
                    }
                });
                TopNew.setVisibility(View.GONE);
        }

        final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
        final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
        TopBack.setText(activity.getResources().getString(R.string.Back));
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Grade) {
                    Selection.setVisibility(View.VISIBLE);
                }else{
                    GradeSelection.setVisibility(View.VISIBLE);
                }
                OverviewView.setVisibility(View.GONE);
                TopNew.setVisibility(View.GONE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!Grade) {
                            Selection.setVisibility(View.GONE);
                        }else{
                            GradeSelection.setVisibility(View.GONE);
                        }
                        MainLayout.setVisibility(View.VISIBLE);
                        TopNew.setVisibility(View.GONE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        if(!Grade) {
                            Selection.setVisibility(View.GONE);
                        }else{
                            GradeSelection.setVisibility(View.GONE);
                        }
                        MainLayout.setVisibility(View.VISIBLE);
                        TopNew.setVisibility(View.GONE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
            }
        });

        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                if(!Grade) {
                    Selection.setVisibility(View.VISIBLE);
                }else{
                    GradeSelection.setVisibility(View.VISIBLE);
                }
                OverviewView.setVisibility(View.GONE);
                TopNew.setVisibility(View.GONE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!Grade) {
                            Selection.setVisibility(View.GONE);
                        }else{
                            GradeSelection.setVisibility(View.GONE);
                        }
                        MainLayout.setVisibility(View.VISIBLE);
                        TopNew.setVisibility(View.GONE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        if(!Grade) {
                            Selection.setVisibility(View.GONE);
                        }else{
                            GradeSelection.setVisibility(View.GONE);
                        }
                        MainLayout.setVisibility(View.VISIBLE);
                        TopNew.setVisibility(View.GONE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }





    private void BasicNewEvent(View fragmentView, Activity activity){
        final Button TopNew = (Button) activity.findViewById(R.id.organizer_new);
        final ConstraintLayout OverviewView = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout);
        fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.VISIBLE);
        OverviewView.setVisibility(View.GONE);
        TopNew.setVisibility(View.GONE);
        CalendarView cv = (CalendarView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Calendar);
        cv.setDate(new Date().getTime(), false, true);
        final TextView error = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_ErrorDate);
        error.setVisibility(View.GONE);
        fyear = 0;
        Selected = null;
    }

    private void NewEventExam(final View fragmentView, final Activity activity){
        BasicNewEvent(fragmentView, activity);
        TextView newEventHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Heading);
        newEventHeading.setText(activity.getResources().getString(R.string.EnterNewExam));
        TextView newEventSubHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Date);
        newEventSubHeading.setText(activity.getResources().getString(R.string.ExamAppointment));


        final ScrollView newHomework = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout);
        final ScrollView HourSelection = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView);
        final ScrollView FinishHomework = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView);
        final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
        final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
        final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
        final ScrollView newEvent = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout);

        TopBack.setText(activity.getResources().getString(R.string.Cancel));
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.GONE);
                newHomework.setVisibility(View.GONE);
                HourSelection.setVisibility(View.GONE);
                FinishHomework.setVisibility(View.GONE);
                newEvent.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setText(activity.getResources().getString(R.string.Back));
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                Selection.setVisibility(View.GONE);
                newHomework.setVisibility(View.GONE);
                HourSelection.setVisibility(View.GONE);
                FinishHomework.setVisibility(View.GONE);
                newEvent.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Button newEventBack = (Button) fragmentView.findViewById(R.id.organizer_NewEventBack);
        newEventBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.VISIBLE);
                newEvent.setVisibility(View.GONE);
                TopBack.setText(activity.getResources().getString(R.string.Back));
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Selection.setVisibility(View.GONE);
                        MainLayout.setVisibility(View.VISIBLE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Selection.setVisibility(View.GONE);
                        MainLayout.setVisibility(View.VISIBLE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
            }
        });


        CalendarView cv = (CalendarView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Calendar);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fday = dayOfMonth;
                fmonth = month + 1;
                fyear = year;
            }
        });

        final TextView error = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_ErrorDate);
        Button newEventNext = (Button) fragmentView.findViewById(R.id.organizer_NewEventNext);
        newEventNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
                int rday, rmonth, ryear;
                Date c = Calendar.getInstance().getTime();
                rday = Integer.parseInt(new SimpleDateFormat("dd").format(c));
                rmonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
                ryear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));

                Exam exam = new Exam(fday, fmonth, fyear);

                if(ryear == fyear){
                    if(rmonth == fmonth){
                        if(rday < fday){
                            ContinueMethod(fragmentView, null, exam,null, activity, 1);
                            error.setVisibility(View.GONE);
                        }else{
                            error.setVisibility(View.VISIBLE);
                        }
                    }else if(rmonth < fmonth){
                        ContinueMethod(fragmentView, null, exam,null, activity, 1);
                        error.setVisibility(View.GONE);
                    }else{
                        error.setVisibility(View.VISIBLE);
                    }
                }else if(ryear < fyear){
                    ContinueMethod(fragmentView, null, exam,null, activity, 1);
                    error.setVisibility(View.GONE);
                }else{
                    error.setVisibility(View.VISIBLE);
                }
            }
        });

    }









    private void NewEventPresentation(final View fragmentView, final Activity activity){
        BasicNewEvent(fragmentView, activity);
        TextView newEventHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Heading);
        newEventHeading.setText(activity.getResources().getString(R.string.CreateNewPresentation));
        TextView newEventSubHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Date);
        newEventSubHeading.setText(activity.getResources().getString(R.string.Date));


        final ScrollView newHomework = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout);
        final ScrollView HourSelection = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView);
        final ScrollView FinishHomework = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView);
        final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
        final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
        final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
        final ScrollView newEvent = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout);

        TopBack.setText(activity.getResources().getString(R.string.Cancel));
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.GONE);
                newHomework.setVisibility(View.GONE);
                HourSelection.setVisibility(View.GONE);
                FinishHomework.setVisibility(View.GONE);
                newEvent.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setText(activity.getResources().getString(R.string.Back));
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                Selection.setVisibility(View.GONE);
                newHomework.setVisibility(View.GONE);
                HourSelection.setVisibility(View.GONE);
                FinishHomework.setVisibility(View.GONE);
                newEvent.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Button newEventBack = (Button) fragmentView.findViewById(R.id.organizer_NewEventBack);
        newEventBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.VISIBLE);
                newEvent.setVisibility(View.GONE);
                TopBack.setText(activity.getResources().getString(R.string.Back));
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Selection.setVisibility(View.GONE);
                        MainLayout.setVisibility(View.VISIBLE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Selection.setVisibility(View.GONE);
                        MainLayout.setVisibility(View.VISIBLE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
            }
        });


        CalendarView cv = (CalendarView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Calendar);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fday = dayOfMonth;
                fmonth = month + 1;
                fyear = year;
            }
        });

        final TextView error = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_ErrorDate);
        Button newEventNext = (Button) fragmentView.findViewById(R.id.organizer_NewEventNext);
        newEventNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
                int rday, rmonth, ryear;
                Date c = Calendar.getInstance().getTime();
                rday = Integer.parseInt(new SimpleDateFormat("dd").format(c));
                rmonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
                ryear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));

                Presentation pre = new Presentation(fday, fmonth, fyear);

                if(ryear == fyear){
                    if(rmonth == fmonth){
                        if(rday < fday){
                            ContinueMethod(fragmentView, null, null, pre, activity, 3);
                            error.setVisibility(View.GONE);
                        }else{
                            error.setVisibility(View.VISIBLE);
                        }
                    }else if(rmonth < fmonth){
                        ContinueMethod(fragmentView, null,null, pre, activity, 3);
                        error.setVisibility(View.GONE);
                    }else{
                        error.setVisibility(View.VISIBLE);
                    }
                }else if(ryear < fyear){
                    ContinueMethod(fragmentView, null, null, pre, activity, 3);
                    error.setVisibility(View.GONE);
                }else{
                    error.setVisibility(View.VISIBLE);
                }
            }
        });

    }




















    private void NewEventHomework(final View fragmentView, final Activity activity){
        BasicNewEvent(fragmentView, activity);
        TextView newEventHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Heading);
        newEventHeading.setText(activity.getResources().getString(R.string.EnterNewHomework));
        TextView newEventSubHeading = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Date);
        newEventSubHeading.setText(activity.getResources().getString(R.string.DutyDate));

        final ScrollView newHomework = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout);
        final ScrollView HourSelection = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView);
        final ScrollView FinishHomework = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView);
        final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
        final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
        final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
        final ScrollView newEvent = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout);

        TopBack.setText(activity.getResources().getString(R.string.Cancel));
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.GONE);
                newHomework.setVisibility(View.GONE);
                HourSelection.setVisibility(View.GONE);
                FinishHomework.setVisibility(View.GONE);
                newEvent.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                TopBack.setText(activity.getResources().getString(R.string.Back));
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                Selection.setVisibility(View.GONE);
                newHomework.setVisibility(View.GONE);
                HourSelection.setVisibility(View.GONE);
                FinishHomework.setVisibility(View.GONE);
                newEvent.setVisibility(View.GONE);
                MainLayout.setVisibility(View.VISIBLE);
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        Button newEventBack = (Button) fragmentView.findViewById(R.id.organizer_NewEventBack);
        newEventBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.VISIBLE);
                newEvent.setVisibility(View.GONE);
                TopBack.setText(activity.getResources().getString(R.string.Back));
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Selection.setVisibility(View.GONE);
                        MainLayout.setVisibility(View.VISIBLE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Selection.setVisibility(View.GONE);
                        MainLayout.setVisibility(View.VISIBLE);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                        Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                            @Override
                            public void doBack() {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    }
                });
            }
        });


        CalendarView cv = (CalendarView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Calendar);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fday = dayOfMonth;
                fmonth = month + 1;
                fyear = year;
            }
        });

        final TextView error = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_ErrorDate);
        Button newEventNext = (Button) fragmentView.findViewById(R.id.organizer_NewEventNext);
        newEventNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
                int rday, rmonth, ryear;
                Date c = Calendar.getInstance().getTime();
                rday = Integer.parseInt(new SimpleDateFormat("dd").format(c));
                rmonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
                ryear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));

                Homework home = new Homework(fday, fmonth, fyear);

                if(ryear == fyear){
                    if(rmonth == fmonth){
                        if(rday < fday){
                            ContinueMethod(fragmentView, home, null,null, activity, 0);
                            error.setVisibility(View.GONE);
                        }else{
                            error.setVisibility(View.VISIBLE);
                        }
                    }else if(rmonth < fmonth){
                        ContinueMethod(fragmentView, home, null,null, activity, 0);
                        error.setVisibility(View.GONE);
                    }else{
                        error.setVisibility(View.VISIBLE);
                    }
                }else if(ryear < fyear){
                    ContinueMethod(fragmentView, home, null,null, activity, 0);
                    error.setVisibility(View.GONE);
                }else{
                    error.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    private int Gradeback = 0;

    private void GradeBackMethod(View fragmentView, Activity activity){
        ConstraintLayout root = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
        ConstraintLayout Graderoot = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSelection);
        ConstraintLayout Gradesub = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSubSelection);

        if(Gradeback == 0){
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }else if(Gradeback == 1){
            root.setVisibility(View.VISIBLE);
            Graderoot.setVisibility(View.GONE);
            Gradeback = 0;
        }else if(Gradeback == 2) {
            Graderoot.setVisibility(View.VISIBLE);
            Gradesub.setVisibility(View.GONE);
            fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificateScrollView).setVisibility(View.GONE);
            EditText edit1 = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_edit1);
            EditText edit2 = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_EnterNewCertificate_edit2);
            edit1.setText("");
            edit2.setText("");
            Gradeback = 1;
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
                        GradeBackMethod(fragmentView, activity);
                    }
                });

                Organizer.setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        GradeBackMethod(fragmentView, activity);
                    }
                });



                ConstraintLayout root = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
                final ConstraintLayout Graderoot = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_GradeSelection);
                root.setVisibility(View.GONE);
                Graderoot.setVisibility(View.VISIBLE);
                Gradeback = 1;

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
                        Gradeback = 2;

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
