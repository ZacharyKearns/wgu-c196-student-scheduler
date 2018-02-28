package ca.zacharykearns.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import ca.zacharykearns.studentscheduler.adapter.TermListAdapter;
import ca.zacharykearns.studentscheduler.model.Term;

public class TermListActivity extends AppCompatActivity {

    public static final String TAG = "TermListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        setTitle("Terms");
        final Term[] mTermList = {
                new Term(1, "Winter 2018", "2018-01-01", "2018-06-01"),
                new Term(2, "Fall 2018", "2018-07-01", "2018-12-01"),
                new Term(3, "Winter 2019", "2019-01-01", "2019-06-01"),
                new Term(4, "Fall 2019", "2019-07-01", "2019-12-01")
        };
        ListAdapter mAdapter = new TermListAdapter(this, mTermList);
        ListView mListView = findViewById(R.id.list_view_terms);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Term mTerm = (Term) adapterView.getAdapter().getItem(i);
                        Intent intent = new Intent(view.getContext(), TermDetailsActivity.class);
                        intent.putExtra("term", mTerm);
                        startActivity(intent);
                    }
                }
        );
    }
}
