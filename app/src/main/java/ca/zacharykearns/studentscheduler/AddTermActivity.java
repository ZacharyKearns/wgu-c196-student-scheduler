package ca.zacharykearns.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ca.zacharykearns.studentscheduler.db.DBHelper;
import ca.zacharykearns.studentscheduler.model.Term;

public class AddTermActivity extends AppCompatActivity {

    public static final String TAG = "AddTermActivity";

    private DBHelper mDBHelper;
    private EditText mTitleInput;
    private EditText mStartInput;
    private EditText mEndInput;
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        setTitle("Add Term");
        mDBHelper = new DBHelper(this);
        mTitleInput = findViewById(R.id.title_input);
        mStartInput = findViewById(R.id.start_input);
        mEndInput = findViewById(R.id.end_input);
        mIntent = getIntent();
        if (mIntent.getStringExtra("type").equals("EDIT")) {
            Term term = (Term) mIntent.getSerializableExtra("term");
            mTitleInput.setText(term.getmTitle());
            mStartInput.setText(term.getmStart());
            mEndInput.setText(term.getmEnd());
        }
    }

    public void onSave(View view) {
        if (mTitleInput.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Title can't be empty", Toast.LENGTH_SHORT).show();
        } else if (mStartInput.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Start date can't be empty", Toast.LENGTH_SHORT).show();
        } else if (mEndInput.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "End date can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (mIntent.getStringExtra("type").equals("ADD")) {
                if (mDBHelper.insertTerm(
                        mTitleInput.getText().toString(),
                        mStartInput.getText().toString(),
                        mEndInput.getText().toString()
                )) {
                    Toast.makeText(getBaseContext(), "Add Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Add Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Term term = (Term) mIntent.getSerializableExtra("term");
                if (mDBHelper.updateTerm(
                        term.getmTermId(),
                        mTitleInput.getText().toString(),
                        mStartInput.getText().toString(),
                        mEndInput.getText().toString()
                )) {
                    Toast.makeText(getBaseContext(), "Edit Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Add Failed", Toast.LENGTH_SHORT).show();
                }
            }
            Intent i = new Intent(this, TermListActivity.class);
            startActivity(i);
        }
    }
}
