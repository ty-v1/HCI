package jp.ac.osaka_u.ty_v1.subject_management.score;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import jp.ac.osaka_u.ty_v1.subject_management.R;
import jp.ac.osaka_u.ty_v1.subject_management.database.Attendance;
import jp.ac.osaka_u.ty_v1.subject_management.database.Attendance_Selector;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDataBaseHelper;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDatabase;
import jp.ac.osaka_u.ty_v1.subject_management.database.Start;
import jp.ac.osaka_u.ty_v1.subject_management.database.Subject;
import jp.ac.osaka_u.ty_v1.subject_management.subject.AddDialogFragment;
import jp.ac.osaka_u.ty_v1.subject_management.subject.TableFragment;
import jp.ac.osaka_u.ty_v1.subject_management.subject.TableModel;

public class ScoreFragment extends Fragment {

    private TableLayout tableLayout = null;
    private OrmaDatabase orma = null;
    private TextView gpaView = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.score_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        tableLayout = view.findViewById(R.id.scoreTable);
        gpaView = view.findViewById(R.id.GPA);
        orma = OrmaDataBaseHelper.createOrmaDatabase();
        update();
    }

    private void update(){
        final int childCount = tableLayout.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            tableLayout.removeViews(1, childCount - 1);
        }
        final Attendance_Selector attendanceSelector = orma.selectFromAttendance();

        for(Attendance attendance : attendanceSelector){
            final Start start = orma.selectFromStart().idEq(attendance.getStartID()).get(0);
            final TableRow scoreRow = (TableRow)getLayoutInflater().inflate(R.layout.score_row, null);

            final TextView subjectName = scoreRow.findViewById(R.id.rowSubjectName);
            subjectName.setText(attendance.getName());

            final TextView year = scoreRow.findViewById(R.id.rowYear);
            year.setText(String.valueOf(start.getYear()));

            final TextView semester = scoreRow.findViewById(R.id.rowSemester);
            semester.setText(start.getSemester() == 0 ? "前期" : "後期");

            final Spinner score = scoreRow.findViewById(R.id.scoreSpinner);
            score.setSelection(getIndexByScore(orma.selectFromSubject().nameEq(start.getName()).getOrNull(0)));
            score.setOnItemSelectedListener(new ScoreSpinnerListener(orma, score, start.getName(), gpaView));

            tableLayout.addView(scoreRow,
                    new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }

    }

    private int getIndexByScore(final Subject subject){
        if(subject == null)
            return 4;

        switch (subject.getScore()){
            case 4:
            case 3:
            case 2:
            case 1:
            case 0:
                return 4 - subject.getScore();
            default:
                return 0;
        }
    }
}
