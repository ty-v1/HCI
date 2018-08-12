package jp.ac.osaka_u.ty_v1.subject_management;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import jp.ac.osaka_u.ty_v1.subject_management.database.Category;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDataBaseHelper;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDatabase;
import jp.ac.osaka_u.ty_v1.subject_management.database.Subject;
import jp.ac.osaka_u.ty_v1.subject_management.plan.Schedule;
import jp.ac.osaka_u.ty_v1.subject_management.plan.ScheduleFragment;
import jp.ac.osaka_u.ty_v1.subject_management.score.ScoreFragment;
import jp.ac.osaka_u.ty_v1.subject_management.subject.TableFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OrmaDataBaseHelper.createOrmaDatabase(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("講義");

        toolbar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.subjectItem:
                    toolbar.setTitle("講義");

                    final Bundle bundle = new Bundle();
                    final Calendar calendar = Calendar.getInstance();
                    bundle.putInt("year", calendar.get(Calendar.YEAR));
                    bundle.putInt("semester", calculateSemester(calendar));
                    final TableFragment fragment = new TableFragment();
                    fragment.setArguments(bundle);
                    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                    return true;
                case R.id.scoreItem:
                    toolbar.setTitle("成績");
                    // Fragmentを作成します
                    final ScoreFragment scoreFragment = new ScoreFragment();
                    getSupportFragmentManager()
                            .beginTransaction().
                            replace(R.id.container, scoreFragment)
                            .commit();
                    return true;
                default:
                    return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);

        final Bundle bundle = new Bundle();
        final Calendar calendar = Calendar.getInstance();
        bundle.putInt("year", calendar.get(Calendar.YEAR));
        bundle.putInt("semester", calculateSemester(calendar));

        final TableFragment fragment = new TableFragment();
        fragment.setArguments(bundle);

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }

    private int calculateSemester(final Calendar calendar) {
        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                return 1;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                return 0;
            default:
                return -1;
        }
    }
}
