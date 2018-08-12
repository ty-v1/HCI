package jp.ac.osaka_u.ty_v1.subject_management.score;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import jp.ac.osaka_u.ty_v1.subject_management.database.Attendance;
import jp.ac.osaka_u.ty_v1.subject_management.database.Attendance_Selector;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDataBaseHelper;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDatabase;
import jp.ac.osaka_u.ty_v1.subject_management.database.Start;
import jp.ac.osaka_u.ty_v1.subject_management.database.Subject;

public class ScoreSpinnerListener implements AdapterView.OnItemSelectedListener {

    private final String subjectName;
    private final OrmaDatabase orma;
    private final Spinner spinner;
    private final TextView gpaView;

    public ScoreSpinnerListener(final OrmaDatabase _orma, final Spinner _spinner,
                                final String _subjectName, final TextView _gpaView){
        subjectName = _subjectName;
        orma = _orma;
        spinner = _spinner;
        gpaView = _gpaView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch ((String)spinner.getSelectedItem()){
            case "S":
                orma.updateSubject()
                        .nameEq(subjectName)
                        .grade(4)
                        .execute();
                break;
            case "A":
                orma.updateSubject()
                        .nameEq(subjectName)
                        .grade(3)
                        .execute();
                break;
            case "B":
                orma.updateSubject()
                        .nameEq(subjectName)
                        .grade(2)
                        .execute();
                break;
            case "C":
                orma.updateSubject()
                        .nameEq(subjectName)
                        .grade(1)
                        .execute();
                break;
            case "F":
                orma.updateSubject()
                        .nameEq(subjectName)
                        .grade(0)
                        .execute();
                break;
            default:
                return;
        }
        updateGPA();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    private void updateGPA(){
        gpaView.setText(String.valueOf(calculateGPA()));
    }

    private float calculateGPA(){
        final Attendance_Selector attendanceSelector = orma.selectFromAttendance();

        int sumPoint = 0;
        int sumCredit = 0;

        for(Attendance attendance : attendanceSelector) {
            final Start start = orma.selectFromStart().idEq(attendance.getStartID()).get(0);
            final String subjectName = start.getName();

            final Subject subject = orma.selectFromSubject().nameEq(subjectName).getOrNull(0);
            if(subject == null || subject.getScore() == -1)
                continue;
            sumPoint += subject.getScore()*subject.getCredit();
            sumCredit += subject.getCredit();
        }

        if(sumCredit == 0)
            return 0.0f;

        return sumPoint/sumCredit;
    }

}
