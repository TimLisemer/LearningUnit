package learningunit.learningunit.menu.organizer.timetable;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;

import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyList;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;
import learningunit.learningunit.Objects.Timetable.CustomAdapter;
import learningunit.learningunit.Objects.Timetable.Hour;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_showcase);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
                FirstScreen.tinyDB.remove("Week");
                Intent intent = new Intent(TimetableShowcase.this, Timetable.class);
                startActivity(intent);
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

            Gson gson = new Gson();
            String json = FirstScreen.tinyDB.getString("Week");
            Type type = new TypeToken<Week>() {}.getType();
            Week week = gson.fromJson(json, type);

            View rootView = inflater.inflate(R.layout.fragment_timetable_showcase, container, false);
            TextView Header = (TextView) rootView.findViewById(R.id.timeTableShowCase_Header);

            int Count = 0;
            if(week.getDayList().get(0).getHourList().size() <= 2){
                Count = 2;
            }else if(week.getDayList().get(0).getHourList().size() <= 4){
                Count = 5;
            }else if(week.getDayList().get(0).getHourList().size() <= 6){
                Count = 8;
            }else if(week.getDayList().get(0).getHourList().size() <= 8){
                Count = 11;
            }else if(week.getDayList().get(0).getHourList().size() <= 10){
                Count = 14;
            }else if(week.getDayList().get(0).getHourList().size() <= 12){
                Count = 17;
            }else if(week.getDayList().get(0).getHourList().size() <= 14){
                Count = 20;
            }else if(week.getDayList().get(0).getHourList().size() <= 16){
                Count = 23;
            }else if(week.getDayList().get(0).getHourList().size() <= 18){
                Count = 26;
            }else if(week.getDayList().get(0).getHourList().size() <= 20){
                Count = 29;
            }else if(week.getDayList().get(0).getHourList().size() <= 22){
                Count = 32;
            }else if(week.getDayList().get(0).getHourList().size() <= 24){
                Count = 35;
            }

            String HourList[] = new String[Count];
            String ColorCodes[] = new String[Count];
            String DayHourList[][] = new String[week.getDayList().size()][Count];
            String DayHourColor[][] = new String[week.getDayList().size()][Count];

            int id = 0;
            int id1 = 0;

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                Header.setText(week.getDayList().get(0).getName());
                for (int i = 0; i < Count; i++) {
                    if (!(i == 2 | i == 5 || i == 8 || i == 11 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26 || i == 29 || i == 32)) {
                        HourList[i] = week.getDayList().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getHourList().get(id1).getName();
                        ColorCodes[i] = week.getDayList().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getHourList().get(id1).getColorCode();
                        id1++;
                    } else {
                        HourList[i] = "";
                        ColorCodes[i] = "";
                    }
                }

                DayHourList[0] = HourList;
                DayHourColor[0] = ColorCodes;

                ListView simpleList = (ListView) rootView.findViewById(R.id.timetable_showcaseListView);
                CustomAdapter customAdapter = new CustomAdapter(inflater.getContext(), DayHourList, DayHourColor);
                simpleList.setAdapter(customAdapter);

            }else{

                Header.setText(week.getDayList().get(getArguments().getInt(ARG_SECTION_NUMBER) - 2).getName());
                for (int i = 0; i < Count; i++) {
                    if (!(i == 2 | i == 5 || i == 8 || i == 11 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26 || i == 29 || i == 32)) {
                        HourList[i] = week.getDayList().get(getArguments().getInt(ARG_SECTION_NUMBER) - 2).getHourList().get(id).getName();
                        ColorCodes[i] = week.getDayList().get(getArguments().getInt(ARG_SECTION_NUMBER) - 2).getHourList().get(id).getColorCode();
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
            String json = FirstScreen.tinyDB.getString("Week");
            Type type = new TypeToken<Week>() {}.getType();
            Week week = gson.fromJson(json, type);
            return week.getDayList().size() + 1;
        }
    }
}
