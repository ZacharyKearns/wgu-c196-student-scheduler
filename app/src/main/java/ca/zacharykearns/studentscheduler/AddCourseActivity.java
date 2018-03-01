package ca.zacharykearns.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import ca.zacharykearns.studentscheduler.db.DBHelper;
import ca.zacharykearns.studentscheduler.model.Term;

public class AddCourseActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private Term mTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle("Add Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        mDBHelper = new DBHelper(this);
        mTerm = mDBHelper.getTerm(((Term) i.getSerializableExtra("term")).getmTermId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return startCourseListActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean startCourseListActivity() {
        Intent i = new Intent(this, CourseListActivity.class);
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }
}
