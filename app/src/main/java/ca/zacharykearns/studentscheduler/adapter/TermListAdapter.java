package ca.zacharykearns.studentscheduler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ca.zacharykearns.studentscheduler.R;
import ca.zacharykearns.studentscheduler.model.Term;

public class TermListAdapter extends ArrayAdapter<Term> {

    public TermListAdapter(@NonNull Context context, Term[] terms) {
        super(context, R.layout.term_list_row, terms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View mTermListRow = inflater.inflate(R.layout.term_list_row, parent, false);
        Term mSingleTermItem = getItem(position);
        TextView mTextTerm = mTermListRow.findViewById(R.id.text_term);
        mTextTerm.setText(mSingleTermItem.getmTitle());
        return mTermListRow;
    }
}
