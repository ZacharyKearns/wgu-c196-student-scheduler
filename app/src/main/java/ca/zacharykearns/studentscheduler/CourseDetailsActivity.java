package ca.zacharykearns.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import ca.zacharykearns.studentscheduler.model.Course;

public class CourseDetailsActivity extends AppCompatActivity {

    private Course mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Course Details");
        Intent i = getIntent();
        mCourse = (Course) i.getSerializableExtra("course");
        TextView mTitle = findViewById(R.id.course_title);
        TextView mStart = findViewById(R.id.course_start_date_value);
        TextView mEnd = findViewById(R.id.course_end_date_value);
        TextView mStatus = findViewById(R.id.course_status_value);
        mTitle.setText(mCourse.getmTitle());
        mStart.setText(mCourse.getmStart());
        mEnd.setText(mCourse.getmEnd());
        mStatus.setText(mCourse.getmStatus());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
