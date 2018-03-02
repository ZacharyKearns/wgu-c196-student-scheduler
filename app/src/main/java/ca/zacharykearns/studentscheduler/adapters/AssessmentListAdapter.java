package ca.zacharykearns.studentscheduler.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.zacharykearns.studentscheduler.R;
import ca.zacharykearns.studentscheduler.models.Assessment;
import ca.zacharykearns.studentscheduler.models.Term;

/**
 * Created by zacharykearns on 2018-03-02.
 */

public class AssessmentListAdapter extends ArrayAdapter<Assessment> {

    public AssessmentListAdapter(@NonNull Context context, ArrayList<Assessment> assessments) {
        super(context, R.layout.assessment_list_row, assessments);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.assessment_list_row, parent, false);
            Assessment mSingleAssessmentItem = getItem(position);
            TextView mTextAssessment = convertView.findViewById(R.id.text_assessment);
            mTextAssessment.setText(mSingleAssessmentItem.getmTitle());
        }
        return convertView;
    }
}
