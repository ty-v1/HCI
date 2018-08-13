package jp.ac.osaka_u.ty_v1.subject_management.subject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import jp.ac.osaka_u.ty_v1.subject_management.MainActivity;
import jp.ac.osaka_u.ty_v1.subject_management.R;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDataBaseHelper;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDatabase;

public class TableFragment extends Fragment implements TableListener {

    private int year = 0;
    private int semester = 0;
    private TableModel model = null;
    private TableLayout tableLayout = null;
    private TableFragment instance = null;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final Bundle bundle = getArguments();
        year =  bundle.getInt("year");
        semester = bundle.getInt("semester");

        return inflater.inflate(R.layout.table_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        tableLayout = view.findViewById(R.id.tableLayout);

        final Spinner yearSpinner = view.findViewById(R.id.spinner1);
        final Spinner semesterSpinner = view.findViewById(R.id.spinner2);

        yearSpinner.setSelection(year - 2015);

        instance = this;

        final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = Integer.parseInt((String) yearSpinner.getSelectedItem());
                semester = semesterSpinner.getSelectedItem().equals("前期")? 0 : 1;

                model = new TableModel(
                        year,
                        semester,
                        OrmaDataBaseHelper.createOrmaDatabase());
                model.addListener(instance);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        yearSpinner.setOnItemSelectedListener(spinnerListener);
        semesterSpinner.setOnItemSelectedListener(spinnerListener);
    }

    @Override
    public void attendanceAdded(int week, int start, int end, String name) {
        update();
    }

    @Override
    public void attendanceDeleted(int week, int start, int end) {
        update();
    }

    @Override
    public void registered() {
        update();
    }

    private void update(){
//        tableLayout.removeAllViews();
        final int childCount = tableLayout.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            tableLayout.removeViews(1, childCount - 1);
        }

        for(int i=0; i<7; i++){
            @SuppressLint("InflateParams")
            final TableRow tableRow = (TableRow)getLayoutInflater().inflate(R.layout.table_row, null);

            final TextView time = tableRow.findViewById(R.id.time);
            time.setText(String.valueOf(i+1));

            final TextView monday = tableRow.findViewById(R.id.monday);
            monday.setText(model.getName(year, semester, 0, i));
            setListener(monday, 0, i);

            final TextView tuesday = tableRow.findViewById(R.id.tuesday);
            tuesday.setText(model.getName(year, semester, 1, i));
            setListener(tuesday, 1, i);

            final TextView wednesday = tableRow.findViewById(R.id.wednesday);
            wednesday.setText(model.getName(year, semester, 2, i));
            setListener(wednesday, 2, i);

            final TextView thursday = tableRow.findViewById(R.id.thursday);
            thursday.setText(model.getName(year, semester, 3, i));
            setListener(thursday, 3, i);

            final TextView friday = tableRow.findViewById(R.id.friday);
            friday.setText(model.getName(year, semester, 4, i));
            setListener(friday, 4, i);

            tableLayout.addView(tableRow,
                    new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void setListener(final TextView textView, final int week, final int time){
        if(textView.getText() != "") {
            textView.setOnClickListener(view -> {
                final AlertDialog deleteDialog = (new AlertDialog.Builder(getContext()))
                        .setTitle("削除確認")
                        .setMessage("削除しますか?")
                        .setPositiveButton("はい", (dialog, which) -> model.delete(year, semester, week, time))
                        .setNegativeButton("いいえ", (dialogInterface, i) -> {})
                        .create();
               deleteDialog.show();
            });
        }else{
            textView.setOnClickListener(view -> {

                final AddDialogFragment addDialog = AddDialogFragment.newInstance();
                // Fragmentを作成します
                final Bundle bundle = new Bundle();
                bundle.putInt("year", year);
                bundle.putInt("semester", semester);
                addDialog.setArguments(bundle);

                final View.OnClickListener listener = view1 -> {
                    model.add(addDialog.getSubjectName(), year, semester);
                    addDialog.dismiss();
                };
                addDialog.setOnOkButtonClickListener(listener);
                addDialog.show(getActivity().getSupportFragmentManager(), "");
            });
        }
    }
}
