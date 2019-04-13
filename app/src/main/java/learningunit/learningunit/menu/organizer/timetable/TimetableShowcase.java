package learningunit.learningunit.menu.organizer.timetable;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import learningunit.learningunit.Objects.PublicAPIs.AnalyticsApplication;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.Timetable.CustomAdapter;
import learningunit.learningunit.Objects.Timetable.HourList;
import learningunit.learningunit.Objects.Timetable.Week;
import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;

public class TimetableShowcase extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static boolean currentWeekShowcase, fabBool;

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        super.onCreate(savedInstanceState);

        currentWeekShowcase = HourList.currentWeekShowcase;
        HourList.currentWeekShowcase = false;

        setContentView(R.layout.activity_timetable_showcase);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if(FirstScreen.tinyDB.getString("WeekB").equalsIgnoreCase("")) {
            fabBool = false;
        }else{
            fabBool = true;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fabBool) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentWeekShowcase == true) {
                        HourList.currentWeekShowcase = false;
                        Toast.makeText(TimetableShowcase.this, getResources().getString(R.string.Week) + " A", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TimetableShowcase.this, getResources().getString(R.string.Week) + " B", Toast.LENGTH_SHORT).show();
                        HourList.currentWeekShowcase = true;
                    }
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }else{
            fab.setVisibility(View.GONE);
        }

        Button back = findViewById(R.id.timetableShowcase_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableShowcase.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button New = findViewById(R.id.timetableShowcase_New);
        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TimetableShowcase.this);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                FirstScreen.tinyDB.remove("WeekA");
                                FirstScreen.tinyDB.remove("WeekB");
                                Intent intent = new Intent(TimetableShowcase.this, Timetable.class);
                                startActivity(intent);
                    }
                });

                builder.setCancelable(true);
                builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setTitle(getResources().getString(R.string.NewTimetableQuestion));
                builder.setMessage(getResources().getString(R.string.NewTimetableQuestion1));
                builder.show();

            }
        });

        Button Share = (Button) findViewById(R.id.timetableShowcase_Share);
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ManageData.OfflineAccount == 2) {
                    Gson gson = new Gson();
                    String json = FirstScreen.tinyDB.getString("WeekA");
                    Type type = new TypeToken<Week>() {
                    }.getType();
                    final Week week = gson.fromJson(json, type);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TimetableShowcase.this);
                    builder.setCancelable(true);
                    TextView CopyWeekID = new TextView(TimetableShowcase.this);
                    CopyWeekID.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    CopyWeekID.setTextSize(22);
                    //CopyWeekID.setTypeface(CopyWeekID.getTypeface(), Typeface.BOLD_ITALIC);
                    CopyWeekID.setTypeface(null, Typeface.BOLD_ITALIC);
                    CopyWeekID.setText("#" + week.getWeekID() + "");
                    CopyWeekID.setPaintFlags(CopyWeekID.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                    builder.setView(CopyWeekID);
                    builder.setTitle(getResources().getString(R.string.TimetableID));
                    builder.setMessage(getResources().getString(R.string.TimetableIDInfo));
                    builder.setNegativeButton(getResources().getString(R.string.Back), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton(getResources().getString(R.string.CopyID), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(TimetableShowcase.this, "ID: #" + week.getWeekID() + getResources().getString(R.string.CopyMessage), Toast.LENGTH_LONG).show();
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("ID", "#" + week.getWeekID());
                            clipboard.setPrimaryClip(clip);
                        }
                    });
                    builder.show();


                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(TimetableShowcase.this);
                    builder.setCancelable(true);
                    builder.setTitle(getResources().getString(R.string.AccountNeeded));
                    builder.setMessage(getResources().getString(R.string.TimetableShareInfo));
                    builder.setNegativeButton(getResources().getString(R.string.Back), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton(getResources().getString(R.string.LoginRegister), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ManageData.RemoveOfflineData();
                            Intent intent = new Intent(TimetableShowcase.this, FirstScreen.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }
            }
        });

    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_timetable_showcase, container, false);
            Gson gson = new Gson();
            String json;
            if(!(FirstScreen.tinyDB.getString("WeekB").equalsIgnoreCase(""))) {
                fabBool = true;
                if (currentWeekShowcase) {
                    json = FirstScreen.tinyDB.getString("WeekB");
                } else {
                    json = FirstScreen.tinyDB.getString("WeekA");
                }
            }else {
                fabBool = false;
                json = FirstScreen.tinyDB.getString("WeekA");
            }
            Type type = new TypeToken<Week>() {}.getType();
            Week week = gson.fromJson(json, type);
            TextView Header = (TextView) rootView.findViewById(R.id.timeTableShowCase_Header);

            int Count = 0;
            if(week.getDayList().get(0).getHourList().size() == 1) {
                Count = 1;
            }else if(week.getDayList().get(0).getHourList().size() == 2){
                Count = 2;
            }else if(week.getDayList().get(0).getHourList().size() == 3){
                Count = 4;
            }else if(week.getDayList().get(0).getHourList().size() == 4){
                Count = 5;
            }else if(week.getDayList().get(0).getHourList().size() == 5){
                Count = 7;
            }else if(week.getDayList().get(0).getHourList().size() == 6){
                Count = 8;
            }else if(week.getDayList().get(0).getHourList().size() == 7){
                Count = 10;
            }else if(week.getDayList().get(0).getHourList().size() == 8){
                Count = 11;
            }else if(week.getDayList().get(0).getHourList().size() == 9){
                Count = 13;
            }else if(week.getDayList().get(0).getHourList().size() == 10){
                Count = 14;
            }else if(week.getDayList().get(0).getHourList().size() == 11){
                Count = 16;
            }else if(week.getDayList().get(0).getHourList().size() == 12){
                Count = 17;
            }else if(week.getDayList().get(0).getHourList().size() == 13){
                Count = 19;
            }else if(week.getDayList().get(0).getHourList().size() == 14){
                Count = 20;
            }else if(week.getDayList().get(0).getHourList().size() == 15){
                Count = 22;
            }else if(week.getDayList().get(0).getHourList().size() == 16){
                Count = 23;
            }else if(week.getDayList().get(0).getHourList().size() == 17){
                Count = 25;
            }else if(week.getDayList().get(0).getHourList().size() == 18){
                Count = 26;
            }else if(week.getDayList().get(0).getHourList().size() == 19){
                Count = 28;
            }else if(week.getDayList().get(0).getHourList().size() == 20){
                Count = 29;
            }else if(week.getDayList().get(0).getHourList().size() == 21){
                Count = 31;
            }else if(week.getDayList().get(0).getHourList().size() == 22){
                Count = 32;
            }else if(week.getDayList().get(0).getHourList().size() == 23){
                Count = 34;
            }else if(week.getDayList().get(0).getHourList().size() == 24){
                Count = 35;
            }

            String HourList[] = new String[Count];
            String ColorCodes[] = new String[Count];
            String DayHourList[][] = new String[week.getDayList().size()][Count];
            String DayHourColor[][] = new String[week.getDayList().size()][Count];

            int id = 0;
            int id1 = 0;

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1 && week.getDayList().size() >= 2) {

                if(currentWeekShowcase == false) {
                    Header.setText(getResources().getString(R.string.Wochenüberblick) + " " + getResources().getString(R.string.Week) + " A");
                }else{
                    Header.setText(getResources().getString(R.string.Wochenüberblick) + " " + getResources().getString(R.string.Week) + " B");
                }
                for(int g = 0; g < week.getDayList().size(); g++) {
                    for (int i = 0; i < Count; i++) {
                        if (!(i == 2 | i == 5 || i == 8 || i == 11 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26 || i == 29 || i == 32)) {
                            DayHourList[g][i] = week.getDayList().get(g).getHourList().get(id1).getName();
                            DayHourColor[g][i] = week.getDayList().get(g).getHourList().get(id1).getColorCode();
                            id1++;
                        } else {
                            DayHourList[g][i] = "";
                            DayHourColor[g][i] = "";
                        }
                    }
                    id1 = 0;
                }

                ListView simpleList = (ListView) rootView.findViewById(R.id.timetable_showcaseListView);
                CustomAdapter customAdapter = new CustomAdapter(inflater.getContext(), DayHourList, DayHourColor);
                simpleList.setAdapter(customAdapter);

            }else{
                int section = 0;
                if(week.getDayList().size() >= 2){
                    section = getArguments().getInt(ARG_SECTION_NUMBER) - 2;
                }else{
                    section = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
                }
                Header.setText(week.getDayList().get(section).getName());
                for (int i = 0; i < Count; i++) {
                    if (!(i == 2 | i == 5 || i == 8 || i == 11 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26 || i == 29 || i == 32)) {
                        HourList[i] = week.getDayList().get(section).getHourList().get(id).getName();
                        ColorCodes[i] = week.getDayList().get(section).getHourList().get(id).getColorCode();
                        id++;
                    } else {
                        HourList[i] = "";
                        ColorCodes[i] = "";
                    }
                }

                ListView simpleList = (ListView) rootView.findViewById(R.id.timetable_showcaseListView);
                CustomAdapter customAdapter = new CustomAdapter(inflater.getContext(), HourList, ColorCodes);
                simpleList.setAdapter(customAdapter);
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            Gson gson = new Gson();
            String json = FirstScreen.tinyDB.getString("WeekA");
            Type type = new TypeToken<Week>() {}.getType();
            Week week = gson.fromJson(json, type);
            if(week.getDayList().size() >= 2) {
                return week.getDayList().size() + 1;
            }else{
                return 1;
            }
        }
    }
}
