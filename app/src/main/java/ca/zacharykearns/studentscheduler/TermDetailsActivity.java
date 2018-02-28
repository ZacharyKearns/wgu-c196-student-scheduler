package ca.zacharykearns.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ca.zacharykearns.studentscheduler.model.Term;

public class TermDetailsActivity extends AppCompatActivity {

    private TextView mTermTitle;
    private TextView mTermStart;
    private TextView mTermEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        setTitle("Term Details");
        Intent i = getIntent();
        Term term = (Term) i.getSerializableExtra("term");
        mTermTitle = findViewById(R.id.term_title);
        mTermStart = findViewById(R.id.term_start_date_value);
        mTermEnd = findViewById(R.id.term_end_date_value);
        mTermTitle.setText(term.getmTitle());
        mTermStart.setText(term.getmStart());
        mTermEnd.setText(term.getmEnd());
    }
}
