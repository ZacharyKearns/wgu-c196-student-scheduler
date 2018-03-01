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

import ca.zacharykearns.studentscheduler.adapter.TermListAdapter;
import ca.zacharykearns.studentscheduler.db.DBHelper;
import ca.zacharykearns.studentscheduler.model.Term;

public class TermListActivity extends AppCompatActivity {

    public static final String TAG = "TermListActivity";

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        setTitle("Terms");
        mDBHelper = new DBHelper(this);
        setTermListView();
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
                return startAddTermActivity(item.getTitle().toString());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setTermListView() {
        final ArrayList<Term> mTermList = mDBHelper.getTerms();
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

    private boolean startAddTermActivity(String type) {
        Intent i = new Intent(this, AddTermActivity.class);
        i.putExtra("type", type);
        startActivity(i);
        return true;
    }
}
