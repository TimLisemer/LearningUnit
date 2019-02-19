package learningunit.learningunit.Objects.Timetable;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import learningunit.learningunit.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String HourList[];
    String ColorCodes[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] HourList, String[] ColorCodes) {
        this.context = context;
        this.HourList = HourList;
        this.ColorCodes = ColorCodes;
        inflter = (LayoutInflater.from(applicationContext));
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
        TextView ListViewText = (TextView)   view.findViewById(R.id.timetable_showcaseListViewText);
        ImageView icon = (ImageView) view.findViewById(R.id.timetable_showcaseListViewIcon);
        ListViewText.setText(HourList[i]);
        ColorDrawable c = new ColorDrawable();
        c.setColor(android.graphics.Color.parseColor(ColorCodes[i]));
        icon.setBackground(c);
        return view;
    }
}