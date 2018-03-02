package ca.zacharykearns.studentscheduler.activities.course_activities;

import android.content.Context;
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
import ca.zacharykearns.studentscheduler.activities.term_activities.TermDetailsActivity;
import ca.zacharykearns.studentscheduler.adapters.CourseListAdapter;
import ca.zacharykearns.studentscheduler.database.DBHelper;
import ca.zacharykearns.studentscheduler.models.Course;
import ca.zacharykearns.studentscheduler.models.Term;

public class CourseListActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private Term mTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setTitle("Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDBHelper = new DBHelper(this);
        Intent i = getIntent();
        mTerm = mDBHelper.getTerm(((Term) i.getSerializableExtra("term")).getmTermId());
        setCourseListView();
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
                return switchActivity(AddCourseActivity.class, item.getTitle().toString(), null);
            case android.R.id.home:
                return switchActivity(TermDetailsActivity.class, null, null);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCourseListView() {
        final ArrayList<Course> mCourseList = mDBHelper.getCourses(mTerm.getmTermId());
        ListAdapter mAdapter = new CourseListAdapter(this, mCourseList);
        ListView mListView = findViewById(R.id.list_view_courses);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Course mCourse = (Course) adapterView.getAdapter().getItem(i);
                        switchActivity(CourseDetailsActivity.class, null, mCourse);
                    }
                }
        );
    }

    private boolean switchActivity(Class<?> cls, String type, Course course) {
        Intent i = new Intent(this, cls);
        if (cls.equals(AddCourseActivity.class)) {
            i.putExtra("type", type);
        } else if (cls.equals(CourseDetailsActivity.class)) {
            i.putExtra("course", course);
        }
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }
}
