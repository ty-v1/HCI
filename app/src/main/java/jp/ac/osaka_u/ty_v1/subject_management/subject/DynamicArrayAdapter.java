package jp.ac.osaka_u.ty_v1.subject_management.subject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.ac.osaka_u.ty_v1.subject_management.R;

public class DynamicArrayAdapter extends ArrayAdapter<String> {

    private String spinnerText;

    public DynamicArrayAdapter(Context context, int resource, List<String> objects, String spinnerText) {
        super(context, resource, objects);
        this.spinnerText = spinnerText;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView =
                (TextView)((LayoutInflater.from(getContext()).inflate(
                        R.layout.item_layout, parent, false)));
        ///スピナー自身のビューを作成
        textView.setText(spinnerText);
        return textView;
    }

    /** ドロップダウンはそのまま */
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    public String getSpinnerText() {
        return spinnerText;
    }

    public void setSpinnerText(String spinnerText) {
        this.spinnerText = spinnerText;
    }
}
