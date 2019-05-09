package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.ArrayList;
import learningunit.learningunit.R;
import learningunit.learningunit.menu.learn.vocabulary.Vokabeln;

public class PublicListCustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ArrayList<String>> SharedLists;
    private Activity activity;

    public PublicListCustomAdapter(Activity activity, ArrayList<ArrayList<String>> SharedList){
        SharedLists = SharedList;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return SharedLists.size();
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
        convertView = inflater.inflate(R.layout.vocabulary_publiclistview, null);

        final ArrayList<String> SharedList = SharedLists.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vokabeln.openSharedList(SharedList, activity);
            }
        });

        TextView nameView = (TextView) convertView.findViewById(R.id.timetable_PublicListView_titel);
        TextView lannguages = (TextView) convertView.findViewById(R.id.timetable_PublicListView_languages);
        TextView languageView1 = (TextView) convertView.findViewById(R.id.timetable_PublicListView_language1);
        TextView languageView2 = (TextView) convertView.findViewById(R.id.timetable_PublicListView_language2);
        TextView countView = (TextView) convertView.findViewById(R.id.timetable_PublicListView_Count);
        TextView creatorView = (TextView) convertView.findViewById(R.id.timetable_PublicListView_Creator);
        TextView idView = (TextView) convertView.findViewById(R.id.timetable_PublicListView_Id);
        TextView followerView = (TextView) convertView.findViewById(R.id.timetable_PublicListView_Followers);

        nameView.setText(SharedList.get(1));
        lannguages.setText("<-->");
        languageView1.setText(SharedList.get(2));
        languageView2.setText(SharedList.get(3));
        countView.setText(SharedList.get(5) + " " + activity.getResources().getString(R.string.Vocabularys));
        creatorView.setText(activity.getResources().getString(R.string.Creator) + " " + SharedList.get(4));
        idView.setText("ID: " + SharedList.get(0));
        followerView.setText(SharedList.get(7) + " Follower");

        nameView.setSelected(true);
        languageView1.setSelected(true);
        languageView2.setSelected(true);
        countView.setSelected(true);
        creatorView.setSelected(true);
        idView.setSelected(true);
        followerView.setSelected(true);


        return  convertView;
    }























}
