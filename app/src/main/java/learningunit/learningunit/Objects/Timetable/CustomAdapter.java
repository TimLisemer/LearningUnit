package learningunit.learningunit.Objects.Timetable;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        TextView number = (TextView) view.findViewById(R.id.timetable_showcaseListViewText2);
        ImageView icon = (ImageView) view.findViewById(R.id.timetable_showcaseListViewIcon);
        TextView ListViewText = (TextView)   view.findViewById(R.id.timetable_showcaseListViewText);

        if(!(i == 2 | i == 5 || i == 8 || i == 11 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26 || i == 29 || i == 32)) {
            final View view1 = view;

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view1.performHapticFeedback(1);
                    if(view1.findViewById(R.id.timetable_showcaseListViewText1).getVisibility() == View.GONE) {
                        view1.findViewById(R.id.timetable_showcaseListViewEdit).setVisibility(View.VISIBLE);
                        view1.findViewById(R.id.timetable_showcaseListViewText1).setVisibility(View.VISIBLE);
                    }else{
                        view1.findViewById(R.id.timetable_showcaseListViewEdit).setVisibility(View.GONE);
                        view1.findViewById(R.id.timetable_showcaseListViewText1).setVisibility(View.GONE);
                    }
                }
            });

            view.findViewById(R.id.timetable_showcaseListViewEdit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("showcaseListView", "View edit");
                }
            });

            icon.setVisibility(View.VISIBLE);
            number.setVisibility(View.VISIBLE);
            ListViewText.setText(HourList[i]);
            ColorDrawable c = new ColorDrawable();
            c.setColor(android.graphics.Color.parseColor(ColorCodes[i]));
            icon.setBackground(c);


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


            number.setText(Count + ".");
        }else{
            number.setHeight(35);
            icon.setMaxHeight(35);
            ListViewText.setHeight(35);
            icon.setVisibility(View.GONE);
            number.setVisibility(View.GONE);
        }
        return view;
    }
}