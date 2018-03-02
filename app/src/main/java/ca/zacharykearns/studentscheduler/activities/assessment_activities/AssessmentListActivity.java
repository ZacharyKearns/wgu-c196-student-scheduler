package ca.zacharykearns.studentscheduler.activities.assessment_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.zacharykearns.studentscheduler.R;
import ca.zacharykearns.studentscheduler.activities.course_activities.CourseDetailsActivity;
import ca.zacharykearns.studentscheduler.activities.term_activities.TermDetailsActivity;
import ca.zacharykearns.studentscheduler.adapters.AssessmentListAdapter;
import ca.zacharykearns.studentscheduler.adapters.TermListAdapter;
import ca.zacharykearns.studentscheduler.database.DBHelper;
import ca.zacharykearns.studentscheduler.models.Assessment;
import ca.zacharykearns.studentscheduler.models.Course;
import ca.zacharykearns.studentscheduler.models.Term;

public class AssessmentListActivity extends AppCompatActivity {

    private Intent mIntent;
    private Course mCourse;
    private Term mTerm;
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        setTitle("Assessments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mIntent = getIntent();
        mDBHelper = new DBHelper(this);
        mCourse = mDBHelper.getCourse(((Course) mIntent.getSerializableExtra("course")).getmCourseId());
        mTerm = mDBHelper.getTerm(((Term) mIntent.getSerializableExtra("term")).getmTermId());
        setAssessmentListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                return switchActivity(AddAssessmentActivity.class, item.getTitle().toString(), null);
            case android.R.id.home:
                return switchActivity(CourseDetailsActivity.class, null, null);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setAssessmentListView() {
        final ArrayList<Assessment> mAssessmentList = mDBHelper.getAssessments(mCourse.getmCourseId());
        ListAdapter mAdapter = new AssessmentListAdapter(this, mAssessmentList);
        ListView mListView = findViewById(R.id.list_view_assessments);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Assessment mAssessment = (Assessment) adapterView.getAdapter().getItem(i);
                        switchActivity(AssessmentDetailsActivity.class, null, mAssessment);
                    }
                }
        );
    }

    private boolean switchActivity(Class<?> cls, String type, Assessment a) {
        Intent i = new Intent(this, cls);
        if (cls.equals(AddAssessmentActivity.class)) {
            i.putExtra("type", type);
        } else if (cls.equals(AssessmentDetailsActivity.class)) {
            i.putExtra("assessment", a);
        }
        i.putExtra("course", mCourse);
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }
}
