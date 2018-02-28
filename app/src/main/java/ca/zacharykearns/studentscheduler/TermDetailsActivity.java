package ca.zacharykearns.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ca.zacharykearns.studentscheduler.model.Term;

public class TermDetailsActivity extends AppCompatActivity {

    private Term term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        setTitle("Term Details");
        Intent i = getIntent();
        term = (Term) i.getSerializableExtra("term");
        TextView mTermTitle = findViewById(R.id.term_title);
        TextView mTermStart = findViewById(R.id.term_start_date_value);
        TextView mTermEnd = findViewById(R.id.term_end_date_value);
        mTermTitle.setText(term.getmTitle());
        mTermStart.setText(term.getmStart());
        mTermEnd.setText(term.getmEnd());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.term_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_edit_term) {
            Intent i = new Intent(this, AddTermActivity.class);
            i.putExtra("type", item.getTitle().toString());
            i.putExtra("term", term);
            startActivity(i);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
