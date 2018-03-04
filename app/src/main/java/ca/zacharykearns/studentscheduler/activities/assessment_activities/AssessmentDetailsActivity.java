package ca.zacharykearns.studentscheduler.activities.assessment_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.zacharykearns.studentscheduler.R;
import ca.zacharykearns.studentscheduler.activities.course_activities.CourseListActivity;
import ca.zacharykearns.studentscheduler.activities.term_activities.AddTermActivity;
import ca.zacharykearns.studentscheduler.activities.term_activities.TermListActivity;
import ca.zacharykearns.studentscheduler.adapters.GoalDateListAdapter;
import ca.zacharykearns.studentscheduler.database.DBHelper;
import ca.zacharykearns.studentscheduler.models.Assessment;
import ca.zacharykearns.studentscheduler.models.Course;
import ca.zacharykearns.studentscheduler.models.GoalDate;
import ca.zacharykearns.studentscheduler.models.Term;

public class AssessmentDetailsActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private Assessment mAssessment;
    private Term mTerm;
    private Course mCourse;
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Assessment Details");
        mDBHelper = new DBHelper(this);
        mIntent = getIntent();
        mAssessment = mDBHelper.getAssessment(((Assessment) mIntent.getSerializableExtra("assessment")).getmAssessmentId());
        mTerm = mDBHelper.getTerm(((Term) mIntent.getSerializableExtra("term")).getmTermId());
        mCourse = mDBHelper.getCourse(((Course) mIntent.getSerializableExtra("course")).getmCourseId());
        setTextViews();
        setGoalDateListView();
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
                return switchActivity(AssessmentListActivity.class, null);
            case R.id.menu_edit:
                return switchActivity(AddAssessmentActivity.class, item.getTitle().toString());
            case R.id.menu_delete:
                return deleteAssessment();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean switchActivity(Class<?> cls, String type) {
        Intent i = new Intent(this, cls);
        if (cls.equals(AddAssessmentActivity.class)) {
            i.putExtra("type", type);
            i.putExtra("assessment", mAssessment);
        }
        i.putExtra("term", mTerm);
        i.putExtra("course", mCourse);
        startActivity(i);
        return true;
    }

    private void setTextViews() {
        TextView mTitle = findViewById(R.id.assessment_title);
        TextView mType = findViewById(R.id.assessment_type_value);
        TextView mDue = findViewById(R.id.assessment_due_date_value);
        mTitle.setText(mAssessment.getmTitle());
        mType.setText(mAssessment.getmType());
        mDue.setText(mAssessment.getmDue());
    }

    private void setGoalDateListView() {
        ArrayList<GoalDate> mGoalDateList = mDBHelper.getGoalDates(mAssessment.getmAssessmentId());
        ListView mListView = findViewById(R.id.list_view_goal_dates);
        ListAdapter mAdapter = new GoalDateListAdapter(this, mGoalDateList);
        mListView.setAdapter(mAdapter);
    }

    private boolean deleteAssessment() {
        if (mDBHelper.deleteAssessment(mAssessment.getmAssessmentId())) {
            Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        }
        switchActivity(AssessmentListActivity.class, null);
        return true;
    }
}
