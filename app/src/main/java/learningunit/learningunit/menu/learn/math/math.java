package learningunit.learningunit.menu.learn.math;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;

public class math extends AppCompatActivity {

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        back = (Button) findViewById(R.id.math_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        fragment_math_overview fragmentOverview;
        fragmentOverview = (fragment_math_overview) Fragment.instantiate(this, fragment_math_overview.class.getName(), null);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.math_layout, fragmentOverview);
        fragmentTransaction.commit();
    }

    public void back(){
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);
    }

}
