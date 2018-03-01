package ca.zacharykearns.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ca.zacharykearns.studentscheduler.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTermsButtonClick(View view) {
        Intent i = new Intent(this, TermListActivity.class);
        startActivity(i);
    }
}
