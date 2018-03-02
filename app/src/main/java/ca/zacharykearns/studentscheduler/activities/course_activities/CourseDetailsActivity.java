package ca.zacharykearns.studentscheduler.activities.course_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.zacharykearns.studentscheduler.R;
import ca.zacharykearns.studentscheduler.activities.assessment_activities.AssessmentListActivity;
import ca.zacharykearns.studentscheduler.adapters.AddMentorListAdapter;
import ca.zacharykearns.studentscheduler.adapters.MentorListAdapter;
import ca.zacharykearns.studentscheduler.database.DBHelper;
import ca.zacharykearns.studentscheduler.models.Course;
import ca.zacharykearns.studentscheduler.models.Mentor;
import ca.zacharykearns.studentscheduler.models.Term;

public class CourseDetailsActivity extends AppCompatActivity {

    private Course mCourse;
    private DBHelper mDBHelper;
    private Term mTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Course Details");
        Intent i = getIntent();
        mDBHelper = new DBHelper(this);
        mTerm = mDBHelper.getTerm(((Term) i.getSerializableExtra("term")).getmTermId());
        mCourse = mDBHelper.getCourse(((Course) i.getSerializableExtra("course")).getmCourseId());
        TextView mTitle = findViewById(R.id.course_title);
        TextView mStart = findViewById(R.id.course_start_date_value);
        TextView mEnd = findViewById(R.id.course_end_date_value);
        TextView mStatus = findViewById(R.id.course_status_value);
        mTitle.setText(mCourse.getmTitle());
        mStart.setText(mCourse.getmStart());
        mEnd.setText(mCourse.getmEnd());
        mStatus.setText(mCourse.getmStatus());
        setMentorListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                return switchActivity(CourseListActivity.class, null);
            case R.id.menu_edit:
                return switchActivity(AddCourseActivity.class, item.getTitle().toString());
            case R.id.menu_delete:
                return deleteCourse();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void viewAssessmentsButtonClick(View view) {
        switchActivity(AssessmentListActivity.class, null);
    }

    private void setMentorListView() {
        ArrayList<Mentor> mMentorList = mDBHelper.getMentors(mCourse.getmCourseId());
        ListView mListView = findViewById(R.id.list_view_mentors);
        ListAdapter mAdapter = new MentorListAdapter(this, mMentorList);
        mListView.setAdapter(mAdapter);
    }

    private boolean switchActivity(Class<?> cls, String type) {
        Intent i = new Intent(this, cls);
        if (cls.equals(AssessmentListActivity.class)) {
            i.putExtra("course", mCourse);
        } else if (cls.equals(AddCourseActivity.class)) {
            i.putExtra("type", type);
            i.putExtra("course", mCourse);
        }
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }

    private boolean deleteCourse() {
        return true;
    }
}
