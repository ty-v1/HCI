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
               case R.id.planItem:
                   toolbar.setTitle("予定");
                   return true;
               case R.id.subjectItem:
                   toolbar.setTitle("講義");
                   // Fragmentを作成します
                   final Bundle bundle = new Bundle();
                   bundle.putInt("year", 2018);
                   bundle.putInt("semester", 1);
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
//
//        // Fragmentを作成します
//        ScheduleFragment fragment = ScheduleFragment.newInstance(2019, 2);
//
//        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        // 新しく追加を行うのでaddを使用します
//        // 他にも、よく使う操作で、replace removeといったメソッドがあります
//        // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
//        transaction.add(R.id.container, fragment);
//        // 最後にcommitを使用することで変更を反映します
//        transaction.commit();
//
//        DBOpener hlpr = new DBOpener(this);
//        SQLiteDatabase s2 = hlpr.getWritableDatabase();
//        SQLiteDatabase s = hlpr.getReadableDatabase();
//        Log.d("s", "データベース名 : " + hlpr.getDatabaseName());
//        Cursor c = s.rawQuery("select name from  sqlite_master where type='table'", new String[]{});
//
//        StringBuilder text = new StringBuilder();
//        while (c.moveToNext()) {
//            text.append(c.getString(0));
//            text.append("\n");
//        }
//
//        for(String g : hlpr.a){
//            TextView view = new TextView(this);
//            view.setText(g);
//            view.setTextColor(Color.BLACK);
//        }


        // Fragmentを作成します
        final Bundle bundle = new Bundle();
        bundle.putInt("year", 2018);
        bundle.putInt("semester", 1);

        final TableFragment fragment = new TableFragment();
        fragment.setArguments(bundle);

        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 新しく追加を行うのでaddを使用します
        // 他にも、よく使う操作で、replace removeといったメソッドがあります
        // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
        transaction.add(R.id.container, fragment);
        // 最後にcommitを使用することで変更を反映します
        transaction.commit();

//        final int menuSize = toolbar.getMenu().size();
//
//        for (int i=0; i<menuSize; i++){
//            toolbar.getMenu().getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem menuItem) {
//                    toolbar.setTitle(menuItem.getTitle());
//                    return false;
//                }
//            });
//        }

//
//        findViewById(a).setOnClickListener(this);

//        findViewById(R.id.action_ba).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toolbar.setTitle("a");
//            }
//        });

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(myToolbar);
        //setContentViewより前にWindowにActionBar表示を設定
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        setContentView(R.layout.activity_main);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // メニューの要素を追加
//        menu.add("Normal item");
//
//        // メニューの要素を追加して取得
//        MenuItem actionItem = menu.add("Action Button");
//
//        // SHOW_AS_ACTION_IF_ROOM:余裕があれば表示
//        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//        // アイコンを設定
//        actionItem.setIcon(android.R.drawable.ic_menu_share);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
//        return true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.planItem:
                toolbar.setTitle("aaaa");
                return true;
            case R.id.subjectItem:
                toolbar.setTitle("iiiii");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
