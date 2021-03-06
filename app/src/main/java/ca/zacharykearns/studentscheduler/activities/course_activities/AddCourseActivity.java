package ca.zacharykearns.studentscheduler.activities.course_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.zacharykearns.studentscheduler.R;
import ca.zacharykearns.studentscheduler.Util;
import ca.zacharykearns.studentscheduler.adapters.AddMentorListAdapter;
import ca.zacharykearns.studentscheduler.database.DBHelper;
import ca.zacharykearns.studentscheduler.models.Course;
import ca.zacharykearns.studentscheduler.models.Mentor;
import ca.zacharykearns.studentscheduler.models.Term;

public class AddCourseActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private Term mTerm;
    private Course mCourse;
    private Intent mIntent;
    private ArrayList<Mentor> mMentorList;
    private final Context context = this;

    private EditText mTitleInput;
    private EditText mStartInput;
    private EditText mEndInput;
    private EditText mStatusInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        mIntent = getIntent();
        String title = mIntent.getStringExtra("type").equals("ADD") ? "Add" : "Edit";
        setTitle(title + " Course");
        mDBHelper = new DBHelper(this);
        mTerm = mDBHelper.getTerm(((Term) mIntent.getSerializableExtra("term")).getmTermId());
        mCourse = mIntent.getStringExtra("type").equals("ADD") ?
                null : mDBHelper.getCourse(((Course) mIntent.getSerializableExtra("course")).getmCourseId());
        setMentorListView();
        Button mAddMentorButton = findViewById(R.id.button_add_mentor);
        mAddMentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMentorDialog();
            }
        });
        mTitleInput = findViewById(R.id.title_input);
        mStartInput = findViewById(R.id.start_input);
        mEndInput = findViewById(R.id.end_input);
        mStatusInput = findViewById(R.id.status_input);
        setInputs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                return saveCourse();
            case R.id.menu_cancel:
                return switchActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean switchActivity() {
        String previous = mIntent.getStringExtra("type");
        Intent i;
        if (previous.equals("ADD")) {
            i = new Intent(this, CourseListActivity.class);
        } else {
            i = new Intent(this, CourseDetailsActivity.class);
            i.putExtra("course", mCourse);
        }
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }

    private void setMentorListView() {
        if (mIntent.getStringExtra("type").equals("ADD")) {
            mMentorList = new ArrayList<>();
        } else {
            mMentorList = mDBHelper.getMentors(mCourse.getmCourseId());
        }
        ListView mListView = findViewById(R.id.list_view_mentors);
        ListAdapter mAdapter = new AddMentorListAdapter(this, mMentorList);
        mListView.setAdapter(mAdapter);
    }

    private void setInputs() {
        if (mIntent.getStringExtra("type").equals("EDIT")) {
            mTitleInput.setText(mCourse.getmTitle());
            mStartInput.setText(mCourse.getmStart());
            mEndInput.setText(mCourse.getmEnd());
            mStatusInput.setText(mCourse.getmStatus());
        }
    }

    private void addMentorDialog() {
        LayoutInflater li = LayoutInflater.from(context);
        View addMentorDialog = li.inflate(R.layout.dialog_mentor, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setView(addMentorDialog);
        final EditText nameInput = addMentorDialog.findViewById(R.id.input_name);
        final EditText phoneInput = addMentorDialog.findViewById(R.id.input_phone);
        final EditText emailInput = addMentorDialog.findViewById(R.id.input_email);
        adb
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (nameInput.getText().toString().trim().isEmpty() ||
                                        phoneInput.getText().toString().trim().isEmpty() ||
                                        emailInput.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(getBaseContext(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                                } else {
                                    mMentorList.add(new Mentor(
                                            nameInput.getText().toString(),
                                            phoneInput.getText().toString(),
                                            emailInput.getText().toString()
                                    ));
                                }
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog alertDialog = adb.create();
        alertDialog.show();
    }

    private boolean saveCourse() {
        if (mTitleInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!Util.checkDate(mStartInput.getText().toString())) {
            Toast.makeText(getBaseContext(), "Invalid start date", Toast.LENGTH_SHORT).show();
        } else if (!Util.checkDate(mEndInput.getText().toString())) {
            Toast.makeText(getBaseContext(), "Invalid end date", Toast.LENGTH_SHORT).show();
        } else if (mStatusInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "Status cannot be empty", Toast.LENGTH_SHORT).show();
        } else if(mMentorList.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please add a course mentor", Toast.LENGTH_SHORT).show();
        } else {
            if (mIntent.getStringExtra("type").equals("ADD")) {
                if (mDBHelper.insertCourse(
                        mTitleInput.getText().toString(),
                        mStartInput.getText().toString(),
                        mEndInput.getText().toString(),
                        mStatusInput.getText().toString(),
                        mTerm.getmTermId(),
                        mMentorList
                )) {
                    Toast.makeText(getBaseContext(), "Add Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Add Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mDBHelper.updateCourse(
                        mCourse.getmCourseId(),
                        mTitleInput.getText().toString(),
                        mStartInput.getText().toString(),
                        mEndInput.getText().toString(),
                        mStatusInput.getText().toString(),
                        mMentorList
                )) {
                    Toast.makeText(getBaseContext(), "Edit Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Edit Failed", Toast.LENGTH_SHORT).show();
                }
            }
            switchActivity();
        }
        return true;
    }
}
