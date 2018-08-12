package jp.ac.osaka_u.ty_v1.subject_management.subject;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import jp.ac.osaka_u.ty_v1.subject_management.R;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDataBaseHelper;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDatabase;
import jp.ac.osaka_u.ty_v1.subject_management.database.Start;
import jp.ac.osaka_u.ty_v1.subject_management.database.Start_Selector;

public class AddDialogFragment extends AppCompatDialogFragment {

    private View.OnClickListener okButtonClickListener = null;

    private Dialog dialog;
    private Spinner spinner;
    private Button btOk;
    private Button btClose;

    private String selectedItem = "";

    public static AddDialogFragment newInstance() {
        AddDialogFragment fragment = new AddDialogFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //XMLとの紐付け
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_attendance_layout, null, false);

        //ダイアログの作成
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btOk = view.findViewById(R.id.bt_ok);
        btClose = view.findViewById(R.id.bt_close);
        spinner = view.findViewById(R.id.spinner);
        //クローズボタン押下時はダイアログを消す
        btClose.setOnClickListener(v -> dialog.dismiss());
        //OKボタンのリスナー
        btOk.setOnClickListener(okButtonClickListener);

        final OrmaDatabase orma = OrmaDataBaseHelper.createOrmaDatabase();
        final Bundle bundle = getArguments();
        final int year = bundle.getInt("year");
        final int semester = bundle.getInt("semester");

        createSpinner(year, semester);

        return dialog;
    }

    private void createSpinner(final int year, final int semester) {
        final OrmaDatabase orma = OrmaDataBaseHelper.createOrmaDatabase();

        final Start_Selector startSelector = orma.selectFromStart().yearEq(year).and().semesterEq(semester);
        final List<String> subjectNameList = new ArrayList<>();
        for (Start start : startSelector) {
            subjectNameList.add(start.getName());
        }
        DynamicArrayAdapter spinnerAdapter = new DynamicArrayAdapter(getActivity(), R.layout.spiner_layout, subjectNameList, "科目を選択してください");
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // ここにスピナー内のアイテムを選択した際の処理を書く
                selectedItem = spinnerAdapter.getItem(position);
                spinnerAdapter.setSpinnerText(selectedItem);
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void setOnOkButtonClickListener(View.OnClickListener listener) {
        this.okButtonClickListener = listener;
    }

    public String getSubjectName(){
        return selectedItem;
    }
}