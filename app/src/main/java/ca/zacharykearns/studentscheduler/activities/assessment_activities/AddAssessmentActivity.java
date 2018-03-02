package ca.zacharykearns.studentscheduler.activities.assessment_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ca.zacharykearns.studentscheduler.R;
import ca.zacharykearns.studentscheduler.Util;
import ca.zacharykearns.studentscheduler.database.DBHelper;
import ca.zacharykearns.studentscheduler.models.Assessment;
import ca.zacharykearns.studentscheduler.models.Course;
import ca.zacharykearns.studentscheduler.models.Term;

public class AddAssessmentActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private Intent mIntent;
    private Course mCourse;
    private Term mTerm;
    private Spinner mSpinner;
    private EditText mTitleInput;
    private EditText mDueInput;
    private Assessment mAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        mDBHelper = new DBHelper(this);
        mIntent = getIntent();
        mCourse = mDBHelper.getCourse(((Course) mIntent.getSerializableExtra("course")).getmCourseId());
        mTerm = mDBHelper.getTerm(((Term) mIntent.getSerializableExtra("term")).getmTermId());
        mAssessment = mIntent.getStringExtra("type").equals("ADD") ?
                null : mDBHelper.getAssessment(((Assessment) mIntent.getSerializableExtra("assessment")).getmAssessmentId());
        String title = mIntent.getStringExtra("type").equals("ADD") ? "Add" : "Edit";
        setTitle(title + " Assessment");
        mSpinner = findViewById(R.id.type_spinner);
        mTitleInput = findViewById(R.id.title_input);
        mDueInput = findViewById(R.id.due_input);
        setSpinner();
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
                return saveAssessment();
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
            i = new Intent(this, AssessmentListActivity.class);
        } else {
            i = new Intent(this, AssessmentDetailsActivity.class);
            i.putExtra("assessment", mAssessment);
        }
        i.putExtra("course", mCourse);
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }

    private void setInputs() {
        if (mIntent.getStringExtra("type").equals("EDIT")) {
            mTitleInput.setText(mAssessment.getmTitle());
            mDueInput.setText(mAssessment.getmDue());
        }
    }

    private void setSpinner() {
        ArrayList<String> a = new ArrayList<>();
        a.add("Objective");
        a.add("Performance");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        if (mAssessment != null) {
            int i = a.indexOf(mAssessment.getmType());
            mSpinner.setSelection(i);
        }
    }

    private boolean saveAssessment() {
        if (mTitleInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!Util.checkDate(mDueInput.getText().toString())) {
            Toast.makeText(getBaseContext(), "Invalid due date", Toast.LENGTH_SHORT).show();
        } else {
            if (mIntent.getStringExtra("type").equals("ADD")) {
                if (mDBHelper.insertAssessment(
                        mTitleInput.getText().toString(),
                        mSpinner.getSelectedItem().toString(),
                        mDueInput.getText().toString(),
                        mCourse.getmCourseId()
                )) {
                    Toast.makeText(getBaseContext(), "Add Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Add Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mDBHelper.updateAssessment(
                        mAssessment.getmAssessmentId(),
                        mTitleInput.getText().toString(),
                        mSpinner.getSelectedItem().toString(),
                        mDueInput.getText().toString()
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
