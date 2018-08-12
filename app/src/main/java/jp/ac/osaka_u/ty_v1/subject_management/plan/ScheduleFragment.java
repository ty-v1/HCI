package jp.ac.osaka_u.ty_v1.subject_management.plan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import jp.ac.osaka_u.ty_v1.subject_management.R;

public class ScheduleFragment extends Fragment implements PlanListener{

    public static ScheduleFragment newInstance(final int year, final int month) {
        final Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);

        final ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.schedule_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final LinearLayout planList = (LinearLayout) view.findViewById(R.id.plan_list);

        final Bundle bundle = getArguments();
        final int year = bundle.getInt("year");
        final int month = bundle.getInt("month");



    }

    @Override
    public void planAdded() {

    }

    @Override
    public void planChanged() {

    }

    @Override
    public void planDeleted() {

    }
}
