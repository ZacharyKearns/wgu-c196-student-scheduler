package ca.zacharykearns.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private Term mTerm;


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
        mTerm = mIntent.getStringExtra("type").equals("ADD") ?
                null : mDBHelper.getTerm(((Term) mIntent.getSerializableExtra("term")).getmTermId());
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
                return saveTerm();
            case R.id.menu_cancel:
                return startPreviousActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setInputs() {
        if (mIntent.getStringExtra("type").equals("EDIT")) {
            mTitleInput.setText(mTerm.getmTitle());
            mStartInput.setText(mTerm.getmStart());
            mEndInput.setText(mTerm.getmEnd());
        }
    }

    private boolean saveTerm() {
        if (mTitleInput.getText().toString().trim().equals("")) {
            Toast.makeText(getBaseContext(), "Title can't be empty", Toast.LENGTH_SHORT).show();
        } else if (!Util.checkDate(mStartInput.getText().toString())) {
            Toast.makeText(getBaseContext(), "Invalid start date", Toast.LENGTH_SHORT).show();
        } else if (!Util.checkDate(mEndInput.getText().toString())) {
            Toast.makeText(getBaseContext(), "Invalid end date", Toast.LENGTH_SHORT).show();
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
                if (mDBHelper.updateTerm(
                        mTerm.getmTermId(),
                        mTitleInput.getText().toString(),
                        mStartInput.getText().toString(),
                        mEndInput.getText().toString()
                )) {
                    Toast.makeText(getBaseContext(), "Edit Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Add Failed", Toast.LENGTH_SHORT).show();
                }
            }
            startPreviousActivity();
        }
        return true;
    }

    private boolean startPreviousActivity() {
        String previous = mIntent.getStringExtra("type");
        Intent i;
        if (previous.equals("ADD")) {
            i = new Intent(this, TermListActivity.class);
        } else {
            i = new Intent(this, TermDetailsActivity.class);
            i.putExtra("term", mTerm);
        }
        startActivity(i);
        return true;
    }
}
