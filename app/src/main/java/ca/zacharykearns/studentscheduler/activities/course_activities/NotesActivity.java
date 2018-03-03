package ca.zacharykearns.studentscheduler.activities.course_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v7.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.ArrayList;

import ca.zacharykearns.studentscheduler.R;
import ca.zacharykearns.studentscheduler.activities.assessment_activities.AssessmentListActivity;
import ca.zacharykearns.studentscheduler.adapters.CourseListAdapter;
import ca.zacharykearns.studentscheduler.adapters.NoteListAdapter;
import ca.zacharykearns.studentscheduler.database.DBHelper;
import ca.zacharykearns.studentscheduler.models.Course;
import ca.zacharykearns.studentscheduler.models.Note;
import ca.zacharykearns.studentscheduler.models.Term;

public class NotesActivity extends AppCompatActivity {

    private Course mCourse;
    private Term mTerm;
    private DBHelper mDBHelper;
    private EditText mNoteInput;
    private ArrayList<Note> mNoteList;
    private NoteListAdapter mAdapter;
    private final Context mContext = this;
    private ShareActionProvider mSap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setTitle("Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        mDBHelper = new DBHelper(this);
        mCourse = mDBHelper.getCourse(((Course) i.getSerializableExtra("course")).getmCourseId());
        mTerm = mDBHelper.getTerm(((Term) i.getSerializableExtra("term")).getmTermId());
        mNoteInput = findViewById(R.id.note_input);
        mNoteList = mDBHelper.getNotes(mCourse.getmCourseId());
        mAdapter = new NoteListAdapter(this, mNoteList);
        setNotesListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                return switchActivity(CourseDetailsActivity.class);
            case R.id.menu_share:
                return shareNotes();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean switchActivity(Class<?> cls) {
        Intent i = new Intent(this, cls);
        i.putExtra("course", mCourse);
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }

    public void addNoteClick(View view) {
        if (mNoteInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note field is empty", Toast.LENGTH_SHORT).show();
        } else {
            mDBHelper.insertNote(mCourse.getmCourseId(), mNoteInput.getText().toString());
        }
    }

    private void setNotesListView() {
        ListView mListView = findViewById(R.id.list_view_notes);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        editNoteDialog(i);
                    }
                }
        );
    }

    private void editNoteDialog(final int p) {
        LayoutInflater li = LayoutInflater.from(mContext);
        View addMentorDialog = li.inflate(R.layout.dialog_note, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
        adb.setView(addMentorDialog);
        final EditText noteInput = addMentorDialog.findViewById(R.id.input_note);
        noteInput.setText(mNoteList.get(p).getmNote());
        adb
                .setCancelable(false)
                .setPositiveButton("SAVE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (noteInput.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(mContext, "Field cannot be blank", Toast.LENGTH_SHORT).show();
                                } else {
                                    mDBHelper.updateNote(mNoteList.get(p).getmNoteId(), noteInput.getText().toString());
                                    updateListView();
                                }
                            }
                        })
                .setNeutralButton("DELETE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDBHelper.deleteNote(mNoteList.get(p).getmNoteId());
                                updateListView();
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

    private void updateListView() {
        mNoteList.clear();
        mNoteList.addAll(mDBHelper.getNotes(mCourse.getmCourseId()));
        mAdapter.notifyDataSetChanged();
    }

    private boolean shareNotes() {
        StringBuilder extraText = new StringBuilder("");
        for (Note note : mNoteList) {
            extraText.append(note.getmNote());
            extraText.append("\n");
        }
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Course Notes");
        i.putExtra(Intent.EXTRA_TEXT, extraText.toString());
        startActivity(Intent.createChooser(i, "Share via"));
        return true;
    }
}
