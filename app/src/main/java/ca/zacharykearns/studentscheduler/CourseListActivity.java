package ca.zacharykearns.studentscheduler;

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

import ca.zacharykearns.studentscheduler.adapter.CourseListAdapter;
import ca.zacharykearns.studentscheduler.db.DBHelper;
import ca.zacharykearns.studentscheduler.model.Course;
import ca.zacharykearns.studentscheduler.model.Term;

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
                return startAddCourseActivity(item.getTitle().toString());
            case android.R.id.home:
                return startTermDetailsActivity();
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
                        Intent intent = new Intent(view.getContext(), CourseDetailsActivity.class);
                        intent.putExtra("course", mCourse);
                        startActivity(intent);
                    }
                }
        );
    }

    private boolean startAddCourseActivity(String type) {
        Intent i = new Intent(this, AddCourseActivity.class);
        i.putExtra("type", type);
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }

    private boolean startTermDetailsActivity() {
        Intent i1 = new Intent(this, TermDetailsActivity.class);
        i1.putExtra("term", mTerm);
        startActivity(i1);
        return true;
    }
}
