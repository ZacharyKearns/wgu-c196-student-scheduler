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
import ca.zacharykearns.studentscheduler.models.Course;

public class CourseListAdapter extends ArrayAdapter<Course> {

    public CourseListAdapter(@NonNull Context context, ArrayList<Course> courses) {
        super(context, R.layout.list_row_course, courses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_course, parent, false);
            Course mSingleCourseItem = getItem(position);
            TextView mTextCourse = convertView.findViewById(R.id.text_course);
            mTextCourse.setText(mSingleCourseItem.getmTitle());
        }
        return convertView;
    }
}
