package learningunit.learningunit.Objects.Timetable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import learningunit.learningunit.R;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private String HourList[];
    private String ColorCodes[];
    String DayHourList[][];
    String DayHourColor[][];
    private boolean zero;
    private LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] HourList, String[] ColorCodes) {
        this.context = applicationContext;
        this.HourList = HourList;
        this.ColorCodes = ColorCodes;
        this.zero = false;

        this.inflter = (LayoutInflater.from(applicationContext));
    }

    public CustomAdapter(Context applicationContext, String DayHourList[][], String DayHourColor[][]) {
        this.context = applicationContext;
        this.DayHourList = DayHourList;
        this.DayHourColor = DayHourColor;
        this.zero = true;

        this.HourList = DayHourList[0];
        this.ColorCodes = DayHourColor[0];

        this.inflter = (LayoutInflater.from(applicationContext));

    }

    @Override
    public int getCount() {
        return HourList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.timetable_showcase_listview, null);
        final View view1 = view;

        view.findViewById(R.id.timetable_showcaseListViewNormalLayout).setVisibility(View.GONE);
        view.findViewById(R.id.timetable_showcaseListViewLayout).setVisibility(View.GONE);

        if(zero == false){
            view.findViewById(R.id.timetable_showcaseListViewNormalLayout).setVisibility(View.VISIBLE);

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view1.performHapticFeedback(1);
                    if(view1.findViewById(R.id.timetable_showcaseListViewText1).getVisibility() == View.GONE) {
                        view1.findViewById(R.id.timetable_showcaseListViewEdit).setVisibility(View.VISIBLE);
                        view1.findViewById(R.id.timetable_showcaseListViewText1).setVisibility(View.VISIBLE);
                        ConstraintSet set = new ConstraintSet();
                        ConstraintLayout layout = (ConstraintLayout) view1.findViewById(R.id.constraintLayoutListView);
                        set.clone(layout);
                        float biasedValue = 0f;
                        set.setVerticalBias(view1.findViewById(R.id.timetable_showcaseListViewText2).getId(), biasedValue);
                        set.applyTo(layout);
                    }else{
                        view1.findViewById(R.id.timetable_showcaseListViewEdit).setVisibility(View.GONE);
                        view1.findViewById(R.id.timetable_showcaseListViewText1).setVisibility(View.GONE);
                        ConstraintSet set = new ConstraintSet();
                        ConstraintLayout layout = (ConstraintLayout) view1.findViewById(R.id.constraintLayoutListView);
                        set.clone(layout);
                        float biasedValue = 0.5f;
                        set.setVerticalBias(view1.findViewById(R.id.timetable_showcaseListViewText2).getId(), biasedValue);
                        set.applyTo(layout);
                    }
                }
            });

            view.findViewById(R.id.timetable_showcaseListViewEdit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("showcaseListView", "View edit");
                }
            });

        }else{
            view.findViewById(R.id.timetable_showcaseListViewLayout).setVisibility(View.VISIBLE);
            if(DayHourList.length == 2){

                view.findViewById(R.id.timetable_showcaseListViewView4).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView3).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton4).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline3).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView2).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton3).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline2).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView1).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton2).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline1).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton1).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline).setVisibility(View.GONE);

                view.findViewById(R.id.owcaseListViewButton).setVisibility(View.GONE);

                Guideline guideline4 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline4);
                guideline4.setGuidelinePercent(0);

                Guideline guideline5 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline5);
                guideline5.setGuidelinePercent(0.50f);

            }else if(DayHourList.length == 3){

                view.findViewById(R.id.timetable_showcaseListViewView3).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView2).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton3).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline2).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView1).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton2).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline1).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton1).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline).setVisibility(View.GONE);

                view.findViewById(R.id.owcaseListViewButton).setVisibility(View.GONE);

                Guideline guideline3 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline3);
                guideline3.setGuidelinePercent(0);

                Guideline guideline4 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline4);
                guideline4.setGuidelinePercent(0.33f);

                Guideline guideline5 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline5);
                guideline5.setGuidelinePercent(0.66f);


            }else if(DayHourList.length == 4){

                view.findViewById(R.id.timetable_showcaseListViewView2).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView1).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton2).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline1).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton1).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline).setVisibility(View.GONE);

                view.findViewById(R.id.owcaseListViewButton).setVisibility(View.GONE);

                Guideline guideline2 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline2);
                guideline2.setGuidelinePercent(0);

                Guideline guideline3 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline3);
                guideline3.setGuidelinePercent(0.25f);

                Guideline guideline4 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline4);
                guideline4.setGuidelinePercent(0.5f);

                Guideline guideline5 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline5);
                guideline5.setGuidelinePercent(0.75f);

            }else if(DayHourList.length == 5){

                view.findViewById(R.id.timetable_showcaseListViewView1).setVisibility(View.GONE);

                view.findViewById(R.id.timetable_showcaseListViewView).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton1).setVisibility(View.GONE);
                view.findViewById(R.id.timetable_showcaseListViewGuideline).setVisibility(View.GONE);

                view.findViewById(R.id.owcaseListViewButton).setVisibility(View.GONE);

                Guideline guideline1 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline1);
                guideline1.setGuidelinePercent(0);

                Guideline guideline2 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline2);
                guideline2.setGuidelinePercent(0.2f);

                Guideline guideline3 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline3);
                guideline3.setGuidelinePercent(0.4f);

                Guideline guideline4 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline4);
                guideline4.setGuidelinePercent(0.6f);

                Guideline guideline5 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline5);
                guideline5.setGuidelinePercent(0.8f);

            }else if(DayHourList.length == 5){

                view.findViewById(R.id.timetable_showcaseListViewView).setVisibility(View.GONE);
                view.findViewById(R.id.owcaseListViewButton).setVisibility(View.GONE);

                Guideline guideline = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline);
                guideline.setGuidelinePercent(0);

                Guideline guideline1 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline1);
                guideline1.setGuidelinePercent(0.16f);

                Guideline guideline2 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline2);
                guideline2.setGuidelinePercent(0.33f);

                Guideline guideline3 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline3);
                guideline3.setGuidelinePercent(0.50f);

                Guideline guideline4 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline4);
                guideline4.setGuidelinePercent(0.66f);

                Guideline guideline5 = (Guideline) view.findViewById(R.id.timetable_showcaseListViewGuideline5);
                guideline5.setGuidelinePercent(0.83f);

            }else{

            }


        }


        TextView number = (TextView) view.findViewById(R.id.timetable_showcaseListViewText2);
        ImageView icon = (ImageView) view.findViewById(R.id.timetable_showcaseListViewIcon);
        TextView ListViewText = (TextView) view.findViewById(R.id.timetable_showcaseListViewText);

        if(!(i == 2 | i == 5 || i == 8 || i == 11 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26 || i == 29 || i == 32)) {

            ColorDrawable c = new ColorDrawable();
            if(ColorCodes[i] == null || ColorCodes[i] == ""){
                c.setColor(Color.WHITE);
            }else {
                c.setColor(android.graphics.Color.parseColor(ColorCodes[i]));
            }

            number.setVisibility(View.VISIBLE);

            int Count = 0;
            if(i <= 2){
                Count = i + 1;
            }else if(i <= 5){
                Count = i - 1 + 1;
            }else if(i <= 8){
                Count = i - 2 + 1;
            }else if(i <= 11){
                Count = i - 3 + 1;
            }else if(i <= 14){
                Count = i - 4 + 1;
            }else if(i <= 17){
                Count = i - 5 + 1;
            }else if(i <= 20){
                Count = i - 6 + 1;
            }else if(i <= 23){
                Count = i - 7 + 1;
            }else if(i <= 26){
                Count = i - 8 + 1;
            }else if(i <= 29){
                Count = i - 9 + 1;
            }else if(i <= 32){
                Count = i - 10 + 1;
            }else if(i <= 35){
                Count = i - 11 + 1;
            }


            if(zero == false) {
                icon.setVisibility(View.VISIBLE);
                ListViewText.setText(HourList[i]);
                icon.setBackground(c);
            }else{

                if(DayHourList.length >= 2) {
                    ColorDrawable b = new ColorDrawable();
                    b.setColor(android.graphics.Color.parseColor(DayHourColor[0][i]));
                    ColorDrawable b1 = new ColorDrawable();
                    b1.setColor(android.graphics.Color.parseColor(DayHourColor[1][i]));

                    view.findViewById(R.id.owcaseListViewButton6).setBackground(b);
                    view.findViewById(R.id.owcaseListViewButton5).setBackground(b1);
                }

                if(DayHourList.length >= 3) {
                    ColorDrawable b = new ColorDrawable();
                    b.setColor(android.graphics.Color.parseColor(DayHourColor[2][i]));
                    view.findViewById(R.id.owcaseListViewButton4).setBackground(b);
                }

                if(DayHourList.length >= 4) {
                    ColorDrawable b = new ColorDrawable();
                    b.setColor(android.graphics.Color.parseColor(DayHourColor[3][i]));
                    view.findViewById(R.id.owcaseListViewButton3).setBackground(b);
                }

                if(DayHourList.length >= 5) {
                    ColorDrawable b = new ColorDrawable();
                    b.setColor(android.graphics.Color.parseColor(DayHourColor[4][i]));
                    view.findViewById(R.id.owcaseListViewButton2).setBackground(b);
                }

                if(DayHourList.length >= 6) {
                    ColorDrawable b = new ColorDrawable();
                    b.setColor(android.graphics.Color.parseColor(DayHourColor[5][i]));
                    view.findViewById(R.id.owcaseListViewButton1).setBackground(b);
                }

                if(DayHourList.length == 7) {
                    ColorDrawable b = new ColorDrawable();
                    b.setColor(android.graphics.Color.parseColor(DayHourColor[6][i]));
                    view.findViewById(R.id.owcaseListViewButton).setBackground(b);
                }

            }


            number.setText(Count + ".");
        }else{

            view.findViewById(R.id.timetable_showcaseListViewLayout).setVisibility(View.GONE);
            view.findViewById(R.id.timetable_showcaseListViewNormalLayout).setVisibility(View.VISIBLE);
            number.setHeight(60);
            icon.setMaxHeight(60);
            ListViewText.setVisibility(View.GONE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        return view;
    }
}