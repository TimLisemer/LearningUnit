package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import learningunit.learningunit.R;

public class HomeworkCustomAdapter extends BaseAdapter {

    ArrayList<Homework> eventlist = new ArrayList<Homework>();
    private LayoutInflater inflater;
    private Activity activity;
    boolean out = false;

    public HomeworkCustomAdapter(Activity activity, ArrayList<Homework> eventlist){

        ArrayList<Event> elist = new ArrayList<Event>();
        for(Homework h : eventlist){
            elist.add((Event) h);
        }
        elist = EventMethods.SortEventList(elist);
        for(Event e : elist){
            this.eventlist.add((Homework) e);
        }

        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return eventlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView = inflater.inflate(R.layout.organizer_homework_listview, null);
        convertView.findViewById(R.id.organizer_Exam_ListView_RootLayout).setVisibility(View.GONE);
        convertView.findViewById(R.id.organizer_homework_ListView_RootLayout).setVisibility(View.VISIBLE);

        final View cView = convertView;
        final Homework h = eventlist.get(position);

        TextView title = (TextView) convertView.findViewById(R.id.organizer_homework_ListView_Title);
        title.setText(h.getTitle());

        TextView Date = (TextView) convertView.findViewById(R.id.organizer_homework_ListView_Date);
        Date.setText(h.getDay() + "." + h.getMonth() + "." + h.getYear());

        ImageView image = (ImageView) convertView.findViewById(R.id.organizer_homework_ListView_ImageView);
        image.setBackgroundColor(android.graphics.Color.parseColor(h.getHour().getColorCode()));

        convertView.findViewById(R.id.organizer_homework_ListView_SecondLayout).setVisibility(View.GONE);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out == false) {
                    cView.findViewById(R.id.organizer_homework_ListView_SecondLayout).setVisibility(View.VISIBLE);

                    TextView Hour = (TextView) cView.findViewById(R.id.organizer_homework_ListView_HourName);
                    Hour.setText(h.getHour().getName());

                    TextView Description = (TextView) cView.findViewById(R.id.organizer_homework_ListView_DescriptionText);
                    Description.setText(h.getDescription());

                    final Button done = (Button) cView.findViewById(R.id.organizer_homework_ListView_Done);
                    if (h.getDone()) {
                        done.setText(activity.getResources().getString(R.string.NotDone));
                    } else {
                        done.setText(activity.getResources().getString(R.string.Done));
                    }
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (h.getDone()) {
                                done.setText(activity.getResources().getString(R.string.Done));
                                h.setDone(false);
                            } else {
                                done.setText(activity.getResources().getString(R.string.NotDone));
                                h.setDone(true);
                            }
                        }
                    });
                    out = true;
                }else {
                    out = false;
                    cView.findViewById(R.id.organizer_homework_ListView_SecondLayout).setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }
}
